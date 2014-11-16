package com.equifax.eid.idpsdk.identity;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class Questionnaire implements Serializable {

    private Long questionnaireId;
    private List<Question> questions = new ArrayList<Question>();

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
