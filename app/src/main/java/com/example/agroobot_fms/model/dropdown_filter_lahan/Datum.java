package com.example.agroobot_fms.model.dropdown_filter_lahan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("user_id_int")
    @Expose
    private Integer userIdInt;
    @SerializedName("project_code_var")
    @Expose
    private String projectCodeVar;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("land_name_var")
    @Expose
    private String landNameVar;
    @SerializedName("address_land_txt")
    @Expose
    private String addressLandTxt;
    @SerializedName("district_var")
    @Expose
    private String districtVar;
    @SerializedName("city_var")
    @Expose
    private String cityVar;
    @SerializedName("province_var")
    @Expose
    private String provinceVar;
    @SerializedName("postal_code_int")
    @Expose
    private String postalCodeInt;
    @SerializedName("area_var")
    @Expose
    private String areaVar;
    @SerializedName("land_coordinates_txt")
    @Expose
    private String landCoordinatesTxt;
    @SerializedName("status_int")
    @Expose
    private Integer statusInt;
    @SerializedName("created_by_var")
    @Expose
    private String createdByVar;
    @SerializedName("created_on_dtm")
    @Expose
    private String createdOnDtm;
    @SerializedName("updated_by_var")
    @Expose
    private Object updatedByVar;
    @SerializedName("updated_on_dtm")
    @Expose
    private Object updatedOnDtm;
    @SerializedName("field_assistant_id_int")
    @Expose
    private Integer fieldAssistantIdInt;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public Integer getUserIdInt() {
        return userIdInt;
    }

    public void setUserIdInt(Integer userIdInt) {
        this.userIdInt = userIdInt;
    }

    public String getProjectCodeVar() {
        return projectCodeVar;
    }

    public void setProjectCodeVar(String projectCodeVar) {
        this.projectCodeVar = projectCodeVar;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getLandNameVar() {
        return landNameVar;
    }

    public void setLandNameVar(String landNameVar) {
        this.landNameVar = landNameVar;
    }

    public String getAddressLandTxt() {
        return addressLandTxt;
    }

    public void setAddressLandTxt(String addressLandTxt) {
        this.addressLandTxt = addressLandTxt;
    }

    public String getDistrictVar() {
        return districtVar;
    }

    public void setDistrictVar(String districtVar) {
        this.districtVar = districtVar;
    }

    public String getCityVar() {
        return cityVar;
    }

    public void setCityVar(String cityVar) {
        this.cityVar = cityVar;
    }

    public String getProvinceVar() {
        return provinceVar;
    }

    public void setProvinceVar(String provinceVar) {
        this.provinceVar = provinceVar;
    }

    public String getPostalCodeInt() {
        return postalCodeInt;
    }

    public void setPostalCodeInt(String postalCodeInt) {
        this.postalCodeInt = postalCodeInt;
    }

    public String getAreaVar() {
        return areaVar;
    }

    public void setAreaVar(String areaVar) {
        this.areaVar = areaVar;
    }

    public String getLandCoordinatesTxt() {
        return landCoordinatesTxt;
    }

    public void setLandCoordinatesTxt(String landCoordinatesTxt) {
        this.landCoordinatesTxt = landCoordinatesTxt;
    }

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    public String getCreatedByVar() {
        return createdByVar;
    }

    public void setCreatedByVar(String createdByVar) {
        this.createdByVar = createdByVar;
    }

    public String getCreatedOnDtm() {
        return createdOnDtm;
    }

    public void setCreatedOnDtm(String createdOnDtm) {
        this.createdOnDtm = createdOnDtm;
    }

    public Object getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(Object updatedByVar) {
        this.updatedByVar = updatedByVar;
    }

    public Object getUpdatedOnDtm() {
        return updatedOnDtm;
    }

    public void setUpdatedOnDtm(Object updatedOnDtm) {
        this.updatedOnDtm = updatedOnDtm;
    }

    public Integer getFieldAssistantIdInt() {
        return fieldAssistantIdInt;
    }

    public void setFieldAssistantIdInt(Integer fieldAssistantIdInt) {
        this.fieldAssistantIdInt = fieldAssistantIdInt;
    }
}
