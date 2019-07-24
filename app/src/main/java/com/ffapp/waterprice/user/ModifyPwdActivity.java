package com.ffapp.waterprice.user;

import android.os.Bundle;
import android.widget.EditText;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.MD5;
import my.MySharedPreferences;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.http.OkGoClient;

import static com.ffapp.waterprice.user.UserIndexActivity.HTTP_LOGIN;

public class ModifyPwdActivity extends BasisActivity {


    @BindView(R.id.edit_new_pwd)
    EditText edit_new_pwd;
    @BindView(R.id.edit_old_pwd)
    EditText edit_old_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_modify_pwd_activity);

        setTitleLeftButton(null);
        setTitle(R.string.user_modify_password);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.btn_ok)
    void post() {
//        String userName = LoginBean.getInstance().getUser().getUserName();
        final String pass = edit_old_pwd.getText().toString().trim();
        String passNew = edit_new_pwd.getText().toString().trim();
        String passAgain = edit_pwd_confirm.getText().toString().trim();

        // if (userName.length() == 0) {
        // showToast("请输入用户名");
        // return;
        // }

        if (pass.length() == 0) {
            showToast("请输入旧密码");
            return;
        }

        if (passNew.length() == 0) {
            showToast("请输入新密码");
            return;
        }

        if (passAgain.length() == 0) {
            showToast("请再次输入新密码");
            return;
        }

        if (!passNew.equals(passAgain)) {
            showToast("新密码不一致，请重新输入新密码");
            edit_new_pwd.setText("");
            edit_pwd_confirm.setText("");
            return;
        }

        HttpParams params = new HttpParams();
        params.put("newPassword", passNew);
        params.put("oldPassword", pass);
        showProgress();
        OkGoClient.post(mContext, Constants.URL_RESET, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                if(new MySharedPreferences(mContext).getRememberPwd()){
                    new MySharedPreferences(mContext).putPassword(pass);
                }
                DialogUtils.DialogOKmsgFinish(mContext,((BasisBean)result).getStatusInfo());
            }

            @Override
            public void onFailure(int httpWhat, Object result) {
                super.onFailure(httpWhat, result);
            }

            @Override
            public void onFinish(int httpWhat) {
                hideLoading();
            }
        }, 22, BasisBean.class);

//        RequestParams params = new RequestParams();
//        params.put("token", LoginBean.getUserToken());
//        params.put("old_password", MD5.getMD5ofStrLowercase(pass));
//        params.put("new_password", MD5.getMD5ofStrLowercase(passNew));
//        showLoading();
//        HttpRestClient.post(Constants.aaa, params, new MyHttpListener() {
//            @Override
//            public void onSuccess(int httpWhat, Object result) {
////                showToast(((BasisBean)result).getResultData());
////                finish();
//                if(new MySharedPreferences(mContext).getRememberPwd()){
//                    new MySharedPreferences(mContext).putPassword(pass);
//                }
//                DialogUtils.DialogOKmsgFinish(mContext,((BasisBean)result).getStatusInfo());
//            }
//
//            @Override
//            public void onFinish(int httpWhat) {
//                hideLoading();
//            }
//        }, 0, BasisBean.class);

    }


}
