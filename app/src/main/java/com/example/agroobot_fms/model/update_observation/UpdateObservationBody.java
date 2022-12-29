package com.example.agroobot_fms.model.update_observation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateObservationBody {
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
    @SerializedName("updated_by_var")
    @Expose
    private String updatedByVar;

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

    public String getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(String updatedByVar) {
        this.updatedByVar = updatedByVar;
    }
}
