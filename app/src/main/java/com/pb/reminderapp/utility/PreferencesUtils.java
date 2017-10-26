package com.pb.reminderapp.utility;

import android.content.SharedPreferences;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.pb.reminderapp.model.EventInfo;

import java.util.List;


/**
 * Created by gr016ma on 9/27/2017.
 */

public class PreferencesUtils {
    private static final String ADDRESS = "address";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String ALREADY_REGISTERED = "registered";
    private static final String EMAIL_ID = "emailId";
    public static final String HACK_PREFERENCES = "HACK_PREF";
    public static final String SANDBOX_TOKEN = "SANDBOX_TOKEN";
    public static final String PROD_TOKEN = "PROD_TOKEN";
    public static final String TOKEN_SET_TIME = "TOKEN_SET_TIME";
    private static SharedPreferences sharedpreferences;
    private static List<EventInfo> selectedEventInfo;
    private static GoogleAccountCredential credentials;

    public static List<EventInfo> getSelectedEventInfo() {
        return selectedEventInfo;
    }

    public static void setSelectedEventInfo(List<EventInfo> selectedEventInfo) {
        PreferencesUtils.selectedEventInfo = selectedEventInfo;
    }

    public static SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }

    public static void setSharedpreferences(SharedPreferences sharedpreferences) {
        PreferencesUtils.sharedpreferences = sharedpreferences;
    }


    public static void saveRegistrationPreference(String emailId, String address, String firstName, String lastName, String sandboxToken, String prodToken, long currentTimeInMillis) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ADDRESS, address);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(EMAIL_ID, emailId);
        editor.putBoolean(ALREADY_REGISTERED, true);
        editor.putString(SANDBOX_TOKEN,sandboxToken);
        editor.putString(PROD_TOKEN,prodToken);
        editor.putLong(TOKEN_SET_TIME, currentTimeInMillis);
        editor.commit();
    }

    public static String getEmailId() {
        return sharedpreferences.getString(EMAIL_ID, "");
    }

    public static String getAddress() {
        return sharedpreferences.getString(ADDRESS, "");
    }

    public static String getSandboxToken(){return sharedpreferences.getString(SANDBOX_TOKEN, "");}

    public static String getProdToken(){return sharedpreferences.getString(PROD_TOKEN, "");}

    public static long getTokenSetTime(){return sharedpreferences.getLong(TOKEN_SET_TIME, 0);}

    public static void setSandboxToken(String sandboxToken){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(TOKEN_SET_TIME, System.currentTimeMillis());
        editor.putString(SANDBOX_TOKEN,sandboxToken);
        editor.commit();
    }

    public static void setProdToken(String prodToken){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(TOKEN_SET_TIME, System.currentTimeMillis());
        editor.putString(PROD_TOKEN,prodToken);
        editor.commit();
    }

    public static boolean isRegistrationExist() {
        return sharedpreferences.getBoolean(ALREADY_REGISTERED, false);
    }

    public static void setCredentials(GoogleAccountCredential credentials) {
        PreferencesUtils.credentials = credentials;
    }

    public static GoogleAccountCredential getCredentials() {
        return credentials;
    }
}
