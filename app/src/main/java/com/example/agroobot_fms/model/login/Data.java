package com.example.agroobot_fms.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id_seq")
    @Expose
    private Integer idSeq;
    @SerializedName("user_group_id_int")
    @Expose
    private Integer userGroupIdInt;
    @SerializedName("fullname_var")
    @Expose
    private String fullnameVar;
    @SerializedName("username_var")
    @Expose
    private String usernameVar;
    @SerializedName("email_var")
    @Expose
    private String emailVar;
    @SerializedName("phone_number_int")
    @Expose
    private String phoneNumberInt;
    @SerializedName("allow_web_int")
    @Expose
    private Integer allowWebInt;
    @SerializedName("allow_mobile_int")
    @Expose
    private Integer allowMobileInt;
    @SerializedName("status_int")
    @Expose
    private Integer statusInt;
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
    @SerializedName("last_login_on_dtm")
    @Expose
    private String lastLoginOnDtm;
    @SerializedName("farmer_group_id_int")
    @Expose
    private Integer farmerGroupIdInt;
    @SerializedName("token")
    @Expose
    private String token;

    public Integer getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Integer idSeq) {
        this.idSeq = idSeq;
    }

    public Integer getUserGroupIdInt() {
        return userGroupIdInt;
    }

    public void setUserGroupIdInt(Integer userGroupIdInt) {
        this.userGroupIdInt = userGroupIdInt;
    }

    public String getFullnameVar() {
        return fullnameVar;
    }

    public void setFullnameVar(String fullnameVar) {
        this.fullnameVar = fullnameVar;
    }

    public String getUsernameVar() {
        return usernameVar;
    }

    public void setUsernameVar(String usernameVar) {
        this.usernameVar = usernameVar;
    }

    public String getEmailVar() {
        return emailVar;
    }

    public void setEmailVar(String emailVar) {
        this.emailVar = emailVar;
    }

    public String getPhoneNumberInt() {
        return phoneNumberInt;
    }

    public void setPhoneNumberInt(String phoneNumberInt) {
        this.phoneNumberInt = phoneNumberInt;
    }

    public Integer getAllowWebInt() {
        return allowWebInt;
    }

    public void setAllowWebInt(Integer allowWebInt) {
        this.allowWebInt = allowWebInt;
    }

    public Integer getAllowMobileInt() {
        return allowMobileInt;
    }

    public void setAllowMobileInt(Integer allowMobileInt) {
        this.allowMobileInt = allowMobileInt;
    }

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
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

    public String getLastLoginOnDtm() {
        return lastLoginOnDtm;
    }

    public void setLastLoginOnDtm(String lastLoginOnDtm) {
        this.lastLoginOnDtm = lastLoginOnDtm;
    }

    public Integer getFarmerGroupIdInt() {
        return farmerGroupIdInt;
    }

    public void setFarmerGroupIdInt(Integer farmerGroupIdInt) {
        this.farmerGroupIdInt = farmerGroupIdInt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
