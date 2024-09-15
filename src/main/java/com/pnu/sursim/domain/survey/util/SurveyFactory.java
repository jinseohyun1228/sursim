package com.pnu.sursim.domain.survey.util;

import com.pnu.sursim.domain.survey.dto.QuestionOptionRequest;
import com.pnu.sursim.domain.survey.dto.QuestionRequest;
import com.pnu.sursim.domain.survey.dto.SemanticOptionRequest;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.survey.entity.SemanticOption;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.user.entity.User;

import static com.pnu.sursim.domain.survey.util.SurveyRequiredTimeCalculator.calculateRequiredTime;

public class SurveyFactory {

    public static Survey makeSurvey(SurveyRequest surveyRequest, User creator) {
        return Survey.builder()
                .creator(creator)
                .startDate(surveyRequest.startDate())
                .dueDate(surveyRequest.dueDate())
                .publicAccess(surveyRequest.publicAccess())
                .points(surveyRequest.points())
                .timeRequired(calculateRequiredTime(surveyRequest))
                .build();
    }

    public static Question makeQuestion(QuestionRequest questionRequest, Survey survey) {
        return Question.builder()
                .text(questionRequest.text())
                .questionType(questionRequest.questionType())
                .requiredOption(questionRequest.requiredOption())
                .index(questionRequest.number())
                .survey(survey)
                .build();
    }

    public static QuestionOption makeOption(QuestionOptionRequest questionOptionRequest, Question question) {
        return QuestionOption.builder()
                .indexOfCurrentResponse(questionOptionRequest.number())
                .text(questionOptionRequest.text())
                .question(question)
                .build();
    }

    public static SemanticOption makeSemantic(SemanticOptionRequest semanticOptionRequest, Question question) {
        return SemanticOption.builder()
                .leftEnd(semanticOptionRequest.leftEnd())
                .rightEnd(semanticOptionRequest.rightEnd())
                .build();
    }


}
