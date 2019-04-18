package com.ffapp.baseapp.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisActivity;
import com.ffapp.baseapp.bean.BasisBean;
import com.ffapp.baseapp.bean.LoginBean;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;

public class RegistActivity extends BasisActivity {


    @BindView(R.id.eidt_user)
    EditText edit_acount;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;
    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;

    @BindView(R.id.text_msgcode)
    TextView text_msgcode;

    CountDownTimerUtils countDownTimerUtils;
    KProgressHUD mProgressDialog;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.login_regist_activity);
        setTitle(R.string.user_regist);
        setTitleLeftButton(null);
        setTitleBg(R.color.transparent);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        check_rule.setSelected( isRuleCheck);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimerUtils != null) countDownTimerUtils.cancel();
    }

    private final static int HTTP_REGIST = 11;
    private final static int HTTP_SEND_MSGCODE = 12;
    private final static int HTTP_VERTIFY_MSGCODE = 13;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_REGIST:
//                    loginSucceed((LoginBean) result);
                    dismissProgress();
//                    DialogUtils.DialogOKmsgFinish(mContext, ((BasicBeanHC) result).getStatusInfo());
                    setResult(Activity.RESULT_OK);
//                    DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanHC) result).getStatusInfo())
                    break;
                case HTTP_SEND_MSGCODE:
                    showToast(((BasisBean) result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode, 60000, 1000);
                    countDownTimerUtils.start();
                    dismissProgress();
                    break;
                case HTTP_VERTIFY_MSGCODE:
                    postRegist();
                    break;
            }
        }

        @Override
        public boolean onHttpFailure(int httpWhat, Throwable arg3) {
            dismissProgress();
            return super.onHttpFailure(httpWhat, arg3);
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
            dismissProgress();
        }

        @Override
        public void onFinish(int httpWhat) {
//            dismissProgress();
//            mProgressDialog.dismiss();
        }
    };

    String account, pwd;

    @OnClick(R.id.btn_ok)
    void checkAndPost() {
        account = edit_acount.getText().toString().trim();
        pwd = edit_pwd.getText().toString().trim();
        String pwd_confirm = edit_pwd_confirm.getText().toString().trim();
        String msgcode = edit_msgcode.getText().toString().trim();

        if (CheckUtils.isStrEmpty(account)) {
            showToast("请输入用户名");
            return;
        }
        if (CheckUtils.isStrEmpty(pwd)) {
            showToast("请输入登录密码");
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
        if (CheckUtils.isStrEmpty(msgcode)) {
            showToast("请输入短信验证码");
            return;
        }

        if(!isRuleCheck){
            showToast("请同意《海沧区公共服务平台用户注册协议》");
            return;
        }

        vertifyMsgcode(msgcode);
//        postRegist();
    }

    @OnClick(R.id.text_msgcode)
    void sendCode() {
        String mobile = edit_acount.getText().toString().trim();
//        if (!CheckUtils.isMobileNO(mobile)) {
//            showToast("请输入正确的手机号码");
//            return;
//        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
//        HttpRestClient.post(Constants.URL_MSGCODE_SEND, params, myHttpListener,
//                HTTP_SEND_MSGCODE, BasicBeanHC.class);
    }

    void vertifyMsgcode(String msgCode) {
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("mobile", mobile);
        params.put("code", msgCode);
//        HttpRestClient.post(Constants.URL_MSGCODE_VERTIFY, params, myHttpListener,
//                HTTP_VERTIFY_MSGCODE, BasicBeanHC.class);

    }

    void postRegist() {

        RequestParams params = new RequestParams();
        showProgress();
//        mProgressDialog.show();
        params.put("AccountName", account);
        params.put("AccountPwd", pwd);
//        HttpRestClient.get(Constants.URL_REGIST_BYPERSON, params, myHttpListener,
//                HTTP_REGIST, BasicBeanHC.class);
    }

    private void loginSucceed(LoginBean bean) {
//        mSp.putString("personalUser",mEdtUser.getText().toString().trim());
//        mSp.putString("personalPass",mEdtPass.getText().toString().trim());
//        mSp.putString("personalData",bean.getData().getData());
        bean.save();
//        mSp.putIsLogined(true);
        finish();
    }
    @OnClick(R.id.text_regist_rule)
    void onBtnRegistRule(){
//        Intent intent = new Intent(mContext,RegistRuleActivity.class);
//        startActivity(intent);
    }

    @BindView(R.id.check_rule)
    ImageView check_rule;

    boolean isRuleCheck = true;
    @OnClick(R.id.check_rule)
    void onRuleCheckClick(){
        isRuleCheck = !isRuleCheck;
        check_rule.setSelected( isRuleCheck);
    }

}
