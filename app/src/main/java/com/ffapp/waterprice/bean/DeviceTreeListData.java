package com.ffapp.waterprice.bean;

import java.util.List;

public class DeviceTreeListData extends BasisBean {
    private String name;
    private String value;
    private String icon;
    private String iconSkin;
    private boolean nocheck;
    private boolean open;
    private boolean checked;
    private List<Children> children;
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

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }



    class Children extends BasisBean{

        private String name;
        private String value;
        private String icon;
        private String iconSkin;
        private boolean nocheck;
        private boolean open;
        private boolean checked;
        private List<Children> children;
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

        public void setChildren(List<Children> children) {
            this.children = children;
        }
        public List<Children> getChildren() {
            return children;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

    }


}
