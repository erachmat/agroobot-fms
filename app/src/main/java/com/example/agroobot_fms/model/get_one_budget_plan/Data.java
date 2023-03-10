package com.example.agroobot_fms.model.get_one_budget_plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("budget_plan")
    @Expose
    private List<BudgetPlan> budgetPlan = null;
    @SerializedName("budget_detail")
    @Expose
    private List<BudgetDetail> budgetDetail = null;

    public List<BudgetPlan> getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(List<BudgetPlan> budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public List<BudgetDetail> getBudgetDetail() {
        return budgetDetail;
    }

    public void setBudgetDetail(List<BudgetDetail> budgetDetail) {
        this.budgetDetail = budgetDetail;
    }
}
