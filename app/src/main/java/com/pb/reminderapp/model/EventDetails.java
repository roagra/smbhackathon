package com.pb.reminderapp.model;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class EventDetails {

    private String eventId;

    private String eventTitle;

    private String eventStartDate;

    private String eventDescription;

    private String toAddress;

    private String eventStatus;

    private RateRequest rateRequest;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

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
