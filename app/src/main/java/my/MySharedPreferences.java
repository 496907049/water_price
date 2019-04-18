package my;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;

public class MySharedPreferences {
    final String SP_NAME = "suncco";
    final String SP_KEY_USER = "user";
    final String SP_KEY_USER_ID = "user_id";
    final String SP_KEY_PASSWORD = "password";
    final String SP_KEY_AUTOUPDATE = "auto_update";
    final String SP_KEY_AUTOLOGIN = "auto_login";
    final String SP_KEY_REMEMBER_PWD = "remember_password";
    final String SP_TOKEN = "token";
    final String SP_SEX = "sex";
    final String SP_INFOSEARCH_HISTORY = "info_search_history";

    final String SP_KEY_SESSION = "user_session";
    final String SP_KEY_CUSTOMNO = "user_custom_no";

    final String SP_LOCUS_ISLOCUS = "locus_is_locus";// 是否需要手势密码
    final String SP_LOCUS_PASSWORD = "locus_password";// 手势密码
    final String SP_LOCUS_MODIFYTIME = "locus_modifytime";// 手势密码修改时间
    final String SP_LOCUS_PASTTIME = "locus_pasttime";// 手势密码有效时间

    final String SP_IS_LOGINED = "user_islogined";

    final String SP_VERSION = "save_version";

    final String CHECK_UPDATE = "check_update";
    final String SERVICE_IP = "service_ip";
    final String SP_LANGUAGE = "app_language";


    private SharedPreferences mSharedPreferences;

    public MySharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public String getString(String key, String defaut) {
        return mSharedPreferences.getString(key, defaut);
    }

    public void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public void putLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaut) {
        return mSharedPreferences.getBoolean(key, defaut);
    }

    public boolean putBoolean(String key, boolean value) {
        return mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public String getUser() {
        return getString(SP_KEY_USER);
    }

    public String getPassword() {
        return getString(SP_KEY_PASSWORD);
    }

    public void putUser(String user) {
        putString(SP_KEY_USER, user);
    }

    public void putPassword(String password) {
        putString(SP_KEY_PASSWORD, password);
    }

    public String getUserID() {
        return getString(SP_KEY_USER_ID);
    }

    public void putUserID(String user_id) {
        putString(SP_KEY_USER_ID, user_id);
    }

    public void clearUserPassword() {
        // putUser(null);
        putPassword(null);
    }

    public boolean getAutoUpdate() {
        return true;
        // return mSharedPreferences.getBoolean(SP_KEY_AUTOUPDATE, true);
    }

    public void putAutoUpdate(boolean autoupdate) {
        putBoolean(SP_KEY_AUTOUPDATE, autoupdate);
    }

    public boolean getAutoLogin() {
        return mSharedPreferences.getBoolean(SP_KEY_AUTOLOGIN, true);
    }

    public void putAutoLogin(boolean autologin) {
        putBoolean(SP_KEY_AUTOLOGIN, autologin);
    }

    public boolean getRememberPwd() {
        return mSharedPreferences.getBoolean(SP_KEY_REMEMBER_PWD, true);
    }

    public void putRememberPwd(boolean isRemember) {
        putBoolean(SP_KEY_REMEMBER_PWD, isRemember);
    }

    public boolean getRememberUser() {
        return mSharedPreferences.getBoolean("remember_user", true);
    }

    public void putRememberUser(boolean isRemember) {
        putBoolean("remember_user", isRemember);
    }

    public ArrayList<String> getInfoSearchHistory() {
        ArrayList<String> list = new ArrayList<String>();
        String history = getString(SP_INFOSEARCH_HISTORY);
        if (TextUtils.isEmpty(history))
            return list;
        String[] histrys = history.split(",");
        for (int i = 0, l = histrys.length; i < l; i++) {
            list.add(histrys[i]);
        }
        list.add("清除历史记录");
        return list;
    }

    public void saveInfoSearchHistory(String keyWords) {
        String longhistory = getString(SP_INFOSEARCH_HISTORY);
        if (TextUtils.isEmpty(longhistory)) {
            longhistory = "";
        }
        if (!longhistory.contains(keyWords + ",")) {
            StringBuilder sb = new StringBuilder(longhistory);
            sb.insert(0, keyWords + ",");
            putString(SP_INFOSEARCH_HISTORY, sb.toString());
        }
    }

    public void clearInfoSearchHistory() {
        putString(SP_INFOSEARCH_HISTORY, null);
    }

    public boolean getIsLocus() {
        // return mSharedPreferences.getBoolean(SP_LOCUS_ISLOCUS, true);
        return true;
    }

    public void putIsLocus(boolean isLocus) {
        putBoolean(SP_LOCUS_ISLOCUS, isLocus);
    }

    public String getSaveVersion() {
        return getString(SP_VERSION);
    }

    public void putSaveVersion(String version) {
        putString(SP_VERSION, version);
    }

    public boolean isFirst(Context context) {
        String version = getSaveVersion();
        if (TextUtils.isEmpty(version))
            return true;
        if (version.equals(SystemParamsUtils.getAPPVersonCode(context) + "")) {
            return false;
        }
        return true;
    }

    public boolean setFirstDone(Context context) {
        String version = SystemParamsUtils.getAPPVersonCode(context) + "";
        putSaveVersion(version);
        if (TextUtils.isEmpty(version))
            return false;
        return true;
    }

    public boolean isLogined() {
        return getBoolean(SP_IS_LOGINED, false);
    }

    public boolean putIsLogined(boolean islogined) {
        putBoolean(SP_IS_LOGINED, islogined);
        return islogined;
    }

    public boolean putCheckUpdated(boolean b) {
        putBoolean(CHECK_UPDATE, b);
        return b;
    }

    /***
     * 首页是否检查过更新，检查过后不再检查
     *
     * @return
     */
    public boolean isCheckUpdated() {
        return getBoolean(CHECK_UPDATE, false);
    }

    public int getAppLanguage() {
        return getInt(SP_LANGUAGE, 0);
    }

    public void setAppLanguage(int language) {
        putInt(SP_LANGUAGE, language);
    }


}
