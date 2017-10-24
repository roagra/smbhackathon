package com.pb.reminderapp.model;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class EventDetails {

    private String eventTitle;

    private String eventStartDate;

    private String eventDescription;

    private RateRequest rateRequest;

    public RateRequest getRateRequest() {
        return rateRequest;
    }

    public void setRateRequest(RateRequest rateRequest) {
        this.rateRequest = rateRequest;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }
}
