package com.example.agroobot_fms.model.list_menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMenuBody {
    @SerializedName("user_group_id_int")
    @Expose
    private String userGroupIdInt;

    public String getUserGroupIdInt() {
        return userGroupIdInt;
    }

    public void setUserGroupIdInt(String userGroupIdInt) {
        this.userGroupIdInt = userGroupIdInt;
    }
}
