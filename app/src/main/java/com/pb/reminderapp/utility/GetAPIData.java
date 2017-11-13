package com.pb.reminderapp.utility;

import android.net.Uri;

import com.google.gson.Gson;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.PostCodeResponse;
import com.pb.reminderapp.model.RateRequest;
import com.pb.reminderapp.model.RateResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ro003ag on 10/6/2017.
 */

public class GetAPIData {

    private static final String SANDBOX_RATES_API_URL = "https://api-sandbox.pitneybowes.com/shippingservices/v1/rates?includeDeliveryCommitment=true";
    private static final String SANDBOX_SHIPPING_LABELS_API_URL = "https://api-sandbox.pitneybowes.com/shippingservices/v1/shipments?includeDeliveryCommitment=true";
    public static final String SANDBOX_TOKEN = "SANDBOX_TOKEN";
    public static final String PROD_TOKEN = "PROD_TOKEN";
    public static final String SANDBOX_OAUTH_URL = "https://api-sandbox.pitneybowes.com/oauth/token";
    public static final String PROD_OAUTH_URL = "https://api.pitneybowes.com/oauth/token";
    public static final String SANDBOX_APIKEY_SECRET = "VFRVTHVPWkdUdzNVWGRtYkJ2NTRYNVBQbzBYc2g2MDA6UWNjR25Fa09lcElEQkNuWA==";
    public static final String PROD_APIKEY_SECRET = "R3pWN01BOGd1Yk1hdHh4aERuaE1QaDNJcmhHZ0FyQzY6S3VwM0dMU1FvTXNCVWRhdQ==";

/*    private static EventDetails eventDetails;

    public static EventDetails getSelectedEventDetails() {
        return eventDetails;
    }

    public static void setSelectedEventDetails(EventDetails eventDetailsObj) {
        eventDetails = eventDetailsObj;
    }*/

    public static String getToken(String urlStr , String APIkeySecret) {
        String token = "";
        try {
            URL url = new URL(urlStr);
            String urlParameters = "grant_type=client_credentials";
            byte[] postData = urlParameters.getBytes();
            int postDataLength = postData.length;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Add Request Header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", "Basic " + APIkeySecret);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(postData);
            //display what returns the POST request
            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                JSONObject jObject = new JSONObject(sb.toString());
                token = (String) jObject.get("access_token");
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static RateResponse getRates(RateRequest rateRequest) {
        RateResponse rateResponse = null;
        Gson g = new Gson();
        try {
            URL url = new URL(SANDBOX_RATES_API_URL);
            //JSONObject jsonObj = new JSONObject(request);
            String rateRequestJson = g.toJson(rateRequest);
            String urlParameters = rateRequestJson;
            byte[] postData = urlParameters.getBytes();
            int postDataLength = postData.length;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Add Request Header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("X-PB-Shipper-Rate-Plan", "");
            urlConnection.setRequestProperty("Authorization", "Bearer " + checkToken(SANDBOX_TOKEN));
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(postData);
            //display what returns the POST request
            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                rateResponse = g.fromJson(sb.toString(), RateResponse.class);
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateResponse;
    }

    public static RateResponse getShipmentLabel(RateRequest rateRequest) {
        RateResponse rateResponse = null;
        Gson g = new Gson();
        try {
            URL url = new URL(SANDBOX_SHIPPING_LABELS_API_URL);
            //JSONObject jsonObj = new JSONObject(request);
            String rateRequestJson = g.toJson(rateRequest);
            String urlParameters = rateRequestJson;
            byte[] postData = urlParameters.getBytes();
            int postDataLength = postData.length;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Add Request Header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("X-PB-Shipper-Rate-Plan", "");
            urlConnection.setRequestProperty("X-PB-TransactionId", "ship-W2" + System.currentTimeMillis() + "CRiP");
            urlConnection.setRequestProperty("Authorization", "Bearer " + checkToken(SANDBOX_TOKEN));
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(postData);
            //display what returns the POST request
            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                rateResponse = g.fromJson(sb.toString(), RateResponse.class);
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateResponse;
    }

    public static PostCodeResponse getPostCode(String toAddress) {
        PostCodeResponse postCodeResponse = new PostCodeResponse();
        try {
            URL url = new URL("http://api.pitneybowes.com/location-intelligence/geocode-service/v1/transient/premium/geocode?country=USA&mainAddress="+ toAddress +"&matchMode=Standard&fallbackGeo=true&fallbackPostal=true&maxCands=1&streetOffset=7&streetOffsetUnits=METERS&cornerOffset=7&cornerOffsetUnits=METERS");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + checkToken(PROD_TOKEN));
            urlConnection.setRequestProperty("Content-Type", "application/json");

            //display what returns the GET request
            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                JSONObject jObject = new JSONObject(sb.toString());
                Integer totalMatches = (Integer)jObject.get("totalMatches");
                if(totalMatches > 0) {
                    JSONArray jArray = (JSONArray) jObject.get("candidates");
                    JSONObject jObject2 = (JSONObject) jArray.get(0);
                    JSONObject address = (JSONObject) jObject2.get("address");
                    postCodeResponse.setAddressLastLine((String) address.get("addressLastLine"));
                    postCodeResponse.setAddressNumber((String) address.get("addressNumber"));
                    postCodeResponse.setAreaName1((String) address.get("areaName1"));
                    postCodeResponse.setAreaName2((String) address.get("areaName2"));
                    postCodeResponse.setAreaName3((String) address.get("areaName3"));
                    postCodeResponse.setAreaName4((String) address.get("areaName4"));
                    postCodeResponse.setMainAddressLine((String) address.get("mainAddressLine"));
                    postCodeResponse.setPlaceName((String) address.get("placeName"));
                    postCodeResponse.setPostCode1((String) address.get("postCode1"));
                    postCodeResponse.setPostCode2((String) address.get("postCode2"));
                    postCodeResponse.setCountry((String) address.get("country"));
                    postCodeResponse.setStreetName((String) address.get("streetName"));
                }
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postCodeResponse;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static String checkToken(String tokenEnv){
        String tokenReturn = "";
        if(SANDBOX_TOKEN.equals(tokenEnv)){
            if (System.currentTimeMillis() - PreferencesUtils.getTokenSetTime() > 720000){
                tokenReturn = GetAPIData.getToken(SANDBOX_OAUTH_URL , SANDBOX_APIKEY_SECRET);
                PreferencesUtils.setSandboxToken(tokenReturn);
            } else if(!PreferencesUtils.getSandboxToken().isEmpty()){
                tokenReturn = PreferencesUtils.getSandboxToken();
            } else {
                tokenReturn = GetAPIData.getToken(SANDBOX_OAUTH_URL , SANDBOX_APIKEY_SECRET);
                PreferencesUtils.setSandboxToken(tokenReturn);
            }
        }

        if(PROD_TOKEN.equals(tokenEnv)){
            if (System.currentTimeMillis() - PreferencesUtils.getTokenSetTime() > 720000){
                tokenReturn = GetAPIData.getToken(PROD_OAUTH_URL , PROD_APIKEY_SECRET);
                PreferencesUtils.setProdToken(tokenReturn);
            } else if(!PreferencesUtils.getProdToken().isEmpty()){
                tokenReturn = PreferencesUtils.getProdToken();
            } else {
                tokenReturn = GetAPIData.getToken(PROD_OAUTH_URL , PROD_APIKEY_SECRET);
                PreferencesUtils.setProdToken(tokenReturn);
            }
        }
        return tokenReturn;
    }
}
