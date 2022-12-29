package com.example.agroobot_fms.model.dropdown_kondisi_anakan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("puppies_condition_var")
    @Expose
    private String puppiesConditionVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getPuppiesConditionVar() {
        return puppiesConditionVar;
    }

    public void setPuppiesConditionVar(String puppiesConditionVar) {
        this.puppiesConditionVar = puppiesConditionVar;
    }
}
