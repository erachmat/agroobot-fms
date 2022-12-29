package com.example.agroobot_fms.model.dropdown_kondisi_air;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("watering_condition_var")
    @Expose
    private String wateringConditionVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getWateringConditionVar() {
        return wateringConditionVar;
    }

    public void setWateringConditionVar(String wateringConditionVar) {
        this.wateringConditionVar = wateringConditionVar;
    }
}
