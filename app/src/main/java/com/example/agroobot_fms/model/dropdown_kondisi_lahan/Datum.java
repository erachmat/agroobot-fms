package com.example.agroobot_fms.model.dropdown_kondisi_lahan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("land_condition_var")
    @Expose
    private String landConditionVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getLandConditionVar() {
        return landConditionVar;
    }

    public void setLandConditionVar(String landConditionVar) {
        this.landConditionVar = landConditionVar;
    }
}
