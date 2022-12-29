package com.example.agroobot_fms.model.create_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id_int")
    @Expose
    private String userIdInt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;
    @SerializedName("time_calender_dte")
    @Expose
    private String timeCalenderDte;
    @SerializedName("suggest_txt")
    @Expose
    private String suggestTxt;
    @SerializedName("rating_txt")
    @Expose
    private String ratingTxt;
    @SerializedName("created_by_var")
    @Expose
    private String createdByVar;

    public String getUserIdInt() {
        return userIdInt;
    }

    public void setUserIdInt(String userIdInt) {
        this.userIdInt = userIdInt;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getPeriodPlantTxt() {
        return periodPlantTxt;
    }

    public void setPeriodPlantTxt(String periodPlantTxt) {
        this.periodPlantTxt = periodPlantTxt;
    }

    public String getTimeCalenderDte() {
        return timeCalenderDte;
    }

    public void setTimeCalenderDte(String timeCalenderDte) {
        this.timeCalenderDte = timeCalenderDte;
    }

    public String getSuggestTxt() {
        return suggestTxt;
    }

    public void setSuggestTxt(String suggestTxt) {
        this.suggestTxt = suggestTxt;
    }

    public String getRatingTxt() {
        return ratingTxt;
    }

    public void setRatingTxt(String ratingTxt) {
        this.ratingTxt = ratingTxt;
    }

    public String getCreatedByVar() {
        return createdByVar;
    }

    public void setCreatedByVar(String createdByVar) {
        this.createdByVar = createdByVar;
    }

}
