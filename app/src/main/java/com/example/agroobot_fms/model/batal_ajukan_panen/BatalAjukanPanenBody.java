package com.example.agroobot_fms.model.batal_ajukan_panen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatalAjukanPanenBody {
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
