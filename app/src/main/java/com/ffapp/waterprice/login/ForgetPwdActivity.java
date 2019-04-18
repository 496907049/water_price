package com.ffapp.waterprice.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.LoginBean;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;
import my.DialogUtils;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;


public class ForgetPwdActivity extends BasisActivity {

    @BindView(R.id.edit_pwd)
    EditText edit_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;
    @BindView(R.id.eidt_user)
    EditText edit_acount;
    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;
    @BindView(R.id.text_msgcode)
    TextView text_msgcode;

    CountDownTimerUtils countDownTimerUtils;


    public static void toForgetPwdActivity(Activity context, int logintype) {
        Intent intent = new Intent(context,ForgetPwdActivity.class);
        intent.putExtra("logintype",logintype);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.login_forgetpwd_activity);
        setTitleLeftButton(null);
        setTitle(R.string.user_forget_password);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimerUtils !=null)countDownTimerUtils.cancel();
    }
    private final static int HTTP_MODIFY = 11;
    private final static int HTTP_SEND_MSGCODE = 12;
    private final static int HTTP_VERTIFY_MSGCODE = 13;
    private final static int HTTP_GET_USERINFO = 14;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_MODIFY:
//                    loginSucceed((LoginBean) result);
                    setResult(Activity.RESULT_OK);
                    DialogUtils.DialogOKmsgFinish(mContext,"");
                    break;
                case HTTP_SEND_MSGCODE:
//                    showToast(((BasicBeanHC)result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode,60000,1000);
                    countDownTimerUtils.start();
                    break;
                case HTTP_VERTIFY_MSGCODE:
                    post();
                    break;
                case HTTP_GET_USERINFO:
                    LoginBean beanLogin = (LoginBean) result;
                    if (beanLogin == null) {
                        return;
                    }
//                    oldpwd = beanLogin.getAccountPwd();
//                    if(logintype == LoginBean.TYPE_USER){
//                        mobile = beanLogin.getMobile();
//                    }else{
//                        mobile = beanLogin.getTransactorMobile();
//                    }

                    sendCodeReal(mobile);
                    break;
            }
        }

        @Override
        public void onFinish(int httpWhat) {
            dismissProgress();
        }
    };

    String account, pwd,oldpwd,mobile;

    @OnClick(R.id.btn_ok)
    void checkAndPost() {
        account = edit_acount.getText().toString().trim();
        pwd = edit_pwd.getText().toString().trim();
        String pwd_confirm = edit_pwd_confirm.getText().toString().trim();
        String msgcode = edit_msgcode.getText().toString().trim();

        if (CheckUtils.isStrEmpty(account)) {
            showToast("请输入用户帐号");
            return;
        }

        if (CheckUtils.isStrEmpty(msgcode)) {
            showToast("请输入短信验证码");
            return;
        }

        if (CheckUtils.isStrEmpty(pwd)) {
            showToast("请输入新密码");
            return;
        }
        if (CheckUtils.isStrEmpty(pwd_confirm)) {
            showToast("请输入确认密码");
            return;
        }
        if (!pwd.equals(pwd_confirm)) {
            showToast("两次输入密码不一致");
            return;
        }

        vertifyMsgcode(msgcode);
//        post();
    }

    @OnClick(R.id.text_msgcode)
    void sendCode() {
        account = edit_acount.getText().toString().trim();
        if (CheckUtils.isStrEmpty(account)) {
            showToast("请输入用户帐号");
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("name",account);
        params.put("selkind","2");

//        HttpRestClient.get(Constants.URL_USER_INFOBYUSER, params, myHttpListener,
//                HTTP_GET_USERINFO, LoginBean.class);
    }

    void sendCodeReal(String mobile) {
        if(TextUtils.isEmpty(mobile)){
            showToast("该用户未绑定手机号码");
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
//        HttpRestClient.post(Constants.URL_MSGCODE_SEND, params, myHttpListener,
//                HTTP_SEND_MSGCODE, BasicBeanHC.class);
    }

    void vertifyMsgcode(String msgCode) {
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
        params.put("code", msgCode);
//        HttpRestClient.post(Constants.URL_MSGCODE_VERTIFY, params, myHttpListener,
//                HTTP_VERTIFY_MSGCODE, BasicBeanHC.class);

    }

    void post() {

        RequestParams params = new RequestParams();
        showProgress();
        params.put("name",account);
        params.put("password", oldpwd);
        params.put("newpassword", pwd);
        params.put("yzm","123");
//        HttpRestClient.post(Constants.URL_LOGIN_MODIFY_PWD, params, myHttpListener,
//                HTTP_MODIFY, LoginFirst.class);
    }
}
