package com.pb.reminderapp.utility;

import com.google.gson.Gson;
import com.pb.reminderapp.model.RateResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ro003ag on 10/6/2017.
 */

public class GetAPIData {

    private static String getToken() {
        String token = "";
        try {
            URL url = new URL("https://api.pitneybowes.com/oauth/token");
            String urlParameters  = "grant_type=client_credentials";
            byte[] postData = urlParameters.getBytes();
            int postDataLength = postData.length;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Add Request Header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization","Basic abcdefgh==");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
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
                JSONObject jObject  = new JSONObject(sb.toString());
                token = (String)jObject.get("access_token");
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static RateResponse getRates(String request) {
        RateResponse rateResponse = null;
        try {
            URL url = new URL("https://api-sandbox.pitneybowes.com/shippingservices/v1/rates");
            String urlParameters  = request;
            byte[] postData = urlParameters.getBytes();
            int postDataLength = postData.length;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Add Request Header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("X-PB-Shipper-Rate-Plan","PP_SRP_CBP");
            urlConnection.setRequestProperty("Authorization","Bearer " + getToken());
            urlConnection.setRequestProperty("Content-Type","application/json");
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
                Gson g = new Gson();
                rateResponse = g.fromJson(sb.toString(), RateResponse.class);
            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateResponse;
    }

    static String responseJson = "{\n" +
            "\t\"fromAddress\": {\n" +
            "\t\t\"company\": \"Pitney Bowes Inc.\",\n" +
            "\t\t\"name\": \"sender_fname\",\n" +
            "\t\t\"phone\": \"2032032033\",\n" +
            "\t\t\"email\": \"sender@email.com\",\n" +
            "\t\t\"residential\": true,\n" +
            "\t\t\"addressLines\": [\n" +
            "\t\t\t\"27 Waterview Drive\"\n" +
            "\t\t],\n" +
            "\t\t\"cityTown\": \"Shelton\",\n" +
            "\t\t\"stateProvince\": \"CT\",\n" +
            "\t\t\"postalCode\": \"06484\",\n" +
            "\t\t\"countryCode\": \"US\",\n" +
            "\t\t\"status\": \"NOT_CHANGED\"\n" +
            "\t},\n" +
            "\t\"toAddress\": {\n" +
            "\t\t\"company\": \"Glorias Co.\",\n" +
            "\t\t\"name\": \"Peter\",\n" +
            "\t\t\"phone\": \"2222222222\",\n" +
            "\t\t\"email\": \"receiver@email.com\",\n" +
            "\t\t\"residential\": true,\n" +
            "\t\t\"addressLines\": [\n" +
            "\t\t\t\"1 Sullivan SQ\"\n" +
            "\t\t],\n" +
            "\t\t\"cityTown\": \"Berwick\",\n" +
            "\t\t\"postalCode\": \"03901\",\n" +
            "\t\t\"countryCode\": \"US\",\n" +
            "\t\t\"status\": \"NOT_CHANGED\"\n" +
            "\t},\n" +
            "\t\"parcel\": {\n" +
            "\t\t\"weight\": {\n" +
            "\t\t\t\"unitOfMeasurement\": \"OZ\",\n" +
            "\t\t\t\"weight\": 1\n" +
            "\t\t},\n" +
            "\t\t\"dimension\": {\n" +
            "\t\t\t\"unitOfMeasurement\": \"IN\",\n" +
            "\t\t\t\"length\": 6,\n" +
            "\t\t\t\"width\": 0.25,\n" +
            "\t\t\t\"height\": 4,\n" +
            "\t\t\t\"irregularParcelGirth\": 0.002\n" +
            "\t\t}\n" +
            "\t},\n" +
            "\t\"rates\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"carrier\": \"usps\",\n" +
            "\t\t\t\"parcelType\": \"PKG\",\n" +
            "\t\t\t\"specialServices\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"specialServiceId\": \"Ins\",\n" +
            "\t\t\t\t\t\"inputParameters\": [\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"name\": \"INPUT_VALUE\",\n" +
            "\t\t\t\t\t\t\t\"value\": \"50\"\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t],\n" +
            "\t\t\t\t\t\"fee\": 0\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"specialServiceId\": \"DelCon\",\n" +
            "\t\t\t\t\t\"inputParameters\": [\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"name\": \"INPUT_VALUE\",\n" +
            "\t\t\t\t\t\t\t\"value\": \"0\"\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t],\n" +
            "\t\t\t\t\t\"fee\": 0\n" +
            "\t\t\t\t}\n" +
            "\t\t\t],\n" +
            "\t\t\t\"serviceId\": \"PM\",\n" +
            "\t\t\t\"rateTypeId\": \"\",\n" +
            "\t\t\t\"deliveryCommitment\": {\n" +
            "\t\t\t\t\"minEstimatedNumberOfDays\": \"2\",\n" +
            "\t\t\t\t\"maxEstimatedNumberOfDays\": \"2\",\n" +
            "\t\t\t\t\"estimatedDeliveryDateTime\": \"2017-10-09\",\n" +
            "\t\t\t\t\"guarantee\": \"NONE\",\n" +
            "\t\t\t\t\"additionalDetails\": \"By end of Day\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"dimensionalWeight\": {\n" +
            "\t\t\t\t\"weight\": 0,\n" +
            "\t\t\t\t\"unitOfMeasurement\": \"OZ\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"baseCharge\": 6.26,\n" +
            "\t\t\t\"totalCarrierCharge\": 6.26,\n" +
            "\t\t\t\"alternateBaseCharge\": 6.46,\n" +
            "\t\t\t\"destinationZone\": \"3\",\n" +
            "\t\t\t\"alternateTotalCharge\": 6.46\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"shipmentOptions\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"name\": \"SHIPPER_BASE_CHARGE\",\n" +
            "\t\t\t\"value\": \"6.46\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"name\": \"SHIPPER_TOTAL_CHARGE\",\n" +
            "\t\t\t\"value\": \"6.46\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    /**
     * As Actual API is not accessible, so created an dummy response
     * @param request
     * @return
     */

    public static RateResponse getDummyRates(String request) {
        Gson g = new Gson();
        RateResponse rateResponse = g.fromJson(responseJson, RateResponse.class);
        return rateResponse;
    }


}
