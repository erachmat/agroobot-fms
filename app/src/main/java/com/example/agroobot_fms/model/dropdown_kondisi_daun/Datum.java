package com.example.agroobot_fms.model.dropdown_kondisi_daun;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("leaf_condition_var")
    @Expose
    private String leafConditionVar;
    @SerializedName("color_code_var")
    @Expose
    private String colorCodeVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getLeafConditionVar() {
        return leafConditionVar;
    }

    public void setLeafConditionVar(String leafConditionVar) {
        this.leafConditionVar = leafConditionVar;
    }

    public String getColorCodeVar() {
        return colorCodeVar;
    }

    public void setColorCodeVar(String colorCodeVar) {
        this.colorCodeVar = colorCodeVar;
    }
}
