package com.smbhackathon.shipminders.model;

import java.util.Date;

/**
 * Created by RO003AG on 10/23/2017.
 */

public class EventDescription {

        private RateRequest rateRequest;

        private String deliveryDate;

    public RateRequest getRateRequest() {
        return rateRequest;
    }

    public void setRateRequest(RateRequest rateRequest) {
        this.rateRequest = rateRequest;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
