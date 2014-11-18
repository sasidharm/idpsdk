package com.equifax.eid.idpsdk;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.equifax.eid.idpsdk.identity.AnswerChoice;
import com.equifax.eid.idpsdk.identity.Answers;
import com.equifax.eid.idpsdk.identity.Assessment;
import com.equifax.eid.idpsdk.identity.EidRequest;
import com.equifax.eid.idpsdk.identity.Identity;
import com.equifax.eid.idpsdk.identity.IdentityProofing;
import com.equifax.eid.idpsdk.identity.Question;
import com.equifax.eid.idpsdk.identity.Questionnaire;

/**
 * Handles communication to eID server. Returns mocked responses for now.
 * Created by Sasidhar on 11/15/14.
 */
public class EidClient extends AsyncTask<EidRequest, Integer, IdentityProofing> {

    private View view;
    private View progressView;

    public EidClient(View view, View progressView) {
        this.view = view;
        this.progressView = progressView;
    }
    @Override
    protected IdentityProofing doInBackground(EidRequest... requests) {
        try {
            // Simulate network access.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Nothing to do
        }
        EidRequest request = requests[0];
        Identity identity = request.getIdentity();
        IdentityProofing identityProofing = new IdentityProofing();
        if(identity != null)
        {
            Questionnaire questionnaire = getQuestionnaire(identity);
            identityProofing.setQuestionnaire(questionnaire);
        }
        else {
            Answers answers = request.getAnswers();
            if(answers != null)
            {
                Assessment assessment = getAssessment(answers);
                identityProofing.setAssessment(assessment);
            }
        }
        return identityProofing;
    }

    private Assessment getAssessment(Answers answers) {
        if (answers != null) {
            Log.d("Answers", String.valueOf(answers.getQuestionnaireId()));
        }
        return Assessment.PASS;
    }

    private Questionnaire getQuestionnaire(Identity identity) {
        if (identity != null) {
            Log.d("Identity", identity.getFirstName());
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(1L);
        questionnaire.getQuestions().add(createQuestion(1L, "Your credit file indicates that you may have opened an Auto Loan in 2013. Who is the provider of this Auto loan account?", "PNC Bank", "Wells Fargo", "Bank of America", "JP Morgan Chase", "None of the Above"));
        questionnaire.getQuestions().add(createQuestion(2L, "What is monthly payment of the above referenced account?", "$355.00", "$405.00", "$305.00", "$550", "None of the Above"));
        questionnaire.getQuestions().add(createQuestion(3L, "Which of the following street names have you lived in the past?", "April Street", "May Street", "123 Main Street", "North Street", "None of the Above"));
        return questionnaire;
    }

    private Question createQuestion(long questionId, String questionText, String... choices) {
        Question question = new Question();
        question.setQuestionId(questionId);
        question.setQuestionText(questionText);
        long answerId = 1L;
        for(String choice : choices)
        {
            question.getAnswerChoices().add(createAnswerChoice(answerId++, choice));
        }
        return question;
    }

    private AnswerChoice createAnswerChoice(long answerId, String choice1) {
        AnswerChoice choice = new AnswerChoice();
        choice.setAnswerId(answerId);
        choice.setAnswerText(choice1);
        return choice;
    }

    @Override
    protected void onPostExecute(IdentityProofing identityProofing) {
        super.onPostExecute(identityProofing);
        this.progressView.setVisibility(View.GONE);
        this.view.setVisibility(View.VISIBLE);
    }
}
