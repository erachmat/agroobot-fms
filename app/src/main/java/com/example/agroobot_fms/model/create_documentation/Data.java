package com.example.agroobot_fms.model.create_documentation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("time_calender_dte")
    @Expose
    private String timeCalenderDte;
    @SerializedName("document_txt")
    @Expose
    private String documentTxt;
    @SerializedName("created_by_var")
    @Expose
    private String createdByVar;
    @SerializedName("created_on_dtm")
    @Expose
    private String createdOnDtm;
    @SerializedName("updated_by_var")
    @Expose
    private Object updatedByVar;
    @SerializedName("updated_on_dtm")
    @Expose
    private Object updatedOnDtm;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getTimeCalenderDte() {
        return timeCalenderDte;
    }

    public void setTimeCalenderDte(String timeCalenderDte) {
        this.timeCalenderDte = timeCalenderDte;
    }

    public String getDocumentTxt() {
        return documentTxt;
    }

    public void setDocumentTxt(String documentTxt) {
        this.documentTxt = documentTxt;
    }

    public String getCreatedByVar() {
        return createdByVar;
    }

    public void setCreatedByVar(String createdByVar) {
        this.createdByVar = createdByVar;
    }

    public String getCreatedOnDtm() {
        return createdOnDtm;
    }

    public void setCreatedOnDtm(String createdOnDtm) {
        this.createdOnDtm = createdOnDtm;
    }

    public Object getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(Object updatedByVar) {
        this.updatedByVar = updatedByVar;
    }

    public Object getUpdatedOnDtm() {
        return updatedOnDtm;
    }

    public void setUpdatedOnDtm(Object updatedOnDtm) {
        this.updatedOnDtm = updatedOnDtm;
    }
}
