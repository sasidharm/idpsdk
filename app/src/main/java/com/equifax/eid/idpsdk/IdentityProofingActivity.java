package com.equifax.eid.idpsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.equifax.eid.idpsdk.identity.EidRequest;
import com.equifax.eid.idpsdk.identity.Identity;
import com.equifax.eid.idpsdk.identity.IdentityProofing;
import com.equifax.eid.idpsdk.identity.Questionnaire;

import java.util.concurrent.ExecutionException;


public class IdentityProofingActivity extends Activity {

    private Questionnaire questionnaire = null;

//    private Assessment assessment = null;

    private EidClient eidClient;

    private View idFormView;
    private View idProgressView;

    public IdentityProofingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proofing);
        idFormView = findViewById(R.id.identity_submit_form);
        idProgressView = findViewById(R.id.idp_progress);

        final EidRequest request = new EidRequest();
        Identity identity = new Identity();
        request.setIdentity(identity);
        identity.setFirstName(getText(findViewById(R.id.first_name)));
        identity.setLastName(getText(findViewById(R.id.last_name)));

        Button submit = (Button) findViewById(R.id.identity_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eidClient != null)
                {
                    return;
                }
                showProgress(true);
                eidClient = new EidClient(idFormView, idProgressView);
                AsyncTask<EidRequest, Integer, IdentityProofing> task = eidClient.execute(request);
                try {
                    IdentityProofing proofing = task.get();
//                    showProgress(false);
                    questionnaire = proofing.getQuestionnaire();
//                    assessment = proofing.getAssessment();
                    if (questionnaire != null) {
                        Log.d("Quiz", "Displaying Quiz");
                        showQuiz();
                    } else {
                        Log.d("Quiz", "Not Displaying Quiz");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finally
                {
                    eidClient = null;
                }
            }
        });
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (show) {
            Log.d("ProgressView", "Displaying progress view");
        } else {
            Log.d("ProgressView", "Hiding progress view");
        }
        idFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        idProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        /*int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        idFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        idFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                idFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        idProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        idProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                idProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });*/
    }

    private void showQuiz() {

        if (questionnaire != null) {
            Log.d("Quiz", "Starting Quiz activity");
            Intent quizIntent = new Intent(this, QuizActivity.class);
            quizIntent.putExtra("quiz", questionnaire);
            startActivityForResult(quizIntent, 145);
        }
    }
    private String getText(View view) {
        EditText textView = (EditText) view;
        return textView.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_identity_proofing, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "RequestCode: " + requestCode + "; ResultCode: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

}
