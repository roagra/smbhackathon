package com.pb.reminderapp.model;

import java.util.List;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class RateRequest {


    private String includeDeliveryCommitment;
    private Address fromAddress;
    private Address toAddress;
    private Address altReturnAddress;
    private Parcel parcel;
    private List<Rate> rates;
    private List<Document> documents;
    private List<ShipmentOption> shipmentOptions;
    private Custom custom;
    private String shipmentType;


    public static class Address {
        private List<String> addressLines;
        private String cityTown;
        private String stateProvince;
        private String postalCode;
        private String countryCode;
        private String company;
        private String name;
        private String phone;
        private String email;
        private Boolean residential;
        private String deliveryPoint;
    }

    public static class Parcel {

        private Dimension dimension;
        private Weight weight;
        private Integer valueOfGoods;
        private String currencyCode;


        public static class Dimension {
            private Integer length;
            private Integer width;
            private Integer height;
            private String irregularParcelGirth;
            private String unitOfMeasurement;
        }

        public static class Weight {
            private Integer weight;
            private String unitOfMeasurement;
        }
    }

    public static class Rate {
        private String carrier;
        private String serviceId;
        private String parcelType;
        private List<SpecialServices> specialServices;
        private Integer fee;
        private String inductionPostalCode;
        private DimensionalWeight dimensionalWeight;
        private Integer baseCharge;
        private Integer totalCarrierCharge;
        private Integer alternateBaseCharge;
        private Integer alternateTotalCharge;
        private DeliveryCommitment deliveryCommitment;
        private String currencyCode;
        private Integer destinationZone;

        public static class SpecialServices {
            private String specialServiceId;
            private List<InputParameters> inputParameters;

            public static class InputParameters {
                private String name;
                private String value;
            }
        }

        public static class DimensionalWeight {
            private Integer weight;
            private String unitOfMeasurement;
        }

        public static class DeliveryCommitment {
            private String minEstimatedNumberOfDays;
            private String maxEstimatedNumberOfDays;
            private String estimatedDeliveryDateTime;
            private String guarantee;
            private String additionalDetails;
        }

    }

    public static class Document {
        private String type;
        private String size;
        private String fileFormat;
        private String contentType;
        private String printDialogOption;
        private String contents;
        private List<String> pages;
    }

    public static class ShipmentOption {
        private String name;
        private String value;
    }

    public static class Custom {
        private String comment;
    }
}
