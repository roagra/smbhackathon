package com.smbhackathon.shipminders.model;

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

        public Dimension getDimension() {
            return dimension;
        }

        public void setDimension(Dimension dimension) {
            this.dimension = dimension;
        }

        public Weight getWeight() {
            return weight;
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Integer getValueOfGoods() {
            return valueOfGoods;
        }

        public void setValueOfGoods(Integer valueOfGoods) {
            this.valueOfGoods = valueOfGoods;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public static class Dimension {
            private Double length;
            private Double width;
            private Double height;
            private String irregularParcelGirth;
            private String unitOfMeasurement;

            public Double getLength() {
                return length;
            }

            public void setLength(Double length) {
                this.length = length;
            }

            public Double getWidth() {
                return width;
            }

            public void setWidth(Double width) {
                this.width = width;
            }

            public Double getHeight() {
                return height;
            }

            public void setHeight(Double height) {
                this.height = height;
            }

            public String getIrregularParcelGirth() {
                return irregularParcelGirth;
            }

            public void setIrregularParcelGirth(String irregularParcelGirth) {
                this.irregularParcelGirth = irregularParcelGirth;
            }

            public String getUnitOfMeasurement() {
                return unitOfMeasurement;
            }

            public void setUnitOfMeasurement(String unitOfMeasurement) {
                this.unitOfMeasurement = unitOfMeasurement;
            }
        }

        public static class Weight {
            private Integer weight;
            private String unitOfMeasurement;

            public Integer getWeight() {
                return weight;
            }

            public void setWeight(Integer weight) {
                this.weight = weight;
            }

            public String getUnitOfMeasurement() {
                return unitOfMeasurement;
            }

            public void setUnitOfMeasurement(String unitOfMeasurement) {
                this.unitOfMeasurement = unitOfMeasurement;
            }
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

        public List<SpecialServices> getSpecialServices() {
            return specialServices;
        }

        public void setSpecialServices(List<SpecialServices> specialServices) {
            this.specialServices = specialServices;
        }

        public Integer getFee() {
            return fee;
        }

        public void setFee(Integer fee) {
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

        public Integer getBaseCharge() {
            return baseCharge;
        }

        public void setBaseCharge(Integer baseCharge) {
            this.baseCharge = baseCharge;
        }

        public Integer getTotalCarrierCharge() {
            return totalCarrierCharge;
        }

        public void setTotalCarrierCharge(Integer totalCarrierCharge) {
            this.totalCarrierCharge = totalCarrierCharge;
        }

        public Integer getAlternateBaseCharge() {
            return alternateBaseCharge;
        }

        public void setAlternateBaseCharge(Integer alternateBaseCharge) {
            this.alternateBaseCharge = alternateBaseCharge;
        }

        public Integer getAlternateTotalCharge() {
            return alternateTotalCharge;
        }

        public void setAlternateTotalCharge(Integer alternateTotalCharge) {
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

        public Integer getDestinationZone() {
            return destinationZone;
        }

        public void setDestinationZone(Integer destinationZone) {
            this.destinationZone = destinationZone;
        }

        public static class SpecialServices {
            private String specialServiceId;
            private List<InputParameters> inputParameters;

            public String getSpecialServiceId() {
                return specialServiceId;
            }

            public void setSpecialServiceId(String specialServiceId) {
                this.specialServiceId = specialServiceId;
            }

            public List<InputParameters> getInputParameters() {
                return inputParameters;
            }

            public void setInputParameters(List<InputParameters> inputParameters) {
                this.inputParameters = inputParameters;
            }

            public static class InputParameters {
                private String name;
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getFileFormat() {
            return fileFormat;
        }

        public void setFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getPrintDialogOption() {
            return printDialogOption;
        }

        public void setPrintDialogOption(String printDialogOption) {
            this.printDialogOption = printDialogOption;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public List<String> getPages() {
            return pages;
        }

        public void setPages(List<String> pages) {
            this.pages = pages;
        }
    }

    public static class ShipmentOption {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Custom {
        private String comment;
    }
}
