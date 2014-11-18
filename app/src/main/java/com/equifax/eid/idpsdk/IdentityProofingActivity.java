package com.equifax.eid.idpsdk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.equifax.eid.idpsdk.identity.Assessment;
import com.equifax.eid.idpsdk.identity.EidRequest;
import com.equifax.eid.idpsdk.identity.Identity;
import com.equifax.eid.idpsdk.identity.IdentityProofing;
import com.equifax.eid.idpsdk.identity.Questionnaire;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class IdentityProofingActivity extends Activity {

    private Questionnaire questionnaire = null;
    private Assessment assessment = null;

//    private Assessment assessment = null;

    private EidClient eidClient;

    private View idFormView;
    private View idProgressView;
    private Identity identity;

    public IdentityProofingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proofing);
        idFormView = findViewById(R.id.idp_form);
        idProgressView = findViewById(R.id.idp_progress);

        final EidRequest request = new EidRequest();
        identity = (Identity) getIntent().getSerializableExtra("identity");
        if (identity == null) {
            identity = new Identity();
        } else {
            EditText firstNameWidget = (EditText) findViewById(R.id.first_name);
            firstNameWidget.setText(identity.getFirstName());
            firstNameWidget.setEnabled(false);
            EditText lastNameWidget = (EditText) findViewById(R.id.last_name);
            lastNameWidget.setText(identity.getLastName());
            lastNameWidget.setEnabled(false);

            EditText ssnWidget = (EditText) findViewById(R.id.ssn);
            ssnWidget.setText(identity.getSsn());
            ssnWidget.setEnabled(false);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date parsedDate = dateFormat.parse(identity.getDob());
                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedDate);
                DatePicker dobWidget = (DatePicker) findViewById(R.id.dob_value);
                dobWidget.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), null);
                dobWidget.setEnabled(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            request.setIdentity(identity);
        }


        Button submit = (Button) findViewById(R.id.identity_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eidClient != null) {
                    return;
                }
                showProgress(true);
                eidClient = new EidClient(IdentityProofingActivity.this, idFormView, idProgressView);
                identity.setFirstName(getText(findViewById(R.id.first_name)));
                identity.setLastName(getText(findViewById(R.id.last_name)));
                AsyncTask<EidRequest, Integer, IdentityProofing> task = eidClient.execute(request);
                eidClient = null;
            }
        });
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
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
        });
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
