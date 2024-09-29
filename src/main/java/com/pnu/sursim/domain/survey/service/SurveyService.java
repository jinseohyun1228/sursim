package com.pnu.sursim.domain.survey.service;

import com.pnu.sursim.domain.survey.dto.*;
import com.pnu.sursim.domain.survey.entity.*;
import com.pnu.sursim.domain.survey.repository.*;
import com.pnu.sursim.domain.survey.util.SurveyFactory;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import com.pnu.sursim.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SurveyService {

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final SemanticOptionRepository semanticOptionRepository;
    private final RewardRepository rewardRepository;
    private final S3Service s3Service;

    //서베이 등록
    @Transactional
    public Long createSurvey(String email, SurveyRequest surveyRequest) {

        User creator = findUserOrThrow(email);

        //문항 저장
        Survey savedSurvey = surveyRepository.save(SurveyFactory.makeSurvey(surveyRequest, creator));

        //문항을 만드는 부분
        surveyRequest.questionList().forEach(questionRequest -> {
            // Question 객체 생성 & 데이터베이스에 Question 저장
            Question savedQuestion = questionRepository.save(SurveyFactory.makeQuestion(questionRequest, savedSurvey));

            // 체크박스거나 객관식인 경우 추가로 문항 저장하기
            if ((savedQuestion.getQuestionType() == QuestionType.CHECK_CHOICE) || (savedQuestion.getQuestionType() == QuestionType.MULTIPLE_CHOICE)) {
                questionRequest.questionOption().forEach(questionOptionRequest -> questionOptionRepository.save(SurveyFactory.makeOption(questionOptionRequest, savedQuestion)));
            }

            // 의미 분별 척도인 경우
            if (savedQuestion.getQuestionType() == QuestionType.SEMANTIC_RATINGS) {
                semanticOptionRepository.save(SurveyFactory.makeSemantic(questionRequest.semanticOption(), savedQuestion));
            }

        });

        return savedSurvey.getId();

    }

    //모든 서베이 페이지 조회
    public Page<SurveyResponse> getSurveyPageForAll(Pageable pageable) {
        //id기준 내림차순으로 정렬될 수 있도록 PageRequest생성
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());

        //내림차순으로 정렬된 PageRequest를 사용하여 서베이 조회
        Page<Survey> surveys = surveyRepository.findAll(pageRequest);

        return completeSurveyPage(surveys, pageRequest);
    }

    //유저에 맞는 서베이 페이지 조회
    public Page<SurveyResponse> getSurveyPageForUser(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());

        Page<Survey> surveys = surveyRepository.findAllByAgeAndGender(user.getBirthDate(), user.getGender(), pageRequest);

        return completeSurveyPage(surveys, pageRequest);

    }


    //리워드가 있고 유저에게 맞는 서베이 페이지 조회
    public Page<SurveyResponse> getSurveyPageForReward(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());

        Page<Survey> surveys = surveyRepository.findAllByAgeAndGenderAndHasReward(user.getBirthDate(), user.getGender(), pageRequest);
        return completeSurveyPage(surveys, pageRequest);
    }

    //리워드가 있고 유저에게 맞는 서베이 3개만 조회
    public List<SurveyResponse> getSurveysForRewardTop3(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        //사이즈 3으로 제한하여 PageRequest 생성 (ID 기준 내림차순)
        Pageable top3PageRequest = PageRequest.of(0, 3, Sort.by("id").descending());

        Page<Survey> surveys = surveyRepository.findAllByAgeAndGenderAndHasReward(user.getBirthDate(), user.getGender(), top3PageRequest);

        return completeSurveyPage(surveys, top3PageRequest).getContent();
    }


    //리워드 등록
    @Transactional
    public void addReward(String email, long surveyId, RewardRequest rewardRequest, MultipartFile rewardFile) {
        User creator = findUserOrThrow(email);

        Survey targetSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_DOES_NOT_EXIST))
                .validateAddReward()
                .validateCreator(creator);

        //이미지 업로드
        String rewardImg = s3Service.uploadImg(rewardFile);

        Reward savedReward = rewardRepository.save(SurveyFactory.makeReward(targetSurvey, rewardRequest, rewardImg));

        targetSurvey.registerReward(savedReward);

    }

    //id기준으로 서부이세부정보까지 반환 반환
    public SpecSurveyResponse getSpecSurveysById(long surveyId) {
        Survey targetSurvey = findSurveyOrThrow(surveyId);

        List<QuestionResponse> questionResponseList = completeQuestionResponseList(targetSurvey);

        //리워드가 없는 경우
        if (!targetSurvey.hasReward()) {
            return SurveyFactory.makeSpecSurveyResponse(targetSurvey, questionResponseList);
        }

        //리워드가 있는 경우
        Reward reward = rewardRepository.findBySurveyId(targetSurvey.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NO_REWARDS));

        return SurveyFactory.makeSurveyWithRewardResponse(targetSurvey, questionResponseList, reward);

    }


    //서베이 조회 페이지 객체를 만드는 부분
    private Page<SurveyResponse> completeSurveyPage(Page<Survey> surveys, Pageable pageable) {
        List<SurveyResponse> surveyResponseRecodes = surveys.getContent().stream()
                .map(SurveyFactory::makeSurveyResponse).collect(Collectors.toList());
        return new PageImpl<>(surveyResponseRecodes, pageable, surveys.getTotalElements());
    }


    //유저의 유효성을 검사하고 예외를 발생시키는 부분
    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
    }

    public Survey findSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_DOES_NOT_EXIST));
    }


    //서베이의 문항 정보를 만드는 부분
    public List<QuestionResponse> completeQuestionResponseList(Survey survey) {
        return questionRepository.findAllBySurveyIdOrderByIndexAsc(survey.getId())
                .stream()
                .map(question -> {
                    //문항의 타입이 단일/체크 경우 ChoiceQuestionResponse 반환
                    if ((question.getQuestionType() == QuestionType.CHECK_CHOICE) || (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE)) {
                        List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionIdOrderByIndexAsc(question.getId());
                        if (questionOptions.isEmpty()) {
                            throw new CustomException(ErrorCode.INCORRECT_CHOICE_QUESTION);
                        }
                        return SurveyFactory.makeChoiceQuestionResponse(question, questionOptions);
                    }

                    //문항 타입이 의미판별인 경우 변경 -> SemanticQuestionResponse
                    if (question.getQuestionType() == QuestionType.SEMANTIC_RATINGS) {
                        SemanticOption semanticOption = semanticOptionRepository.findByQuestionId(question.getId())
                                .orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_SEMANTIC_QUESTIONS));
                        return SurveyFactory.makeSemanticQuestionResponse(question, semanticOption);
                    }

                    //일반 문항 -> QuestionResponse
                    return SurveyFactory.makeQuestionResponse(question);
                })
                .collect(Collectors.toList());
    }

}
