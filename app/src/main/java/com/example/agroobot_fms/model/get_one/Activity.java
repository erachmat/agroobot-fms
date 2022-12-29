package com.example.agroobot_fms.model.get_one;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Activity {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("time_calender_dte")
    @Expose
    private String timeCalenderDte;
    @SerializedName("time_txt")
    @Expose
    private String timeTxt;
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
    private Integer jumlahTxt;
    @SerializedName("satuan_txt")
    @Expose
    private String satuanTxt;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("commodity_name")
    @Expose
    private String commodityName;
    @SerializedName("name_planting")
    @Expose
    private String namePlanting;

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

    public String getTimeTxt() {
        return timeTxt;
    }

    public void setTimeTxt(String timeTxt) {
        this.timeTxt = timeTxt;
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

    public Integer getJumlahTxt() {
        return jumlahTxt;
    }

    public void setJumlahTxt(Integer jumlahTxt) {
        this.jumlahTxt = jumlahTxt;
    }

    public String getSatuanTxt() {
        return satuanTxt;
    }

    public void setSatuanTxt(String satuanTxt) {
        this.satuanTxt = satuanTxt;
    }

    public String getLandCodeVar() {
        return landCodeVar;
    }

    public void setLandCodeVar(String landCodeVar) {
        this.landCodeVar = landCodeVar;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getNamePlanting() {
        return namePlanting;
    }

    public void setNamePlanting(String namePlanting) {
        this.namePlanting = namePlanting;
    }
}
