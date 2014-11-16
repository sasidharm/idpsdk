package com.equifax.eid.idpsdk.identity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class Answers {
    private long questionnaireId;

    private List<Answer> answerList = new ArrayList<Answer>();

    public long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
