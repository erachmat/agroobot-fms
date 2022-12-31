package com.example.agroobot_fms.model.delete_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteRatingBody {
    @SerializedName("user_id_int")
    @Expose
    private String userIdInt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;

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
}
