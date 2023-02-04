package com.example.agroobot_fms.model.batal_ajukan_budget_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatalAjukanBudgetDetailBody {
    @SerializedName("updated_by_var")
    @Expose
    private String updatedByVar;

    public String getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(String updatedByVar) {
        this.updatedByVar = updatedByVar;
    }

}
