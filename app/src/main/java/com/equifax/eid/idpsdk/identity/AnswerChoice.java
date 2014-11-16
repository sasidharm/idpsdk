package com.equifax.eid.idpsdk.identity;

import java.io.Serializable;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class AnswerChoice implements Serializable {
    private long answerId;
    private String answerText;
    private boolean correctAnswer;

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
