package com.ffapp.waterprice.bean;


import com.mic.adressselectorlib.DeviceTreeChildListData;

import java.util.ArrayList;

public class DeviceTreeListData extends BasisBean {
    private String name;
    private String value;
    private String icon;
    private String iconSkin;
    private boolean nocheck;
    private boolean open;
    private boolean checked;
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



}
