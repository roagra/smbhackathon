package com.pb.reminderapp.model;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class EventInfo {

    private String eventTitle;

    private List<ShipmentDetail> ShipmentDetails;

    private String userDeliveryDateTime;

    private String suggestion;

    private String toAddress;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getUserDeliveryDateTime() {
        return userDeliveryDateTime;
    }

    public void setUserDeliveryDateTime(String userDeliveryDateTime) {
        this.userDeliveryDateTime = userDeliveryDateTime;
    }

    public List<ShipmentDetail> getShipmentDetails() {
        return ShipmentDetails;
    }

    public void setShipmentDetails(List<ShipmentDetail> shipmentDetails) {
        ShipmentDetails = shipmentDetails;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    //private ImageView eventImage;

/*    public ImageView getEventImage() {
        return eventImage;
    }

    public void setEventImage(ImageView eventImage) {
        this.eventImage = eventImage;
    }*/

    public static class ShipmentDetail {

        private String estimatedDeliveryDateTime;

        private String shipmentAmount;

        private String serviceType;

        private int priority;

        public String getShipmentAmount() {
            return shipmentAmount;
        }

        public void setShipmentAmount(String shipmentAmount) {
            this.shipmentAmount = shipmentAmount;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getEstimatedDeliveryDateTime() {
            return estimatedDeliveryDateTime;
        }

        public void setEstimatedDeliveryDateTime(String estimatedDeliveryDateTime) {
            this.estimatedDeliveryDateTime = estimatedDeliveryDateTime;
        }
    }
}
