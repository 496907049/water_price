package com.ffapp.waterprice.bean;


import android.content.Context;
import android.text.TextUtils;

import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.jpush.TagAliasOperatorHelper;

import java.util.Date;

import my.FileUtils;
import my.MySharedPreferences;


public class LoginBean extends BasisBean implements Cloneable {
    private static final long serialVersionUID = 1L;

    public static final String FILE_CACHE = Constants.DIR_FILECACHE
            + "login.data";
    private static LoginBean sLoginBean;
    /**
     * username : admin
     * realname : 管理员
     * mobile : 139****9219
     * nickname :
     * token : cb3dcf01e5492ba76bdc65211cae1c24
     * token_expire_time : 1551847623
     */

    private String username;

    private String password;
    private String realname;
    private String mobile;
    private String nickname;
    private String accessToken;
    private String message;

    private long expiresAt;


    public LoginBean() {
        super();
        // sLoginBean = this;
    }

    public static boolean isLogin() {
		return getInstance() != null && !TextUtils.isEmpty(sLoginBean.getToken());
//        return getInstance() != null && getInstance().getUser() != null;
//        return false;
    }

    public static LoginBean getInstance() {
        if (sLoginBean == null)
            sLoginBean = getFromCache();
        return sLoginBean;
    }

    public static LoginBean getFromCache() {
        LoginBean data;
        data = (LoginBean) FileUtils.readObject(FILE_CACHE);
        if (data != null) {
            sLoginBean = data;
        }
//        LogUtil.i("login---->getFromCache---" + (data == null));
        return data;
    }

    public void save() {
        sLoginBean = this;
        FileUtils.cacheObject(FILE_CACHE, this);
        // FileSaveHandler.saveObject(FILE_CACHE, this);
//        LogUtil.i("login---->save---" + FILE_CACHE);
    }

    private static void delete() {
        FileUtils.cacheObject(FILE_CACHE, null);
        // FileSaveHandler.removeObject(FILE_CACHE);
    }

	public static void logout() {
		delete();
		sLoginBean = null;

        TagAliasOperatorHelper.getInstance().cleanAlias();
	}

    public static void logout(Context mContext) {
        delete();
        sLoginBean = null;
        MySharedPreferences mSp = new MySharedPreferences(mContext);
        mSp.putIsLogined(false);
        mSp.putPassword("");
        mSp.putUser("");
        TagAliasOperatorHelper.getInstance().cleanAlias();
    }

    public static void logPast(Context mContext) {
        delete();
        sLoginBean = null;
        MySharedPreferences mSp = new MySharedPreferences(mContext);
        mSp.putIsLogined(false);
    }

    public boolean isLoginPast(){
        if(expiresAt == 0)return true;
        return  new Date().getTime() > expiresAt;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserId() {
        if (!isLogin()) return "";
        return getInstance().getUsername();
//        return getInstance().getUser().getUserId();
    }

//    public   String getJpushAlias(){
//        return "ukluser_"+getUserId();
//    }

    public static String getUserToken() {
        if (!isLogin()) return "";
        return getInstance().getToken();
//        return getInstance().getUser().getUserId();
    }


    public String getJpushAlias() {
        return "";
    }

    public void setJpushAlias(String jpushAlias) {
//        this.jpushAlias = jpushAlias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return accessToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
