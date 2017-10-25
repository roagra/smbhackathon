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


    public static void saveRegistrationPreference(String emailId, String address, String firstName, String lastName) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ADDRESS, address);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(EMAIL_ID, emailId);
        editor.putBoolean(ALREADY_REGISTERED, true);
        editor.commit();
    }

    public static String getEmailId() {
        return sharedpreferences.getString(EMAIL_ID, "");
    }

    public static String getAddress() {
        return sharedpreferences.getString(ADDRESS, "");
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
