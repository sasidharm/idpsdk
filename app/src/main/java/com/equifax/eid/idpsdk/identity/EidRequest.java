package com.equifax.eid.idpsdk.identity;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class EidRequest {
    private Identity identity;

    private Answers answers;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }
}
