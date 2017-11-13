package com.pb.reminderapp;

/**
 * Created by ro003ag on 10/8/2017.
 */

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateResponse;
import com.pb.reminderapp.service.ReminderAppService;
import com.pb.reminderapp.utility.GetAPIData;
import com.pb.reminderapp.utility.PreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class RegistrationActivity extends Activity
        implements EasyPermissions.PermissionCallbacks {

    private static Context context;
    // UI references.
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText addressView;
    private View mProgressView;
    private View mLoginFormView;
    private RegistrationActivity mainActivity;

    public static Context getContext() {
        return context;
    }


    GoogleAccountCredential mCredential;
    //    private TextView mOutputText;
//    private Button mCallApiButton;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String WAIT_TEXT = "Getting Shipment Details from Google Calender....";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    //private static final String SANDBOX_OAUTH_URL = "https://api-sandbox.pitneybowes.com/oauth/token";
    //private static final String PROD_OAUTH_URL = "https://api.pitneybowes.com/oauth/token";
    //private static final String SANDBOX_APIKEY_SECRET = "VFRVTHVPWkdUdzNVWGRtYkJ2NTRYNVBQbzBYc2g2MDA6UWNjR25Fa09lcElEQkNuWA==";
    //private static final String PROD_APIKEY_SECRET = "R3pWN01BOGd1Yk1hdHh4aERuaE1QaDNJcmhHZ0FyQzY6S3VwM0dMU1FvTXNCVWRhdQ==";


    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        PreferencesUtils.setSharedpreferences(getSharedPreferences(PreferencesUtils.HACK_PREFERENCES
                , Context.MODE_PRIVATE));
        mainActivity = this;
        setContentView(R.layout.activity_register);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(WAIT_TEXT);
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());


        firstNameView = (EditText) findViewById(R.id.firstName);
        lastNameView = (EditText) findViewById(R.id.lastName);
        addressView = (EditText) findViewById(R.id.address);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mProgress.show();
        getResultsFromApi();


    }

    private void attemptLogin() {
        new LoginTasks().execute();


    }

    private class LoginTasks extends AsyncTask<Void, Void, Boolean> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;
        long currentTimeInMillis =0;
        String sandboxToken ="";
        String prodToken = "";

        LoginTasks() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ReminderAppService appService = new ReminderAppService();
            List<RateResponse> rateResponseList = new ArrayList();
            try {
                 currentTimeInMillis = System.currentTimeMillis();
                 sandboxToken = GetAPIData.getToken(GetAPIData.SANDBOX_OAUTH_URL , GetAPIData.SANDBOX_APIKEY_SECRET);
                 prodToken = GetAPIData.getToken(GetAPIData.PROD_OAUTH_URL ,GetAPIData.PROD_APIKEY_SECRET);

            } catch (Exception e) {
                e.printStackTrace();
                mLastError = e;
                cancel(true);
                return true;
            }
            return true;
        }




        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean output) {
            PreferencesUtils.saveRegistrationPreference("", addressView.getText().
                    toString(), firstNameView.getText().toString(), lastNameView.getText().toString(), sandboxToken, prodToken, currentTimeInMillis);

            Intent intent = new Intent(getContext(), LabelCountActivity.class);
            getContext().startActivity(intent);
        }

        @Override
        protected void onCancelled() {


        }
    }




    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            //mOutputText.setText("No network connection available.");
        } else {
            mProgress.hide();
            if (PreferencesUtils.isRegistrationExist()) {
                Intent intent = new Intent(getContext(), LabelCountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        }
    }


    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
//                    mOutputText.setText(
//                            "App requires Google Play Services.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                RegistrationActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}