package com.ffapp.waterprice.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.bean.UserBean;
import com.ffapp.waterprice.home.HomeTabActivity;
import com.ffapp.waterprice.jpush.TagAliasOperatorHelper;
import com.ffapp.waterprice.util.MyUtils;
import com.ffapp.waterprice.util.StringListChooseActivity;
import com.flyco.dialog.listener.OnBtnClickL;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.DialogUtils;
import my.MySharedPreferences;
import my.http.MyHttpListener;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BasisActivity {


    @BindView(R.id.eidt_user)
    EditText edit_user;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;

    BaseListDataListBean listServers = new BaseListDataListBean();
    @BindView(R.id.text_servername)
    TextView text_servername;


    public static void ToLogin(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);

    }

    public static boolean toLoginIfNotLogin(Activity context, int requestCode) {
        if (!LoginBean.isLogin()) {
            ToLogin(context, requestCode);
            return false;
        }
        return true;

    }

    public static void toLoginAllClear(Context mContext) {
        LoginBean.logout();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//        mContext.finish();
    }

    public static void toLoadingAllClearNologout(Context mContext) {
        Intent intent = new Intent(mContext, HomeTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//        mContext.finish();
    }

    public static boolean toLoginIfNotLoginDialog(final Activity context, final int requestCode) {
        if (!LoginBean.isLogin()) {
            DialogUtils.DialogTwo(context, "", "您未登录，是否立即登录？", "立即登录", "取消", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    ToLogin(context, requestCode);
                }
            }, null);

            return false;
        }
        return true;

    }

    public static void ToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    @Override
    public void initViews() {
        super.initViews();
        setDefautTrans(false);
        setContentView(R.layout.login_activity);
        setTitleBg(R.drawable.base_transparent);
        setTitle("");
        edit_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    findViewById(R.id.btn_login).performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        LoginBean.logout();

        setCheckView();
        listServers = new BaseListDataListBean();
        BaseListData data;

        data = new BaseListData("http://192.168.25.245:8081/", "测试环境");
        data.setTenant("app");
        data.setAccessKey("45bd5cc0c8694cdc92c43a6edc094089");
        listServers.getList().add(data);

        BaseListData dataCurrent = listServers.getDataById(MyUtils.getIp());
        if (dataCurrent != null) {
            text_servername.setText(dataCurrent.getName());
            MyUtils.putSerciceData(dataCurrent);
        } else {
            dataCurrent = listServers.getList().get(0);
            MyUtils.putSerciceData(data);
            text_servername.setText(dataCurrent.getName());
        }

        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.INTERNET
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_WIFI_STATE
                                , Manifest.permission.READ_PHONE_STATE
                        ).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        BasisApp.showToast(permissions.toString() + "权限拒绝");
                    }
                });
        MySharedPreferences msp = new MySharedPreferences(mContext);
        if (msp.getRememberUser()) {
            edit_user.setText(msp.getUser());
        }
        if (msp.getRememberPwd()) {
            edit_pwd.setText(msp.getPassword());
        }

        if (TextUtils.isEmpty(msp.getUser())) {
            edit_user.setText("root");
            edit_pwd.setText("123456");
        }

//        edit_user.setText("admin");
//        edit_pwd.setText("111111");

    }


    public static final int REQUEST_REGISTER = 11;
    private final int REQUEST_FOGET = 13;
    private final int REQUEST_SERVER_CHOOSE = 14;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_FOGET:
                if (resultCode == Activity.RESULT_OK) {
//                    userEdit.setText(data.getStringExtra("name"));
//                    pwdEdit.setText(data.getStringExtra("pass"));
//                    doLogin();
                }
                break;
            case REQUEST_SERVER_CHOOSE:
                if (resultCode == Activity.RESULT_OK) {
                    int position = data.getIntExtra("position", -1);
                    if (position > -1) {
                        BaseListData dateSeleted = listServers.getList().get(position);
                        MyUtils.putSerciceData(dateSeleted);
                        text_servername.setText(listServers.getList().get(position).getName());
//                        if (position == 0) {
//
//                            edit_user.setText("ffuser");
//                            edit_pwd.setText("123456");
//                        } else {
//                            edit_user.setText("xmxf");
//                            edit_pwd.setText("987456");
//                        }
                    }
                }
                break;
            default:
                break;
        }
    }


    int MAX_HIT = 7;
    long[] mHits = new long[MAX_HIT];
    boolean isDeveleper = false;


    @OnClick(R.id.view_server)
    void server_choose() {
        StringListChooseActivity.toStringListChoose(mContext, "请选择运行环境", listServers.getListString(), REQUEST_SERVER_CHOOSE);
    }


    @OnClick(R.id.btn_login)
    void login() {
        doLogin();
    }


    @OnClick(R.id.text_regist)
    void regist() {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivityForResult(intent, REQUEST_REGISTER);
    }

    @OnClick(R.id.text_forgetpwd)
    void fogetpwd() {
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivityForResult(intent, REQUEST_FOGET);
    }


    @BindView(R.id.img_check_usere)
    ImageView img_check_usere;
    @BindView(R.id.img_check_pwd)
    ImageView img_check_pwd;

    @OnClick(R.id.view_remember_user)
    void rememberUser() {
        MySharedPreferences msp = new MySharedPreferences(mContext);
        msp.putRememberUser(!msp.getRememberUser());
        if (!msp.getRememberUser()) {
            msp.putRememberPwd(false);
        }
        setCheckView();
    }

    @OnClick(R.id.view_remember_pwd)
    void rememberPwd() {
        MySharedPreferences msp = new MySharedPreferences(mContext);
        msp.putRememberPwd(!msp.getRememberPwd());
        if (msp.getRememberPwd()) {
            msp.putRememberUser(true);
        }
        setCheckView();

    }

    void setCheckView() {
        MySharedPreferences msp = new MySharedPreferences(mContext);

        img_check_usere.setImageResource(msp.getRememberUser() ? R.drawable.btn_radio_select : R.drawable.btn_radio_default);
        img_check_pwd.setImageResource(msp.getRememberPwd() ? R.drawable.btn_radio_select : R.drawable.btn_radio_default);
    }

    String userName, password;

    void doLogin() {

        userName = edit_user.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.user_user_empty);
            return;
        }

        password = edit_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast(R.string.user_password_empty);
            return;
        }

        LoginBean loginBean = new LoginBean();
        loginBean.setUuid("test");
        loginBean.setUsername("test");
//        loginBean.save();
        onLoginSuccess(loginBean);

//        HttpParams params = new HttpParams();
//        params.put("username", userName);
//        params.put("password", password);
//        showProgress();
//        OkGoClient.post(mContext,Constants.URL_LOGIN, params,myHttpListener, HTTP_LOGIN, UserBean.class);

    }

    static final int HTTP_LOGIN = 12;
    static final int HTTP_SMS = 13;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_LOGIN:
                    UserBean userBean = (UserBean) result;
                    MyUtils.putUserName(userBean.getRealname());
                  getToken();
                    break;
//                case HTTP_SMS:
////                    showToast("短信验证码获取成功");
//                    showToast(((BasisBean) result).getResultData());
//                    new CountDownTimerUtils(btn_msgcode).start();
//                    break;
            }
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
        }

        @Override
        public void onFinish(int httpWhat) {
            dismissProgress();
        }
    };

    protected void onLoginSuccess(LoginBean loginBean) {
        showToast("登录成功");
        setResult(Activity.RESULT_OK);
        loginBean.save();
        MySharedPreferences mSp = new MySharedPreferences(this);
        if (mSp.getRememberUser()) {
            mSp.putUser(userName);
        } else {
            mSp.putUser("");
        }
        if (mSp.getRememberPwd()) {
            mSp.putPassword(password);
        } else {
            mSp.putPassword("");
        }
        ActivityTool.skipActivity(mContext, HomeTabActivity.class);
        TagAliasOperatorHelper.getInstance().setAlias(LoginBean.getInstance().getJpushAlias());
        finish();
    }

    void getToken() {
        BaseListData dataCurrent = listServers.getDataById(MyUtils.getIp());
        dataCurrent.setAccount(userName);

        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{ \"accessKey\": \"45bd5cc0c8694cdc92c43a6edc094089\", \"account\": \"admin\", \"tenant\": \"app\"}");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dataCurrent));

        showProgress();
        OkGoClient.post(mContext,Constants.URL_GET_TOKEN, body, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String a =response.body();
                LoginBean bean = JSON.parseObject(a, LoginBean.class);
                if(bean.getAccessToken() == null){
                    showToast(""+bean.getMessage());
                    return;
                }else {
                    onLoginSuccess(bean);
                }
            }
        }, 0, LoginBean.class);


    }

    private void onResultOk() {
        setResult(Activity.RESULT_OK);
        finish();
    }

}
