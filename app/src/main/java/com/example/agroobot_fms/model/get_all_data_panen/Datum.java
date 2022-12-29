package com.example.agroobot_fms.model.get_all_data_panen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("id_seq")
    @Expose
    private String idSeq;
    @SerializedName("commodity_name_var")
    @Expose
    private String commodityNameVar;
    @SerializedName("land_code_var")
    @Expose
    private String landCodeVar;
    @SerializedName("period_plant_txt")
    @Expose
    private String periodPlantTxt;
    @SerializedName("harvest_flo")
    @Expose
    private Integer harvestFlo;
    @SerializedName("harvest_on_dte")
    @Expose
    private String harvestOnDte;
    @SerializedName("harvest_drying_flo")
    @Expose
    private Integer harvestDryingFlo;
    @SerializedName("harvest_drying_dte")
    @Expose
    private String harvestDryingDte;
    @SerializedName("harvest_milling_flo")
    @Expose
    private Integer harvestMillingFlo;
    @SerializedName("harvest_milling_dte")
    @Expose
    private String harvestMillingDte;
    @SerializedName("document_txt")
    @Expose
    private List<Object> documentTxt = null;
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
    @SerializedName("status_int")
    @Expose
    private Integer statusInt;
    @SerializedName("status_name_var")
    @Expose
    private String statusNameVar;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getCommodityNameVar() {
        return commodityNameVar;
    }

    public void setCommodityNameVar(String commodityNameVar) {
        this.commodityNameVar = commodityNameVar;
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

    public Integer getHarvestFlo() {
        return harvestFlo;
    }

    public void setHarvestFlo(Integer harvestFlo) {
        this.harvestFlo = harvestFlo;
    }

    public String getHarvestOnDte() {
        return harvestOnDte;
    }

    public void setHarvestOnDte(String harvestOnDte) {
        this.harvestOnDte = harvestOnDte;
    }

    public Integer getHarvestDryingFlo() {
        return harvestDryingFlo;
    }

    public void setHarvestDryingFlo(Integer harvestDryingFlo) {
        this.harvestDryingFlo = harvestDryingFlo;
    }

    public String getHarvestDryingDte() {
        return harvestDryingDte;
    }

    public void setHarvestDryingDte(String harvestDryingDte) {
        this.harvestDryingDte = harvestDryingDte;
    }

    public Integer getHarvestMillingFlo() {
        return harvestMillingFlo;
    }

    public void setHarvestMillingFlo(Integer harvestMillingFlo) {
        this.harvestMillingFlo = harvestMillingFlo;
    }

    public String getHarvestMillingDte() {
        return harvestMillingDte;
    }

    public void setHarvestMillingDte(String harvestMillingDte) {
        this.harvestMillingDte = harvestMillingDte;
    }

    public List<Object> getDocumentTxt() {
        return documentTxt;
    }

    public void setDocumentTxt(List<Object> documentTxt) {
        this.documentTxt = documentTxt;
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

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    public String getStatusNameVar() {
        return statusNameVar;
    }

    public void setStatusNameVar(String statusNameVar) {
        this.statusNameVar = statusNameVar;
    }
}
