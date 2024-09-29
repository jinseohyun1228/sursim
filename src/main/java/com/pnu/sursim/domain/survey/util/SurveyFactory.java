package com.pnu.sursim.domain.survey.util;

import com.pnu.sursim.domain.survey.dto.*;
import com.pnu.sursim.domain.survey.entity.*;
import com.pnu.sursim.domain.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pnu.sursim.domain.survey.util.SurveyRequiredTimeCalculator.calculateRequiredTime;

public class SurveyFactory {

    public static Survey makeSurvey(SurveyRequest surveyRequest, User creator) {
        AgeGroup ageGroup = AgeGroup.SPECIFIC;

        if (surveyRequest.minAge() == null && surveyRequest.maxAge() == null) {
            ageGroup = AgeGroup.ALL;
        }

        int minAge = Optional.ofNullable(surveyRequest.minAge()).orElse(0);
        int maxAge = Optional.ofNullable(surveyRequest.maxAge()).orElse(Integer.MAX_VALUE);

        Consent consent = Consent.builder().collectionPurpose(surveyRequest.consentInfo().collectionPurpose())
                .collectedData(surveyRequest.consentInfo().collectedData())
                .retentionPeriod(surveyRequest.consentInfo().retentionPeriod())
                .contactInfo(surveyRequest.consentInfo().contactInfo()).build();

        return Survey.builder()
                .title(surveyRequest.title())
                .creator(creator)
                .startDate(surveyRequest.startDate())
                .dueDate(surveyRequest.dueDate())
                .publicAccess(surveyRequest.publicAccess())
                .points(surveyRequest.points())
                .timeRequired(calculateRequiredTime(surveyRequest))
                .minAge(minAge)
                .maxAge(maxAge)
                .gender(surveyRequest.gender())
                .ageGroup(ageGroup)
                .consent(consent)
                .build();
    }

    public static Question makeQuestion(QuestionRequest questionRequest, Survey survey) {
        return Question.builder()
                .text(questionRequest.text())
                .questionType(questionRequest.questionType())
                .requiredOption(questionRequest.requiredOption())
                .index(questionRequest.index())
                .survey(survey)
                .build();
    }

    public static QuestionOption makeOption(QuestionRequest.QuestionOptionRequest questionOptionRequest, Question question) {
        return QuestionOption.builder()
                .index(questionOptionRequest.index())
                .text(questionOptionRequest.text())
                .question(question)
                .build();
    }

    public static SemanticOption makeSemantic(QuestionRequest.SemanticOptionRequest semanticOptionRequest, Question question) {
        return SemanticOption.builder()
                .leftEnd(semanticOptionRequest.leftEnd())
                .rightEnd(semanticOptionRequest.rightEnd())
                .question(question)
                .build();
    }


    public static ChoiceQuestionResponse makeChoiceQuestionResponse(Question question, List<QuestionOption> questionOptions) {
        return ChoiceQuestionResponse.builder()
                .optionResponses(questionOptions.stream()
                        .map(questionOption -> new ChoiceQuestionResponse.OptionResponse(questionOption.getId(), questionOption.getIndex(), questionOption.getText()))
                        .collect(Collectors.toList()))
                .id(question.getId())
                .index(question.getIndex())
                .text(question.getText())
                .questionType(question.getQuestionType())
                .requiredOption(question.getRequiredOption())
                .build();
    }

    public static SemanticQuestionResponse makeSemanticQuestionResponse(Question question, SemanticOption semanticOption) {
        return SemanticQuestionResponse.builder()
                .semanticOption(new SemanticQuestionResponse.SemanticOptionResponse(semanticOption.getLeftEnd(), semanticOption.getRightEnd()))
                .id(question.getId())
                .index(question.getIndex())
                .text(question.getText())
                .questionType(question.getQuestionType())
                .requiredOption(question.getRequiredOption())
                .build();
    }

    public static QuestionResponse makeQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .index(question.getIndex())
                .text(question.getText())
                .questionType(question.getQuestionType())
                .requiredOption(question.getRequiredOption())
                .build();
    }

    public static Reward makeReward(Survey targetSurvey, RewardRequest rewardRequest, String rewardImg) {
        return Reward.builder()
                .survey(targetSurvey)
                .title(rewardRequest.title())
                .rewardType(rewardRequest.rewardType())
                .count(rewardRequest.count())
                .rewardImg(rewardImg)
                .build();

    }

    public static SurveyWithRewardResponse makeSurveyWithRewardResponse(Survey survey, List<QuestionResponse> questionResponses, Reward reward) {
        return SurveyWithRewardResponse.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .startDate(survey.getStartDate())
                .dueDate(survey.getDueDate())
                .ageGroup(survey.getAgeGroup())
                .minAge(survey.getMinAge())
                .maxAge(survey.getMaxAge())
                .publicAccess(survey.getPublicAccess())
                .rewardStatus(survey.getRewardStatus())
                .gender(survey.getGender())
                .timeRequired(survey.getTimeRequired())
                .points(survey.getPoints())
                .questionList(questionResponses)
                .consentInfo(new SpecSurveyResponse.ConsentInfoResponse(survey.getConsent()))
                .reward(new SurveyWithRewardResponse.RewardResponse(reward.getTitle(), reward.getRewardType(), reward.getCount(), reward.getRewardImg()))
                .build();
    }


    public static SpecSurveyResponse makeSpecSurveyResponse(Survey survey, List<QuestionResponse> questionResponses) {
        return SpecSurveyResponse.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .startDate(survey.getStartDate())
                .dueDate(survey.getDueDate())
                .ageGroup(survey.getAgeGroup())
                .minAge(survey.getMinAge())
                .maxAge(survey.getMaxAge())
                .publicAccess(survey.getPublicAccess())
                .rewardStatus(survey.getRewardStatus())
                .gender(survey.getGender())
                .timeRequired(survey.getTimeRequired())
                .points(survey.getPoints())
                .questionList(questionResponses)
                .consentInfo(new SpecSurveyResponse.ConsentInfoResponse(survey.getConsent()))
                .build();
    }

    public static SurveyResponse makeSurveyResponse(Survey survey) {
        return SurveyResponse.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .startDate(survey.getStartDate())
                .dueDate(survey.getDueDate())
                .ageGroup(survey.getAgeGroup())
                .minAge(survey.getMinAge())
                .maxAge(survey.getMaxAge())
                .publicAccess(survey.getPublicAccess())
                .rewardStatus(survey.getRewardStatus())
                .gender(survey.getGender())
                .timeRequired(survey.getTimeRequired())
                .points(survey.getPoints())
                .build();
    }



}
