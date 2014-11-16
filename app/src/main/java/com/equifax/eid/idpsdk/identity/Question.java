package com.equifax.eid.idpsdk.identity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class Question implements Serializable {
    private long questionId;
    private String questionText;
    private List<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();

    public List<AnswerChoice> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<AnswerChoice> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


}
