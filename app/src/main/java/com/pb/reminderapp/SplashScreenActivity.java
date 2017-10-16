package com.pb.reminderapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


public class SplashScreenActivity extends Activity {

    private final String TAG = SplashScreenActivity.class.getSimpleName();
    private boolean isLoadingActivity = false;

    public boolean isAllPermissionGranted = false;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * This method is used to load the activity
     */
    private void loadActivity() {
        try {
            if (isLoadingActivity) {
                return;
            } else {
                isLoadingActivity = true;
            }
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            Intent intent = null;
                            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            SplashScreenActivity.this.finish();


                        }
                    }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SplashScreenActivity.this.finish();

    }
}
