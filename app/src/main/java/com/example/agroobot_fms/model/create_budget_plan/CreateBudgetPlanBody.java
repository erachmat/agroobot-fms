package com.example.agroobot_fms.model.create_budget_plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBudgetPlanBody {
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
}
