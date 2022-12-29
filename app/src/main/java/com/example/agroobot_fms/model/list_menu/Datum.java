package com.example.agroobot_fms.model.list_menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("menu_id_int")
    @Expose
    private Integer menuIdInt;
    @SerializedName("name_var")
    @Expose
    private String nameVar;
    @SerializedName("url_var")
    @Expose
    private String urlVar;
    @SerializedName("icon_var")
    @Expose
    private String iconVar;
    @SerializedName("children")
    @Expose
    private List<Child> children = null;

    public Integer getMenuIdInt() {
        return menuIdInt;
    }

    public void setMenuIdInt(Integer menuIdInt) {
        this.menuIdInt = menuIdInt;
    }

    public String getNameVar() {
        return nameVar;
    }

    public void setNameVar(String nameVar) {
        this.nameVar = nameVar;
    }

    public String getUrlVar() {
        return urlVar;
    }

    public void setUrlVar(String urlVar) {
        this.urlVar = urlVar;
    }

    public String getIconVar() {
        return iconVar;
    }

    public void setIconVar(String iconVar) {
        this.iconVar = iconVar;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
