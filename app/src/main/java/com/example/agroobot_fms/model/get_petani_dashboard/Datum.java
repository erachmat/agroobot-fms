package com.example.agroobot_fms.model.get_petani_dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("field_assistant_id_int")
    @Expose
    private Integer fieldAssistantIdInt;
    @SerializedName("total_farmer_int")
    @Expose
    private Integer totalFarmerInt;

    public Integer getFieldAssistantIdInt() {
        return fieldAssistantIdInt;
    }

    public void setFieldAssistantIdInt(Integer fieldAssistantIdInt) {
        this.fieldAssistantIdInt = fieldAssistantIdInt;
    }

    public Integer getTotalFarmerInt() {
        return totalFarmerInt;
    }

    public void setTotalFarmerInt(Integer totalFarmerInt) {
        this.totalFarmerInt = totalFarmerInt;
    }

}
