package com.pb.reminderapp.model;

import java.util.List;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class EventInfo {

    private String eventTitle;

    private boolean isEnabled;

    private boolean isEarly;

    private boolean isSevere;

    private String eventId;

    private ShippingOption standardShippingOption;

    private ShippingOption pmShippingOption;

    private ShippingOption fmShippingOption;

    private boolean isStandardPostTooEarly;

    private boolean isPriorityMailTooEarly;

    private boolean isFirstClassMailTooEarly;

    private boolean isStandardPostTooLate;

    private boolean isPriorityMailTooLate;

    private boolean isFirstClassMailTooLate;

    public boolean isEarly() {
        return isEarly;
    }

    public void setEarly(boolean early) {
        isEarly = early;
    }

    public boolean isStandardPostTooLate() {
        return isStandardPostTooLate;
    }

    public void setStandardPostTooLate(boolean standardPostTooLate) {
        isStandardPostTooLate = standardPostTooLate;
    }

    public boolean isPriorityMailTooLate() {
        return isPriorityMailTooLate;
    }

    public void setPriorityMailTooLate(boolean priorityMailTooLate) {
        isPriorityMailTooLate = priorityMailTooLate;
    }

    public boolean isFirstClassMailTooLate() {
        return isFirstClassMailTooLate;
    }

    public void setFirstClassMailTooLate(boolean firstClassMailTooLate) {
        isFirstClassMailTooLate = firstClassMailTooLate;
    }

    public boolean isStandardPostTooEarly() {
        return isStandardPostTooEarly;
    }

    public void setStandardPostTooEarly(boolean standardPostTooEarly) {
        isStandardPostTooEarly = standardPostTooEarly;
    }

    public boolean isPriorityMailTooEarly() {
        return isPriorityMailTooEarly;
    }

    public void setPriorityMailTooEarly(boolean priorityMailTooEarly) {
        isPriorityMailTooEarly = priorityMailTooEarly;
    }

    public boolean isFirstClassMailTooEarly() {
        return isFirstClassMailTooEarly;
    }

    public void setFirstClassMailTooEarly(boolean firstClassMailTooEarly) {
        isFirstClassMailTooEarly = firstClassMailTooEarly;
    }

    public boolean isSevere() {
        return isSevere;
    }

    public void setSevere(boolean severe) {
        isSevere = severe;
    }

    public ShippingOption getStandardShippingOption() {
        return standardShippingOption;
    }

    public void setStandardShippingOption(ShippingOption standardShippingOption) {
        this.standardShippingOption = standardShippingOption;
    }

    public ShippingOption getPmShippingOption() {
        return pmShippingOption;
    }

    public void setPmShippingOption(ShippingOption pmShippingOption) {
        this.pmShippingOption = pmShippingOption;
    }

    public ShippingOption getFmShippingOption() {
        return fmShippingOption;
    }

    public void setFmShippingOption(ShippingOption fmShippingOption) {
        this.fmShippingOption = fmShippingOption;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }



    public static class ShippingOption {
        private String mailClass;
        private String note;
        private boolean isSelected;

        public String getMailClass() {
            return mailClass;
        }

        public void setMailClass(String mailClass) {
            this.mailClass = mailClass;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

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
