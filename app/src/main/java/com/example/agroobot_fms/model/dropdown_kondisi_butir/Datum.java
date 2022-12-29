package com.example.agroobot_fms.model.dropdown_kondisi_butir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("grain_condition_var")
    @Expose
    private String grainConditionVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getGrainConditionVar() {
        return grainConditionVar;
    }

    public void setGrainConditionVar(String grainConditionVar) {
        this.grainConditionVar = grainConditionVar;
    }
}
