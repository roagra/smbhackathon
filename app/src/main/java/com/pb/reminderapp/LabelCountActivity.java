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
import android.icu.util.GregorianCalendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.gson.Gson;
import com.pb.reminderapp.model.EventDescription;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateRequest;
import com.pb.reminderapp.model.RateResponse;
import com.pb.reminderapp.service.ReminderAppService;
import com.pb.reminderapp.utility.GetAPIData;
import com.pb.reminderapp.utility.LabelCountListAdapter;
import com.pb.reminderapp.utility.LazyAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LabelCountActivity extends Activity {

    private static Context context;
    private ListView listView;
    private LabelCountListAdapter adapter;
    private LabelCountActivity mainActivity;

    public static Context getContext() {
        return context;
    }


    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        mainActivity = this;
        setContentView(R.layout.label_count_activity);


        Button taskList = findViewById(R.id.taskList);
        taskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.list);
        adapter = new LabelCountListAdapter(this, getLabelCount());
        listView.setAdapter(adapter);


    }


    private List<LabelCount> getLabelCount() {
        List<LabelCount> labelCounts = new ArrayList<>();
        java.util.Calendar calender = java.util.GregorianCalendar.getInstance();
        Date date = calender.getTime();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dt.format(date);

        labelCounts.add(new LabelCount("Date ", "Label Count"));

        labelCounts.add(new LabelCount(currentDate, ""+20));

        calender.add(Calendar.DATE, -1);
        currentDate = dt.format(calender.getTime());
        labelCounts.add(new LabelCount(currentDate, ""+12));

        calender.add(Calendar.DATE, -1);
        currentDate = dt.format(calender.getTime());
        labelCounts.add(new LabelCount(currentDate, ""+23));

        calender.add(Calendar.DATE, -1);
        currentDate = dt.format(calender.getTime());
        labelCounts.add(new LabelCount(currentDate, ""+15));

        calender.add(Calendar.DATE, -1);
        currentDate = dt.format(calender.getTime());
        labelCounts.add(new LabelCount(currentDate, ""+18));

        calender.add(Calendar.DATE, -1);
        currentDate = dt.format(calender.getTime());
        labelCounts.add(new LabelCount(currentDate, ""+24));

        return labelCounts;
    }


    public static class LabelCount {

        private String dateRange;
        private String count;

        public LabelCount() {

        }

        public LabelCount(String dateRange, String count) {
            this.dateRange = dateRange;
            this.count = count;
        }

        public String getDateRange() {
            return dateRange;
        }

        public void setDateRange(String dateRange) {
            this.dateRange = dateRange;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }


}