package com.example.agroobot_fms.model.dropdown_farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private Integer idSeq;
    @SerializedName("fullname_var")
    @Expose
    private String fullnameVar;

    public Integer getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Integer idSeq) {
        this.idSeq = idSeq;
    }

    public String getFullnameVar() {
        return fullnameVar;
    }

    public void setFullnameVar(String fullnameVar) {
        this.fullnameVar = fullnameVar;
    }
}
