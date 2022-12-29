package com.example.agroobot_fms.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBody {
    @SerializedName("phone_number_int")
    @Expose
    private String phoneNumberInt;
    @SerializedName("password_var")
    @Expose
    private String passwordVar;

    public String getPhoneNumberInt() {
        return phoneNumberInt;
    }

    public void setPhoneNumberInt(String phoneNumberInt) {
        this.phoneNumberInt = phoneNumberInt;
    }

    public String getPasswordVar() {
        return passwordVar;
    }

    public void setPasswordVar(String passwordVar) {
        this.passwordVar = passwordVar;
    }
}
