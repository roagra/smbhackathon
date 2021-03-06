package com.smbhackathon.shipminders;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.smbhackathon.shipminders.model.EventDetails;
import com.smbhackathon.shipminders.model.EventInfo;
import com.smbhackathon.shipminders.model.RateRequest;
import com.smbhackathon.shipminders.model.RateResponse;
import com.smbhackathon.shipminders.service.ReminderAppService;
import com.smbhackathon.shipminders.utility.GetAPIData;
import com.smbhackathon.shipminders.utility.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class PrintPreviewActivity extends Activity {
    private PrintPreviewActivity printPreviewActivity;
    private WebView webView;
    private LinearLayout baseContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_priview);
        this.printPreviewActivity = this;
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        baseContainer = findViewById(R.id.baseContainer);
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
                    .setApplicationName("Reminder App using Google Calendar API Android to mark Event as done")
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
                    //RateRequest shipmentRequest = appService.prepareRateAndShipmentRequest(eventInfo.getToAddress(), shippingOption.getMailClass());
                    //RateResponse shipmentResponse = GetAPIData.getShipmentLabel(shipmentRequest);
                    //appService.markEventAsDone(eventInfo.getEventId(),mService);
                    //rateResponseList.add(shipmentResponse);
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
            if (eventInfo.getStandardShippingOption() != null && eventInfo.getStandardShippingOption().isSelected()) {
                return eventInfo.getStandardShippingOption();
            } else if (eventInfo.getFmShippingOption() != null && eventInfo.getFmShippingOption().isSelected()) {
                return eventInfo.getFmShippingOption();
            } else if (eventInfo.getPmShippingOption() != null && eventInfo.getPmShippingOption().isSelected()) {
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
            String pdf = "https://web-prt3.gcs.pitneybowes.com/usps/325584758/outbound/label/3a58503e50d848ff82d3f9e60d99ba4d.pdf";
            webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

            WebView webView = new WebView(printPreviewActivity);
            webView.getSettings().setJavaScriptEnabled(true);
            pdf = "https://web-prt3.gcs.pitneybowes.com/usps/325584758/outbound/label/3a58503e50d848ff82d3f9e60d99ba4d.pdf";
            webView.loadUrl(pdf);
            baseContainer.addView(webView);
            baseContainer.invalidate();
        }

        @Override
        protected void onCancelled() {


        }
    }
}
