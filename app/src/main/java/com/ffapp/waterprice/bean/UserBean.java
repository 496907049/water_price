package com.ffapp.waterprice.bean;

public class UserBean extends BasisBean{

    private String id;
    private String username;
    private String password;
    private String realname;
    private String phoneNumber;
    private int locked;
    private String email;
    private String areaId;
    private long createAt;
    private long updateAt;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getRealname() {
        return realname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
    public int getLocked() {
        return locked;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getAreaId() {
        return areaId;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
    public long getCreateAt() {
        return createAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
    public long getUpdateAt() {
        return updateAt;
    }

}
