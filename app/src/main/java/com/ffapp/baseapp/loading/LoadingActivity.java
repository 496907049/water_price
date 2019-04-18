package com.ffapp.baseapp.loading;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;

import butterknife.BindView;
import my.LogUtil;
import my.MySharedPreferences;
import my.http.MyHttpListener;
import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisApp;
import com.ffapp.baseapp.basis.BasisOtherActivity;
import com.ffapp.baseapp.bean.BasisBean;
import com.ffapp.baseapp.bean.LoginBean;
import com.ffapp.baseapp.home.HomeTabActivity;
import com.ffapp.baseapp.jpush.TagAliasOperatorHelper;
import com.ffapp.baseapp.login.LoginActivity;
import com.ffapp.baseapp.util.MyUtils;

/**
 * 启动loading
 *
 *
 */
public class    LoadingActivity extends BasisOtherActivity {

    private final static int HTTP_SESSIONCHECK = 11;

    private final int LOADING_PAUSE_TIME = 3000;

    boolean isFromPush = false;
    boolean is_pause_done = false;

    MySharedPreferences mSp;
    BasisBean mSessionCheckBean;

    @BindView(R.id.img_bg)
    ImageView img_bg;

    public static void toLoadingAllClear(Activity mContext) {
        new MySharedPreferences(mContext).putIsLogined(false);
        Intent intent = new Intent(mContext, LoadingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        mContext.finish();
    }

    @Override
    public void initConfig(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        overridePendingTransition(R.anim.loading_fade_enter, R.anim.fade_exit);
        super.initConfig(savedInstanceState);

    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_loading);
        super.initViews();
        getImmersionBar().transparentStatusBar().init();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            LogUtil.i("loading--->启动参数异常");
            finish();

            return;

        }
        super.initData(savedInstanceState);
//        Glide.with(mContext).load(R.drawable.loading_bg)
//                .placeholder(R.color.title_start)
//                .into(img_bg);44
        BasisApp.isProgramExit = false;
        initPush();

        mSp = new MySharedPreferences(mContext);
//		autoLogin();
        mHandler.sendEmptyMessageDelayed(HANDLER_LOADING_DONE, LOADING_PAUSE_TIME);
//		new BaiduLocUtil(mContext).starLocation();
    }

    private void getLoading() {
    }

    private void initPush() {
        // MySharedPreferences mSp = new MySharedPreferences(this);
        // mSp.putBoolean(PushUtils.SP_IS_WORKED, false);
        // Intent intent = new Intent(PushBroadcastReceiver.ACTION_PUSH_RESET);
        // sendBroadcast(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(HANDLER_LOADING_DONE);
        }
    }

    private void netExceptionDialog() {
        if (this.isFinishing()) {
            return;
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    getLoading();
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    finish();
                }
            }
        };
//        new CustomDialog.Builder(this).setTitle(R.string.app_warn)
//                .setMessage(R.string.app_net_exc)
//                .setPositiveButton(R.string.app_retry, listener)
//                .setNegativeButton(R.string.app_exit, listener).create().show();
    }


    private final static int HANDLER_LOADING_DONE = 11;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_LOADING_DONE:
                    GoHome();
                    break;

                default:
                    break;
            }
        }

    };

    private void GoHome() {
        if(TextUtils.isEmpty(MyUtils.getIp())){
            LoginBean.logout();
        }
        if(LoginBean.isLogin()){
            Intent intent = new Intent(mContext, HomeTabActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

                    TagAliasOperatorHelper.getInstance().setAlias(LoginBean.getInstance().getJpushAlias());
                LoadingActivity.this.finish();

        }else{
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

            LoadingActivity.this.finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void autoLogin() {
//		 String customNo = mSp.getCustomNo();
//		 String sessionId = mSp.getSessionId();
//		 if(TextUtils.isEmpty(customNo) || TextUtils.isEmpty(sessionId)){
//			 return;
//		 }
//		JsonHttpParams params = new JsonHttpParams();
//		params.put("customerNo", customNo);
//		params.put("sessionId", sessionId);
//
//		httpPost(LoginBean.URL_REQUERY, params, LoginBean.class, HTTP_SESSIONCHECK);
    }

    MyHttpListener myHttpListener = new MyHttpListener() {

        @Override
        public void onSuccess(int httpWhat, Object result) {
            // TODO Auto-generated method stub
            switch (httpWhat) {
                case HTTP_SESSIONCHECK:
                    if (is_pause_done) {
                        GoHome();
                    } else {
                        mSessionCheckBean = (BasisBean) result;
                    }
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            // TODO Auto-generated method stub
            if (httpWhat == HTTP_SESSIONCHECK) {
                if (is_pause_done) {
                    GoHome();
                } else {
                    mSessionCheckBean = (BasisBean) result;
                }
            }
            super.onFailure(httpWhat, result);
        }


        @Override
        public boolean onHttpFailure(int httpWhat, Throwable arg3) {
            // TODO Auto-generated method stub
            mSessionCheckBean = new BasisBean();
            if (is_pause_done) {

                mHandler.sendEmptyMessage(HANDLER_LOADING_DONE);
            }
            return super.onHttpFailure(httpWhat, arg3);
        }

        @Override
        public void onFinish(int httpWhat) {
            // TODO Auto-generated method stub

        }
    };
}