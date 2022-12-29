package com.example.agroobot_fms.model.update_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id_int")
    @Expose
    private String userIdInt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;
    @SerializedName("activity_txt")
    @Expose
    private String activityTxt;
    @SerializedName("material_txt")
    @Expose
    private String materialTxt;
    @SerializedName("dose_txt")
    @Expose
    private String doseTxt;
    @SerializedName("jumlah_txt")
    @Expose
    private String jumlahTxt;
    @SerializedName("satuan_txt")
    @Expose
    private String satuanTxt;
    @SerializedName("updated_by_var")
    @Expose
    private String updatedByVar;
    @SerializedName("type_txt")
    @Expose
    private String typeTxt;
    @SerializedName("updated_on_dtm")
    @Expose
    private String updatedOnDtm;

    public String getUserIdInt() {
        return userIdInt;
    }

    public void setUserIdInt(String userIdInt) {
        this.userIdInt = userIdInt;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getPeriodPlantTxt() {
        return periodPlantTxt;
    }

    public void setPeriodPlantTxt(String periodPlantTxt) {
        this.periodPlantTxt = periodPlantTxt;
    }

    public String getActivityTxt() {
        return activityTxt;
    }

    public void setActivityTxt(String activityTxt) {
        this.activityTxt = activityTxt;
    }

    public String getMaterialTxt() {
        return materialTxt;
    }

    public void setMaterialTxt(String materialTxt) {
        this.materialTxt = materialTxt;
    }

    public String getDoseTxt() {
        return doseTxt;
    }

    public void setDoseTxt(String doseTxt) {
        this.doseTxt = doseTxt;
    }

    public String getJumlahTxt() {
        return jumlahTxt;
    }

    public void setJumlahTxt(String jumlahTxt) {
        this.jumlahTxt = jumlahTxt;
    }

    public String getSatuanTxt() {
        return satuanTxt;
    }

    public void setSatuanTxt(String satuanTxt) {
        this.satuanTxt = satuanTxt;
    }

    public String getUpdatedByVar() {
        return updatedByVar;
    }

    public void setUpdatedByVar(String updatedByVar) {
        this.updatedByVar = updatedByVar;
    }

    public String getTypeTxt() {
        return typeTxt;
    }

    public void setTypeTxt(String typeTxt) {
        this.typeTxt = typeTxt;
    }

    public String getUpdatedOnDtm() {
        return updatedOnDtm;
    }

    public void setUpdatedOnDtm(String updatedOnDtm) {
        this.updatedOnDtm = updatedOnDtm;
    }
}
