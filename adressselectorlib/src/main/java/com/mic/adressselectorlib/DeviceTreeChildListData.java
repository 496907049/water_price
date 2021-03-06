package com.mic.adressselectorlib;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceTreeChildListData implements Serializable {

    private String name;
    private String value;
    private String icon;
    private String iconSkin;
    private boolean nocheck;
    private boolean open;
    private boolean checked;
    private String code;
    private ArrayList<DeviceTreeChildListData> children;
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean getNocheck() {
        return nocheck;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getOpen() {
        return open;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChildren(ArrayList<DeviceTreeChildListData> children) {
        this.children = children;
    }

    public ArrayList<DeviceTreeChildListData> getChildren() {
        return children;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
