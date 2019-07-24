package com.ffapp.waterprice.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BasicBeanStr;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.bean.UpdateBean;
import com.ffapp.waterprice.home.HomeBaseActivity;
import com.ffapp.waterprice.login.LoginActivity;
import com.ffapp.waterprice.user.alarm.AlarmActivity;
import com.ffapp.waterprice.util.glide.GlideCatchUtil;
import com.flyco.dialog.listener.OnBtnClickL;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.DialogUtils;
import my.MD5;
import my.MySharedPreferences;
import my.SystemParamsUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;


public class UserIndexActivity extends HomeBaseActivity {

    @BindView(R.id.text_version)
    TextView text_version;
    @BindView(R.id.text_name)
    TextView text_name;
    @BindView(R.id.img_update_point)
    ImageView img_update_point;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_index_activity);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLoginBean();
//            }
//        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        setUserView();
//
//        text_version.setText("v"+ SystemParamsUtils.getAppVersonName(mContext));
//
//        text_name.setText(""+LoginBean.getInstance().getRealname());
//
//        checkUpdata();
    }


//    void getUserInfo() {
//        RequestParams params = new RequestParams();
////        showProgress();
//        params.put("token", LoginBean.getUserToken());
//        HttpRestClient.post(Constants.URL_USER_INFO, params, new MyHttpListener() {
//                    @Override
//                    public void onSuccess(int httpWhat, Object result) {
//                        LoginBean bean = (LoginBean) result;
//                        LoginBean loginBean = LoginBean.getInstance();
//                        loginBean.copyDataFromUserBean(bean);
//                        setUserView();
//                    }
//
//                    @Override
//                    public void onFailure(int httpWhat, Object result) {
////                        super.onFailure(httpWhat, result);
//                    }
//
//                    @Override
//                    public void onFinish(int httpWhat) {
//                        hideLoading();
//                    }
//                },
//                0, LoginBean.class);
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        getImmersionBar().statusBarDarkFont(true,0.5f);
        getImmersionBar().statusBarDarkFont(true).init();
    }

    private void setUserView() {
//        if (LoginBean.isLogin()) {
//            text_nologin.setVisibility(View.GONE);
//            view_login.setVisibility(View.VISIBLE);
////            UserBean userBean = IXMSDK.getLoginInfoBean();
////            text_name.setText(userBean.getUserName());
////            text_authlevel.setText(userBean.getAuthLevelStr());
//            LoginBean loginBean = LoginBean.getInstance();
//            text_name.setText(loginBean.getNick_name());
//            text_des.setText(loginBean.getMobile());
//            BasisApp.loadImgAsbitmap(mContext, loginBean.getHeadPic(), img_photo, R.drawable.user_icon_default);
////            Glide.with(mContext).load(loginBean.getHeadPic()).asBitmap().placeholder(R.drawable.user_icon_default).into(img_photo);
//        } else {
//            text_nologin.setVisibility(View.VISIBLE);
//            view_login.setVisibility(View.GONE);
//            img_photo.setImageResource(R.drawable.user_icon_default);
//        }
    }


    @OnClick(R.id.view_logout)
    void logout(){
        DialogUtils.DialogTwo(mContext, "", "退出当前账号？", "立即退出", "取消", new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                LoginActivity.toLoginAllClear(mContext);
//                postLogout();
            }
        },null);
    }

    @OnClick(R.id.view_alarm)
    void toAlarm(){
       ActivityTool.skipActivity(mContext,AlarmActivity.class);
    }

    void postLogout() {
        RequestParams params = new RequestParams();
//        params.put("ticket", LoginBean.getInstance().getTicket().getValue());
        params.put("token", LoginBean.getUserToken());
        showLoadingDialog();
        HttpRestClient.post(Constants.aaa, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {

//                        MySharedPreferences mSp = new MySharedPreferences(mContext);
//                        mSp.putIsLogined(false);
//                        mSp.putPassword("");
//                        mSp.putUser("");

                        LoginActivity.toLoginAllClear(mContext);
                    }

                    @Override
                    public void onFailure(int httpWhat, Object result) {
//                        super.onFailure(httpWhat, result);
                        LoginActivity.toLoginAllClear(mContext);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                    }
                }, 0,
                BasicBeanStr.class);
    }

    void refreshLoginBean() {
        MySharedPreferences mSp = new MySharedPreferences(this);
        RequestParams params = new RequestParams();
        params.put("username",  mSp.getUser());
        params.put("password", MD5.getMD5ofStrLowercase(mSp.getPassword()));
        HttpRestClient.post(Constants.URL_LOGIN, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                switch (httpWhat) {
                    case HTTP_LOGIN:
                        LoginBean loginBean = (LoginBean) result;
                        loginBean.save();
                        text_version.setText("v"+ SystemParamsUtils.getAppVersonName(mContext));
                        text_name.setText(""+LoginBean.getInstance().getRealname());
                        refresh.setRefreshing(false);
                        break;
//                case HTTP_SMS:
////                    showToast("短信验证码获取成功");
//                    showToast(((BasisBean) result).getResultData());
//                    new CountDownTimerUtils(btn_msgcode).start();
//                    break;
                }
            }

            @Override
            public void onFinish(int httpWhat) {

            }
        }, HTTP_LOGIN, LoginBean.class);
    }
    static final int HTTP_LOGIN = 12;


    @OnClick(R.id.view_modifypwd)
    void modifypwd(){
        ActivityTool.skipActivity(mContext, ModifyPwdActivity.class);
    }

    @OnClick(R.id.view_update)
    void update(){
        UpdateBean.check(mContext,true);
        img_update_point.setVisibility(View.GONE);
    }


    public  void checkUpdata() {

        RequestParams params = new RequestParams();
        params.put("versions_number", SystemParamsUtils.getAPPVersonCode(mContext));
        HttpRestClient.get(Constants.aaa, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                UpdateBean updateBean = (UpdateBean) result;
               if(updateBean.isNeedUpdate(mContext)){
                   img_update_point.setVisibility(View.VISIBLE);
               }else{
                   img_update_point.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(int httpWhat, Object result) {
//                super.onFailure(httpWhat, result);
                img_update_point.setVisibility(View.GONE);
            }

            @Override
            public void onFinish(int httpWhat) {

            }

        }, 0, UpdateBean.class);

    }


    @OnClick(R.id.view_clearcache)
    void clear() {
        showLoading();
        Glide.get(mContext).clearMemory();
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                GlideCatchUtil.getInstance().cleanCacheDisk();
                GlideCatchUtil.getInstance().clearCacheDiskSelf();
                GlideCatchUtil.getInstance().clearCacheMemory();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hideLoading();
//                showToast("清除缓存成功");
                DialogUtils.DialogOkMsg(mContext,"清除缓存成功");
//                setCacheView();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute("");
    }


}
