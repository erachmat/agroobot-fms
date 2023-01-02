package com.example.agroobot_fms.model.dropdown_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("category_var")
    @Expose
    private String categoryVar;

    public String getCategoryVar() {
        return categoryVar;
    }

    public void setCategoryVar(String categoryVar) {
        this.categoryVar = categoryVar;
    }

}
