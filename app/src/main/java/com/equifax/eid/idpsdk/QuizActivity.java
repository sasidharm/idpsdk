package com.equifax.eid.idpsdk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.equifax.eid.idpsdk.identity.AnswerChoice;
import com.equifax.eid.idpsdk.identity.Answers;
import com.equifax.eid.idpsdk.identity.EidRequest;
import com.equifax.eid.idpsdk.identity.Question;
import com.equifax.eid.idpsdk.identity.Questionnaire;


public class QuizActivity extends Activity {

    private View quizView;
    private View quizProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Questionnaire questionnaire = (Questionnaire) getIntent().getSerializableExtra("quiz");

        LinearLayout quizForm = (LinearLayout) findViewById(R.id.quiz_form);
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.equifax);
        for (Question question : questionnaire.getQuestions()) {
            TextView questionLbl = new TextView(this);
            questionLbl.setTextAppearance(this, R.style.label_style);
            questionLbl.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            questionLbl.setText(question.getQuestionText());
            quizForm.addView(questionLbl);

            RadioGroup rg = new RadioGroup(this);
            rg.setId((int) question.getQuestionId());
            for (AnswerChoice answerChoice : question.getAnswerChoices()) {
                RadioButton rb = new RadioButton(this);
                rb.setText(answerChoice.getAnswerText());
                rb.setId((int) answerChoice.getAnswerId());
                rb.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rg.addView(rb);
            }

            quizForm.addView(rg);
        }

        Button button = new Button(this);
        button.setText(R.string.action_submit);
        button.setTextAppearance(this, R.style.label_style);
        quizForm.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quiz", "Finishing Activity");
                quizView = findViewById(R.id.quiz_view);
                quizProgress = findViewById(R.id.quiz_progress);
                EidClient eidClient = new EidClient(QuizActivity.this, quizView, quizProgress);
                EidRequest eidRequest = new EidRequest();
                Answers answers = new Answers();
                eidRequest.setAnswers(answers);
                eidClient.execute(eidRequest);
                showProgress(true);
            }
        });
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        quizView.setVisibility(show ? View.GONE : View.VISIBLE);
        quizView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                quizView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        quizProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        quizProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                quizProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
