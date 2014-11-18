package com.equifax.eid.idpsdk.identity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Pojo to hold on to the questionnaire.
 * Created by Sasidhar on 11/15/14.
 */
public class Questionnaire implements Serializable {

    private Long questionnaireId;
    private List<Question> questions = new ArrayList<Question>();

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
