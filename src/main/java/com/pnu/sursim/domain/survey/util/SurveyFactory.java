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

        if (surveyRequest.minAge()==null && surveyRequest.maxAge() ==null){
            ageGroup = AgeGroup.ALL;
        }

        int minAge = Optional.ofNullable(surveyRequest.minAge()).orElse(0);
        int maxAge = Optional.ofNullable(surveyRequest.maxAge()).orElse(Integer.MAX_VALUE);

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
                .collectionPurpose(surveyRequest.consentInfoRequest().collectionPurpose())
                .collectedData(surveyRequest.consentInfoRequest().collectedData())
                .retentionPeriod(surveyRequest.consentInfoRequest().retentionPeriod())
                .contactInfo(surveyRequest.consentInfoRequest().contactInfo())
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

    public static QuestionOption makeOption(QuestionOptionRequest questionOptionRequest, Question question) {
        return QuestionOption.builder()
                .index(questionOptionRequest.index())
                .text(questionOptionRequest.text())
                .question(question)
                .build();
    }

    public static SemanticOption makeSemantic(SemanticOptionRequest semanticOptionRequest, Question question) {
        return SemanticOption.builder()
                .leftEnd(semanticOptionRequest.leftEnd())
                .rightEnd(semanticOptionRequest.rightEnd())
                .question(question)
                .build();
    }

    public static SurveyResponse makeSurveyResponse(Survey survey, List<QuestionResponse> questionResponses ) {
        return new SurveyResponse(
                survey.getTitle(),
                survey.getStartDate(),
                survey.getDueDate(),
                survey.getAgeGroup(),
                survey.getMinAge(),
                survey.getMaxAge(),
                survey.getPublicAccess(),
                survey.getGender(),
                survey.getTimeRequired(),
                survey.getPoints(),
                questionResponses,
                new ConsentInfoResponse(survey.getCollectionPurpose(), survey.getCollectedData(), survey.getRetentionPeriod(), survey.getContactInfo()));
    }


    public static ChoiceQuestionResponse makeChoiceQuestionResponse(Question question, List<QuestionOption> questionOptions) {
        return ChoiceQuestionResponse.builder()
                .optionResponses(questionOptions.stream()
                        .map(questionOption -> new OptionResponse(questionOption.getId(),questionOption.getIndex(),questionOption.getText()))
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
                .semanticOption(new SemanticOptionResponse(semanticOption.getLeftEnd(), semanticOption.getRightEnd()))
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
}
