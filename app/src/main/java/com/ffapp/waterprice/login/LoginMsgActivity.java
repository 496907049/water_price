package com.ffapp.waterprice.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BasicBeanStr;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.jpush.TagAliasOperatorHelper;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.SystemParamsUtils;
import my.ViewUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;

public class LoginMsgActivity extends BasisActivity {


    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;

    @BindView(R.id.text_msgcode)
    TextView text_msgcode;

    CountDownTimerUtils countDownTimerUtils;
    String mobile,country_code;

    @Override
    public void initViews() {
        super.initViews();
        setDefautTrans(false);
        setContentView(R.layout.login_msg_activity);
//        setStatusBarTransparent(true);
        View titleview = findViewById(R.id.base_title_view);
        View titleviewRoot = findViewById(R.id.base_title_root);
        titleview.setBackgroundResource(R.color.transparent);
        titleviewRoot.setBackgroundResource(R.color.transparent);

        setTitle("");
        setTitleLeftButton(null);
        edit_msgcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    findViewById(R.id.btn_ok).performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mobile = getIntent().getStringExtra("mobile");
        country_code = getIntent().getStringExtra("country");

        if(TextUtils.isEmpty(mobile)){
            finish();
            return;
        }
        text_msgcode.performClick();
    }


    public static final int REQUEST_REGISTER = 11;
    private final int REQUEST_FOGET = 13;

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
            default:
                break;
        }
    }





    @OnClick(R.id.text_msgcode)
    void sendCode() {
//        String mobile = "";
////                edit_acount.getText().toString().trim();
//        if (!CheckUtils.isMobileNO(mobile)) {
//            showToast("请输入正确的手机号码");
//            return;
//        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
        params.put("country_code", country_code);
        HttpRestClient.post(Constants.URL_LOGIN_MSGCODE, params, myHttpListener,
                HTTP_SEND_MSGCODE, BasicBeanStr.class);
    }

    @OnClick(R.id.btn_ok)
    void checkpost() {

        String msgcode = edit_msgcode.getText().toString().trim();
        if (TextUtils.isEmpty(msgcode)) {
            showToast(R.string.user_code_empty);
            return;
        }


//        password = edit_pwd.getText().toString().trim();
//        if (TextUtils.isEmpty(password)) {
//            showToast(R.string.user_password_empty);
//            return;
//        }

        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("code", msgcode);
        params.put("country_code", country_code);
        params.put("equipment", SystemParamsUtils.getIMEI());
            HttpRestClient.post(Constants.URL_LOGIN, params, myHttpListener, HTTP_LOGIN, LoginBean.class);

    }

    static final int HTTP_LOGIN = 12;
    static final int HTTP_SEND_MSGCODE = 13;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_LOGIN:
                    LoginBean loginBean = (LoginBean) result;
                    onLoginSuccess(loginBean);
                    break;
                case HTTP_SEND_MSGCODE:
//                    showToast("短信验证码获取成功");
                    showToast(((BasisBean) result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode, 60000, 1000);
                    countDownTimerUtils.start();
                    break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimerUtils !=null)countDownTimerUtils.cancel();
    }

    protected void onLoginSuccess(LoginBean loginBean) {
//        showToast("登录成功");
        ViewUtils.hideInput(mContext,edit_msgcode);
        showToast(loginBean.getStatusInfo());
        setResult(Activity.RESULT_OK);
        loginBean.save();
        TagAliasOperatorHelper.getInstance().setAlias(LoginBean.getInstance().getJpushAlias());
        finish();
    }


}
