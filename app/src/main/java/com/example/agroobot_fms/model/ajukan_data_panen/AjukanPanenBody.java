package com.example.agroobot_fms.model.ajukan_data_panen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AjukanPanenBody {
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
