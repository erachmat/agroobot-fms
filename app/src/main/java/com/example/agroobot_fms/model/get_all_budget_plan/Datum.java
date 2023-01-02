package com.example.agroobot_fms.model.get_all_budget_plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("user_id_int")
    @Expose
    private Integer userIdInt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("commodity_name_var")
    @Expose
    private String commodityNameVar;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;
    @SerializedName("budget_plan_var")
    @Expose
    private String budgetPlanVar;
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
    @SerializedName("fullname_var")
    @Expose
    private String fullnameVar;
    @SerializedName("land_name_var")
    @Expose
    private String landNameVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public Integer getUserIdInt() {
        return userIdInt;
    }

    public void setUserIdInt(Integer userIdInt) {
        this.userIdInt = userIdInt;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getCommodityNameVar() {
        return commodityNameVar;
    }

    public void setCommodityNameVar(String commodityNameVar) {
        this.commodityNameVar = commodityNameVar;
    }

    public String getPeriodPlantTxt() {
        return periodPlantTxt;
    }

    public void setPeriodPlantTxt(String periodPlantTxt) {
        this.periodPlantTxt = periodPlantTxt;
    }

    public String getBudgetPlanVar() {
        return budgetPlanVar;
    }

    public void setBudgetPlanVar(String budgetPlanVar) {
        this.budgetPlanVar = budgetPlanVar;
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

    public String getFullnameVar() {
        return fullnameVar;
    }

    public void setFullnameVar(String fullnameVar) {
        this.fullnameVar = fullnameVar;
    }

    public String getLandNameVar() {
        return landNameVar;
    }

    public void setLandNameVar(String landNameVar) {
        this.landNameVar = landNameVar;
    }
}
