package com.equifax.eid.idpsdk.identity;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class Answer {

    private long questionId;

    private long answerId;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }
}
