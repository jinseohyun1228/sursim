package com.pnu.sursim.domain.surveyanswer.util;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.survey.entity.Scale;
import com.pnu.sursim.domain.surveyanswer.entity.*;

public class AnswerFactory {

    private AnswerFactory() {
    }

    public static OptionsAnswer makeOptionsAnswer(SurveyAnswer surveyAnswer, Question question, QuestionOption questionOption, int value) {
        return OptionsAnswer.builder()
                .surveyAnswer(surveyAnswer)
                .questionOption(questionOption)
                .question(question)
                .value(value)
                .build();
    }

    public static DescriptiveAnswer makeDescriptiveAnswer(SurveyAnswer surveyAnswer, Question question, String text) {
        return DescriptiveAnswer.builder()
                .surveyAnswer(surveyAnswer)
                .question(question)
                .value(text)
                .build();
    }


    public static TextAnswer makeTextAnswer(SurveyAnswer surveyAnswer, Question question, String text) {
        return TextAnswer.builder()
                .surveyAnswer(surveyAnswer)
                .question(question)
                .value(text)
                .build();
    }


    public static ScaleAnswer makeScaleAnswer(SurveyAnswer surveyAnswer, Question question, Scale scale) {
        return ScaleAnswer.builder()
                .surveyAnswer(surveyAnswer)
                .question(question)
                .scale(scale)
                .build();
    }
}
