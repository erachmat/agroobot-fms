package com.example.agroobot_fms.model.update_budget_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("activity_txt")
    @Expose
    private String activityTxt;
    @SerializedName("category_var")
    @Expose
    private String categoryVar;
    @SerializedName("area_var")
    @Expose
    private String areaVar;
    @SerializedName("quantity_var")
    @Expose
    private String quantityVar;
    @SerializedName("satuan_var")
    @Expose
    private String satuanVar;
    @SerializedName("price_var")
    @Expose
    private String priceVar;
    @SerializedName("total_price_var")
    @Expose
    private String totalPriceVar;
    @SerializedName("status_int")
    @Expose
    private Integer statusInt;
    @SerializedName("created_by_var")
    @Expose
    private String createdByVar;
    @SerializedName("created_on_dtm")
    @Expose
    private String createdOnDtm;
    @SerializedName("updated_by_var")
    @Expose
    private String updatedByVar;
    @SerializedName("updated_on_dtm")
    @Expose
    private String updatedOnDtm;
    @SerializedName("budget_id_int")
    @Expose
    private Integer budgetIdInt;
    @SerializedName("document_txt")
    @Expose
    private List<Object> documentTxt = null;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getActivityTxt() {
        return activityTxt;
    }

    public void setActivityTxt(String activityTxt) {
        this.activityTxt = activityTxt;
    }

    public String getCategoryVar() {
        return categoryVar;
    }

    public void setCategoryVar(String categoryVar) {
        this.categoryVar = categoryVar;
    }

    public String getAreaVar() {
        return areaVar;
    }

    public void setAreaVar(String areaVar) {
        this.areaVar = areaVar;
    }

    public String getQuantityVar() {
        return quantityVar;
    }

    public void setQuantityVar(String quantityVar) {
        this.quantityVar = quantityVar;
    }

    public String getSatuanVar() {
        return satuanVar;
    }

    public void setSatuanVar(String satuanVar) {
        this.satuanVar = satuanVar;
    }

    public String getPriceVar() {
        return priceVar;
    }

    public void setPriceVar(String priceVar) {
        this.priceVar = priceVar;
    }

    public String getTotalPriceVar() {
        return totalPriceVar;
    }

    public void setTotalPriceVar(String totalPriceVar) {
        this.totalPriceVar = totalPriceVar;
    }

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
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

    public String getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(String updatedByVar) {
        this.updatedByVar = updatedByVar;
    }

    public String getUpdatedOnDtm() {
        return updatedOnDtm;
    }

    public void setUpdatedOnDtm(String updatedOnDtm) {
        this.updatedOnDtm = updatedOnDtm;
    }

    public Integer getBudgetIdInt() {
        return budgetIdInt;
    }

    public void setBudgetIdInt(Integer budgetIdInt) {
        this.budgetIdInt = budgetIdInt;
    }

    public List<Object> getDocumentTxt() {
        return documentTxt;
    }

    public void setDocumentTxt(List<Object> documentTxt) {
        this.documentTxt = documentTxt;
    }
}
