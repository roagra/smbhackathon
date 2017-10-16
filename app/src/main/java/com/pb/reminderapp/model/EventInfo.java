package com.pb.reminderapp.model;

import android.widget.ImageView;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class EventInfo {
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDaysToShip() {
        return daysToShip;
    }

    public void setDaysToShip(String daysToShip) {
        this.daysToShip = daysToShip;
    }

    public String getShipmentAmount() {
        return shipmentAmount;
    }

    public void setShipmentAmount(String shipmentAmount) {
        this.shipmentAmount = shipmentAmount;
    }

    private String eventTitle;

    private String daysToShip;

    private String shipmentAmount;


    public ImageView getEventImage() {
        return eventImage;
    }

    public void setEventImage(ImageView eventImage) {
        this.eventImage = eventImage;
    }

    private ImageView eventImage;




}
