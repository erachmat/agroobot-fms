package com.example.agroobot_fms.model.create_observation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("time_calender_dte")
    @Expose
    private String timeCalenderDte;
    @SerializedName("leaf_condition_txt")
    @Expose
    private String leafConditionTxt;
    @SerializedName("land_condition_txt")
    @Expose
    private String landConditionTxt;
    @SerializedName("watering_condition_txt")
    @Expose
    private String wateringConditionTxt;
    @SerializedName("puppies_condition_txt")
    @Expose
    private String puppiesConditionTxt;
    @SerializedName("grain_condition_txt")
    @Expose
    private String grainConditionTxt;
    @SerializedName("hama_txt")
    @Expose
    private String hamaTxt;
    @SerializedName("example_observation_txt")
    @Expose
    private String exampleObservationTxt;
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

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getTimeCalenderDte() {
        return timeCalenderDte;
    }

    public void setTimeCalenderDte(String timeCalenderDte) {
        this.timeCalenderDte = timeCalenderDte;
    }

    public String getLeafConditionTxt() {
        return leafConditionTxt;
    }

    public void setLeafConditionTxt(String leafConditionTxt) {
        this.leafConditionTxt = leafConditionTxt;
    }

    public String getLandConditionTxt() {
        return landConditionTxt;
    }

    public void setLandConditionTxt(String landConditionTxt) {
        this.landConditionTxt = landConditionTxt;
    }

    public String getWateringConditionTxt() {
        return wateringConditionTxt;
    }

    public void setWateringConditionTxt(String wateringConditionTxt) {
        this.wateringConditionTxt = wateringConditionTxt;
    }

    public String getPuppiesConditionTxt() {
        return puppiesConditionTxt;
    }

    public void setPuppiesConditionTxt(String puppiesConditionTxt) {
        this.puppiesConditionTxt = puppiesConditionTxt;
    }

    public String getGrainConditionTxt() {
        return grainConditionTxt;
    }

    public void setGrainConditionTxt(String grainConditionTxt) {
        this.grainConditionTxt = grainConditionTxt;
    }

    public String getHamaTxt() {
        return hamaTxt;
    }

    public void setHamaTxt(String hamaTxt) {
        this.hamaTxt = hamaTxt;
    }

    public String getExampleObservationTxt() {
        return exampleObservationTxt;
    }

    public void setExampleObservationTxt(String exampleObservationTxt) {
        this.exampleObservationTxt = exampleObservationTxt;
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
}
