package com.example.agroobot_fms.model.data_dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("total_project_int")
    @Expose
    private Integer totalProjectInt;
    @SerializedName("total_land_int")
    @Expose
    private Integer totalLandInt;
    @SerializedName("total_area_flo")
    @Expose
    private Double totalAreaFlo;
    @SerializedName("total_land_active_int")
    @Expose
    private Integer totalLandActiveInt;
    @SerializedName("total_area_active_flo")
    @Expose
    private Double totalAreaActiveFlo;
    @SerializedName("total_farmer_int")
    @Expose
    private Integer totalFarmerInt;
    @SerializedName("total_fa_int")
    @Expose
    private Integer totalFaInt;
    @SerializedName("total_rf_int")
    @Expose
    private Integer totalRfInt;
    @SerializedName("total_nonrf_int")
    @Expose
    private Integer totalNonrfInt;
    @SerializedName("stock_beras_int")
    @Expose
    private Integer stockBerasInt;
    @SerializedName("total_gross_revenue_int")
    @Expose
    private Object totalGrossRevenueInt;
    @SerializedName("total_panen_flo")
    @Expose
    private Integer totalPanenFlo;
    @SerializedName("total_gross_beras_int")
    @Expose
    private Integer totalGrossBerasInt;
    @SerializedName("total_gross_beras_campuran_int")
    @Expose
    private Integer totalGrossBerasCampuranInt;
    @SerializedName("total_gross_saprodi_int")
    @Expose
    private Integer totalGrossSaprodiInt;
    @SerializedName("total_funding_int")
    @Expose
    private Integer totalFundingInt;
    @SerializedName("total_realisasi_funding_int")
    @Expose
    private Integer totalRealisasiFundingInt;

    public Integer getTotalProjectInt() {
        return totalProjectInt;
    }

    public void setTotalProjectInt(Integer totalProjectInt) {
        this.totalProjectInt = totalProjectInt;
    }

    public Integer getTotalLandInt() {
        return totalLandInt;
    }

    public void setTotalLandInt(Integer totalLandInt) {
        this.totalLandInt = totalLandInt;
    }

    public Double getTotalAreaFlo() {
        return totalAreaFlo;
    }

    public void setTotalAreaFlo(Double totalAreaFlo) {
        this.totalAreaFlo = totalAreaFlo;
    }

    public Integer getTotalLandActiveInt() {
        return totalLandActiveInt;
    }

    public void setTotalLandActiveInt(Integer totalLandActiveInt) {
        this.totalLandActiveInt = totalLandActiveInt;
    }

    public Double getTotalAreaActiveFlo() {
        return totalAreaActiveFlo;
    }

    public void setTotalAreaActiveFlo(Double totalAreaActiveFlo) {
        this.totalAreaActiveFlo = totalAreaActiveFlo;
    }

    public Integer getTotalFarmerInt() {
        return totalFarmerInt;
    }

    public void setTotalFarmerInt(Integer totalFarmerInt) {
        this.totalFarmerInt = totalFarmerInt;
    }

    public Integer getTotalFaInt() {
        return totalFaInt;
    }

    public void setTotalFaInt(Integer totalFaInt) {
        this.totalFaInt = totalFaInt;
    }

    public Integer getTotalRfInt() {
        return totalRfInt;
    }

    public void setTotalRfInt(Integer totalRfInt) {
        this.totalRfInt = totalRfInt;
    }

    public Integer getTotalNonrfInt() {
        return totalNonrfInt;
    }

    public void setTotalNonrfInt(Integer totalNonrfInt) {
        this.totalNonrfInt = totalNonrfInt;
    }

    public Integer getStockBerasInt() {
        return stockBerasInt;
    }

    public void setStockBerasInt(Integer stockBerasInt) {
        this.stockBerasInt = stockBerasInt;
    }

    public Object getTotalGrossRevenueInt() {
        return totalGrossRevenueInt;
    }

    public void setTotalGrossRevenueInt(Object totalGrossRevenueInt) {
        this.totalGrossRevenueInt = totalGrossRevenueInt;
    }

    public Integer getTotalPanenFlo() {
        return totalPanenFlo;
    }

    public void setTotalPanenFlo(Integer totalPanenFlo) {
        this.totalPanenFlo = totalPanenFlo;
    }

    public Integer getTotalGrossBerasInt() {
        return totalGrossBerasInt;
    }

    public void setTotalGrossBerasInt(Integer totalGrossBerasInt) {
        this.totalGrossBerasInt = totalGrossBerasInt;
    }

    public Integer getTotalGrossBerasCampuranInt() {
        return totalGrossBerasCampuranInt;
    }

    public void setTotalGrossBerasCampuranInt(Integer totalGrossBerasCampuranInt) {
        this.totalGrossBerasCampuranInt = totalGrossBerasCampuranInt;
    }

    public Integer getTotalGrossSaprodiInt() {
        return totalGrossSaprodiInt;
    }

    public void setTotalGrossSaprodiInt(Integer totalGrossSaprodiInt) {
        this.totalGrossSaprodiInt = totalGrossSaprodiInt;
    }

    public Integer getTotalFundingInt() {
        return totalFundingInt;
    }

    public void setTotalFundingInt(Integer totalFundingInt) {
        this.totalFundingInt = totalFundingInt;
    }

    public Integer getTotalRealisasiFundingInt() {
        return totalRealisasiFundingInt;
    }

    public void setTotalRealisasiFundingInt(Integer totalRealisasiFundingInt) {
        this.totalRealisasiFundingInt = totalRealisasiFundingInt;
    }
}
