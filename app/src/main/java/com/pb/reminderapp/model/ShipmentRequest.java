package com.pb.reminderapp.model;

import java.util.List;

/**
 * Created by RO003AG on 10/25/2017.
 */

public class ShipmentRequest {

    private RateRequest.Address fromAddress;
    private RateRequest.Address toAddress;

    private RateRequest.Parcel parcel;
    private List<RateRequest.Rate> rates;



}
