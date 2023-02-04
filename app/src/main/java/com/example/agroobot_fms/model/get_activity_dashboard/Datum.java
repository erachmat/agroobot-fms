package com.example.agroobot_fms.model.get_activity_dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("time_calender_dte")
    @Expose
    private String timeCalenderDte;
    @SerializedName("time_txt")
    @Expose
    private String timeTxt;
    @SerializedName("activity_txt")
    @Expose
    private String activityTxt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("commodity_name")
    @Expose
    private String commodityName;
    @SerializedName("name_planting")
    @Expose
    private String namePlanting;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;
    @SerializedName("land_name_var")
    @Expose
    private String landNameVar;
    @SerializedName("user_id_int")
    @Expose
    private Integer userIdInt;
    @SerializedName("field_assistant_id_int")
    @Expose
    private Integer fieldAssistantIdInt;
    @SerializedName("fullname_var")
    @Expose
    private String fullnameVar;
    @SerializedName("field_assistant_name_var")
    @Expose
    private String fieldAssistantNameVar;

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

    public String getTimeTxt() {
        return timeTxt;
    }

    public void setTimeTxt(String timeTxt) {
        this.timeTxt = timeTxt;
    }

    public String getActivityTxt() {
        return activityTxt;
    }

    public void setActivityTxt(String activityTxt) {
        this.activityTxt = activityTxt;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getNamePlanting() {
        return namePlanting;
    }

    public void setNamePlanting(String namePlanting) {
        this.namePlanting = namePlanting;
    }

    public String getPeriodPlantTxt() {
        return periodPlantTxt;
    }

    public void setPeriodPlantTxt(String periodPlantTxt) {
        this.periodPlantTxt = periodPlantTxt;
    }

    public String getLandNameVar() {
        return landNameVar;
    }

    public void setLandNameVar(String landNameVar) {
        this.landNameVar = landNameVar;
    }

    public Integer getUserIdInt() {
        return userIdInt;
    }

    public void setUserIdInt(Integer userIdInt) {
        this.userIdInt = userIdInt;
    }

    public Integer getFieldAssistantIdInt() {
        return fieldAssistantIdInt;
    }

    public void setFieldAssistantIdInt(Integer fieldAssistantIdInt) {
        this.fieldAssistantIdInt = fieldAssistantIdInt;
    }

    public String getFullnameVar() {
        return fullnameVar;
    }

    public void setFullnameVar(String fullnameVar) {
        this.fullnameVar = fullnameVar;
    }

    public String getFieldAssistantNameVar() {
        return fieldAssistantNameVar;
    }

    public void setFieldAssistantNameVar(String fieldAssistantNameVar) {
        this.fieldAssistantNameVar = fieldAssistantNameVar;
    }

}
