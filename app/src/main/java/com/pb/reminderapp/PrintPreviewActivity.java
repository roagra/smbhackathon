package com.pb.reminderapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateRequest;
import com.pb.reminderapp.model.RateResponse;
import com.pb.reminderapp.service.ReminderAppService;
import com.pb.reminderapp.utility.GetAPIData;
import com.pb.reminderapp.utility.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class PrintPreviewActivity extends Activity {
    private PrintPreviewActivity printPreviewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_priview);
        this.printPreviewActivity = this;

        ImageView backImageView = findViewById(R.id.image_view_header_icon);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPreviewActivity.onBackPressed();
            }
        });
        new PrintLabelTask().execute();
    }

    private class PrintLabelTask extends AsyncTask<Void, Void, Boolean> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        PrintLabelTask() {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, PreferencesUtils.getCredentials())
                    .setApplicationName("Reminder App using Google Calendar API Android")
                    .build();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ReminderAppService appService = new ReminderAppService();
            List<RateResponse> rateResponseList = new ArrayList();
            try {
                List<EventInfo> listEventInfo = PreferencesUtils.getSelectedEventInfo();
                for (EventInfo eventInfo : listEventInfo) {
                    EventInfo.ShippingOption shippingOption = getSelectedShippingOption(eventInfo);
                    RateRequest shipmentRequest =  appService.prepareRateAndShipmentRequest(eventInfo.getToAddress(), shippingOption.getMailClass());
                    RateResponse shipmentResponse = GetAPIData.getShipmentLabel(shipmentRequest);
                    rateResponseList.add(shipmentResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mLastError = e;
                cancel(true);
                return true;
            }
            return true;
        }

        private EventInfo.ShippingOption getSelectedShippingOption(EventInfo eventInfo) {
            if (eventInfo.getStandardShippingOption().isSelected()){
                return eventInfo.getStandardShippingOption();
            } else if(eventInfo.getFmShippingOption().isSelected()){
                return eventInfo.getFmShippingOption();
            } else if(eventInfo.getPmShippingOption().isSelected()){
                return eventInfo.getPmShippingOption();
            } else {
                return null;
            }
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean output) {

        }

        @Override
        protected void onCancelled() {


        }
    }
}
