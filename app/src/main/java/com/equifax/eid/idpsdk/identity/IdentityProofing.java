package com.equifax.eid.idpsdk.identity;

/**
 * Created by Sasidhar on 11/15/14.
 */
public class IdentityProofing {

    private Questionnaire questionnaire;

    private Assessment assessment;

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
