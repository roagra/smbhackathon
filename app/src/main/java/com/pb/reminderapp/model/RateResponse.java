package com.pb.reminderapp.model;

import java.util.List;

/**
 * Created by ro003ag on 10/7/2017.
 */

public class RateResponse {


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
    private String shipmentId;
    private String parcelTrackingNumber;


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

        public List<String> getAddressLines() {
            return addressLines;
        }

        public void setAddressLines(List<String> addressLines) {
            this.addressLines = addressLines;
        }

        public String getCityTown() {
            return cityTown;
        }

        public void setCityTown(String cityTown) {
            this.cityTown = cityTown;
        }

        public String getStateProvince() {
            return stateProvince;
        }

        public void setStateProvince(String stateProvince) {
            this.stateProvince = stateProvince;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getResidential() {
            return residential;
        }

        public void setResidential(Boolean residential) {
            this.residential = residential;
        }

        public String getDeliveryPoint() {
            return deliveryPoint;
        }

        public void setDeliveryPoint(String deliveryPoint) {
            this.deliveryPoint = deliveryPoint;
        }
    }

    public static class Parcel {

        private Dimension dimension;
        private Weight weight;
        private Integer valueOfGoods;
        private String currencyCode;


        public static class Dimension {
            private Double length;
            private Double width;
            private Double height;
            private String irregularParcelGirth;
            private String unitOfMeasurement;
        }

        public static class Weight {
            private Double weight;
            private String unitOfMeasurement;
        }
    }

    public static class Rate {
        private String carrier;
        private String serviceId;
        private String parcelType;
        private String rateTypeId;
        private List<SpecialServices> specialServices;
        private Double fee;
        private String inductionPostalCode;
        private DimensionalWeight dimensionalWeight;
        private Double baseCharge;
        private Double totalCarrierCharge;
        private Double alternateBaseCharge;
        private Double alternateTotalCharge;
        private DeliveryCommitment deliveryCommitment;
        private String currencyCode;
        private String destinationZone;

        public static class SpecialServices {
            private String specialServiceId;
            private List<InputParameters> inputParameters;

            public static class InputParameters {
                private String name;
                private String value;
            }
        }

        public static class DimensionalWeight {
            private Double weight;
            private String unitOfMeasurement;
        }

        public static class DeliveryCommitment {
            private String minEstimatedNumberOfDays;
            private String maxEstimatedNumberOfDays;
            private String estimatedDeliveryDateTime;
            private String guarantee;
            private String additionalDetails;

            public String getMinEstimatedNumberOfDays() {
                return minEstimatedNumberOfDays;
            }

            public void setMinEstimatedNumberOfDays(String minEstimatedNumberOfDays) {
                this.minEstimatedNumberOfDays = minEstimatedNumberOfDays;
            }

            public String getMaxEstimatedNumberOfDays() {
                return maxEstimatedNumberOfDays;
            }

            public void setMaxEstimatedNumberOfDays(String maxEstimatedNumberOfDays) {
                this.maxEstimatedNumberOfDays = maxEstimatedNumberOfDays;
            }

            public String getEstimatedDeliveryDateTime() {
                return estimatedDeliveryDateTime;
            }

            public void setEstimatedDeliveryDateTime(String estimatedDeliveryDateTime) {
                this.estimatedDeliveryDateTime = estimatedDeliveryDateTime;
            }

            public String getGuarantee() {
                return guarantee;
            }

            public void setGuarantee(String guarantee) {
                this.guarantee = guarantee;
            }

            public String getAdditionalDetails() {
                return additionalDetails;
            }

            public void setAdditionalDetails(String additionalDetails) {
                this.additionalDetails = additionalDetails;
            }
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getParcelType() {
            return parcelType;
        }

        public void setParcelType(String parcelType) {
            this.parcelType = parcelType;
        }

        public String getRateTypeId() {
            return rateTypeId;
        }

        public void setRateTypeId(String rateTypeId) {
            this.rateTypeId = rateTypeId;
        }

        public List<SpecialServices> getSpecialServices() {
            return specialServices;
        }

        public void setSpecialServices(List<SpecialServices> specialServices) {
            this.specialServices = specialServices;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }

        public String getInductionPostalCode() {
            return inductionPostalCode;
        }

        public void setInductionPostalCode(String inductionPostalCode) {
            this.inductionPostalCode = inductionPostalCode;
        }

        public DimensionalWeight getDimensionalWeight() {
            return dimensionalWeight;
        }

        public void setDimensionalWeight(DimensionalWeight dimensionalWeight) {
            this.dimensionalWeight = dimensionalWeight;
        }

        public Double getBaseCharge() {
            return baseCharge;
        }

        public void setBaseCharge(Double baseCharge) {
            this.baseCharge = baseCharge;
        }

        public Double getTotalCarrierCharge() {
            return totalCarrierCharge;
        }

        public void setTotalCarrierCharge(Double totalCarrierCharge) {
            this.totalCarrierCharge = totalCarrierCharge;
        }

        public Double getAlternateBaseCharge() {
            return alternateBaseCharge;
        }

        public void setAlternateBaseCharge(Double alternateBaseCharge) {
            this.alternateBaseCharge = alternateBaseCharge;
        }

        public Double getAlternateTotalCharge() {
            return alternateTotalCharge;
        }

        public void setAlternateTotalCharge(Double alternateTotalCharge) {
            this.alternateTotalCharge = alternateTotalCharge;
        }

        public DeliveryCommitment getDeliveryCommitment() {
            return deliveryCommitment;
        }

        public void setDeliveryCommitment(DeliveryCommitment deliveryCommitment) {
            this.deliveryCommitment = deliveryCommitment;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getDestinationZone() {
            return destinationZone;
        }

        public void setDestinationZone(String destinationZone) {
            this.destinationZone = destinationZone;
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

    public String getIncludeDeliveryCommitment() {
        return includeDeliveryCommitment;
    }

    public void setIncludeDeliveryCommitment(String includeDeliveryCommitment) {
        this.includeDeliveryCommitment = includeDeliveryCommitment;
    }

    public Address getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(Address fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Address getToAddress() {
        return toAddress;
    }

    public void setToAddress(Address toAddress) {
        this.toAddress = toAddress;
    }

    public Address getAltReturnAddress() {
        return altReturnAddress;
    }

    public void setAltReturnAddress(Address altReturnAddress) {
        this.altReturnAddress = altReturnAddress;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<ShipmentOption> getShipmentOptions() {
        return shipmentOptions;
    }

    public void setShipmentOptions(List<ShipmentOption> shipmentOptions) {
        this.shipmentOptions = shipmentOptions;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getParcelTrackingNumber() {
        return parcelTrackingNumber;
    }

    public void setParcelTrackingNumber(String parcelTrackingNumber) {
        this.parcelTrackingNumber = parcelTrackingNumber;
    }
}
