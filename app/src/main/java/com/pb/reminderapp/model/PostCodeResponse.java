package com.pb.reminderapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RO003AG on 10/24/2017.
 */

public class PostCodeResponse {
    private String mainAddressLine;
    private String addressLastLine;
    private String placeName;
    private String areaName1;
    private String areaName2;
    private String areaName3;
    private String areaName4;
    private String postCode1;
    private String postCode2;
    private String country;
    private String addressNumber;
    private String streetName;
    private String unitType;
    private String unitValue;
    private Map<String, Object> customFields = new HashMap();

    public String getMainAddressLine() {
        return mainAddressLine;
    }

    public void setMainAddressLine(String mainAddressLine) {
        this.mainAddressLine = mainAddressLine;
    }

    public String getAddressLastLine() {
        return addressLastLine;
    }

    public void setAddressLastLine(String addressLastLine) {
        this.addressLastLine = addressLastLine;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAreaName1() {
        return areaName1;
    }

    public void setAreaName1(String areaName1) {
        this.areaName1 = areaName1;
    }

    public String getAreaName2() {
        return areaName2;
    }

    public void setAreaName2(String areaName2) {
        this.areaName2 = areaName2;
    }

    public String getAreaName3() {
        return areaName3;
    }

    public void setAreaName3(String areaName3) {
        this.areaName3 = areaName3;
    }

    public String getAreaName4() {
        return areaName4;
    }

    public void setAreaName4(String areaName4) {
        this.areaName4 = areaName4;
    }

    public String getPostCode1() {
        return postCode1;
    }

    public void setPostCode1(String postCode1) {
        this.postCode1 = postCode1;
    }

    public String getPostCode2() {
        return postCode2;
    }

    public void setPostCode2(String postCode2) {
        this.postCode2 = postCode2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public Map<String, Object> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, Object> customFields) {
        this.customFields = customFields;
    }
}
