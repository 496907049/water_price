package com.ffapp.waterprice.basis;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import my.LogUtil;
import my.SystemParamsUtils;
import my.ViewUtils;
import my.base.BaseActivity;


public class BasisActivity extends BaseActivity {

    private String TAG = BasisActivity.class.getName();

    protected BasisActivity mContext;

    public static boolean isActive = true;

    //	LoadingDialog mLoadingDialog;
    KProgressHUD mLoadingDialog;
    private ImmersionBar mImmersionBar;

    boolean isDefautTrans = true;

    @Override
    public void initViews() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        initDataS();
    }

    public void setDefautTrans(boolean isDefautTrans) {
        this.isDefautTrans = isDefautTrans;
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
        setHideInputZone(R.id.main_view);
        setHideInputZone(R.id.main_view2);

        ViewGroup mView = null;
        if (findViewById(R.id.base_title_view) != null) {
            mView = mContext.findViewById(R.id.base_title_view);
        }

        mImmersionBar = ImmersionBar.with(this)
                .navigationBarColor(R.color.base_blue)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .statusBarColor(R.color.title_start) //状态栏透明度，不写默认0.0f
                .statusBarDarkFont(true)
//		.transparentStatusBar()
//				.fitsSystemWindows(false)
//				.titleBarMarginTop(base_title_relative)
        ;
        mImmersionBar.init();   //所有子类都将继承这些相同的属性

        setStatusBarTransparent(isDefautTrans);
    }

    public ImmersionBar getImmersionBar() {
        return mImmersionBar;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void initConfig(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        super.initConfig(savedInstanceState);
        mContext = this;
        initDataS();

    }

    @Override
    public void finish() {
        ViewUtils.hideInput(mContext);
        // TODO Auto-generated method stub
        super.finish();
        // BasisApp.actityList.remove(this);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
//		HttpRestClient.getAsyncHttpClient().cancelAllRequests(true);
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

    }

    private void initDataS() {
//		mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog = KProgressHUD.create(mContext, KProgressHUD.Style.SPIN_INDETERMINATE).setSize(75, 75).setCancellable(true);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getApplicationContext(), this.getClass().getCanonicalName());
        if (!isActive) {
            // app 从后台唤醒，进入前台
            LogUtil.i(TAG, this.getClass().getName() + "---->从后台唤醒进入前台");
            isActive = true;
//			IntentUtils.sendBrocastAction(mContext, HomeActivity.ACTION_CHANGEINBACKGROUND);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getApplicationContext(), this.getClass().getCanonicalName());
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
//		LogUtil.i(this.getClass().getName() + "------>onStop");
        if (!SystemParamsUtils.isAppOnForeground(mContext)) {
            // app 进入后台
            LogUtil.i(TAG, this.getClass().getName() + "---->进入后台");
            isActive = false;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public void setTitle(String titleStr) {
        TextView title = findViewById(R.id.base_title_text);
        if (title == null) {
            super.setTitle(titleStr);
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(titleStr);
        findViewById(R.id.base_title_logo).setVisibility(View.GONE);
    }

    public void setTitle(int titleResId) {
        String titleStr = getResources().getString(titleResId);
        setTitle(titleStr + "");
    }

    public void setTitleBg(int resId) {
        if (isDefautTrans) {
            if (findViewById(R.id.base_title_root) == null)
                return;
            findViewById(R.id.base_title_root).setBackgroundResource(resId);
        } else {
            if (findViewById(R.id.base_title_view) == null)
                return;
            findViewById(R.id.base_title_view).setBackgroundResource(resId);
        }

    }

    public void setTitleRightButton(int drawableRes,
                                    OnClickListener onClickListener) {
        if (findViewById(R.id.base_btn_right) == null)
            return;
        findViewById(R.id.base_btn_right).setOnClickListener(onClickListener);
        ((ImageView) findViewById(R.id.base_btn_right_icon))
                .setImageResource(drawableRes);
        findViewById(R.id.base_btn_right).setVisibility(
                drawableRes == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    public void setTitleRightButton2(int drawableRes,
                                     OnClickListener onClickListener) {
        if (findViewById(R.id.base_btn_right2) == null)
            return;
        findViewById(R.id.base_btn_right2).setOnClickListener(onClickListener);
        ((ImageView) findViewById(R.id.base_btn_right_icon2))
                .setImageResource(drawableRes);
        findViewById(R.id.base_btn_right2).setVisibility(View.VISIBLE);
    }

    public void setTitleLeftButton(int drawableRes,
                                   OnClickListener onClickListener) {
        if (findViewById(R.id.base_btn_back) == null)
            return;
        findViewById(R.id.base_btn_back).setOnClickListener(onClickListener);
        ((ImageView) findViewById(R.id.base_btn_back_icon))
                .setImageResource(drawableRes);
        findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
    }

    public void setTitleLeftButton(OnClickListener onClickListener) {
        if (findViewById(R.id.base_btn_back) == null)
            return;
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                public void onClick(View v) {
                    ViewUtils.hideInput(mContext);
                    finish();
                }
            };
        }
        findViewById(R.id.base_btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
    }

    public void setTitleRightText(String text, OnClickListener listenner) {
        TextView textRight = findViewById(R.id.base_title_right_text);
        if (textRight == null)
            return;
        textRight.setVisibility(View.VISIBLE);
        textRight.setText(text);
        textRight.setOnClickListener(listenner);
    }

    public void setTitleRightText(int titleResId, OnClickListener listenner) {
        String titleStr = getResources().getString(titleResId);
        setTitleRightText(titleStr + "", listenner);
    }

    public void setTitleLeftText(String text, OnClickListener listenner) {
        TextView textRight = findViewById(R.id.base_title_left_text);
        if (textRight == null)
            return;
        textRight.setVisibility(View.VISIBLE);
        textRight.setText(text);
        textRight.setOnClickListener(listenner);
    }

    public void setTitleLeftText(int titleResId, OnClickListener listenner) {
        String titleStr = getResources().getString(titleResId);
        setTitleLeftText(titleStr + "", listenner);
    }

    public void setStatusBarTransparent(boolean isTransparent) {
        if (isTransparent) {
            View titleview = findViewById(R.id.base_title_view);
            View titleviewRoot = findViewById(R.id.base_title_root);
            if (titleview == null || titleviewRoot == null) return;
            titleview.setBackgroundResource(R.color.transparent);
            titleviewRoot.setBackgroundResource(R.drawable.top_bar_bg);
            getImmersionBar().transparentStatusBar().titleBarMarginTop(titleview).init();
//			setTitleBg(R.color.transparent);
        } else {
            getImmersionBar()
//                .transparentStatusBar()
                    .navigationBarColor(R.color.base_blue)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                    .statusBarColor(R.color.title_start)
//                .fitsSystemWindows(true)
                    .init();
        }

    }

    public void showToast(String content) {
        try {
            BasisApp.showToast(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(int resId) {
        try {
            BasisApp.showToast(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getIntentString(String key, String def) {
        String result = getIntent().getStringExtra(key);
        if (TextUtils.isEmpty(result))
            result = def;
        return result;
    }

    public void setHideInputZone(int resId) {
        if (findViewById(resId) != null) {
//			findViewById(resId).setOnClickListener(
//					new OnClickListener() {
//						public void onClick(View v) {
//							ViewUtils.hideInput(mContext, v);
//						}
//					});
            findViewById(resId).setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    ViewUtils.hideInput(mContext, v);
                    return false;
                }
            });
        }
    }

    public void showProgress() {
        showLoadingDialog();
    }

    public void showLoading() {
        showLoadingDialog();
    }

    public void hideLoading() {
        hideLoadingDialog();
    }

    public void dismissProgress() {
        hideLoadingDialog();
    }


    public void showLoadingDialog() {
        if (isFinishing()) return;
        mLoadingDialog.show();
    }

    public void hideLoadingDialog() {
        if (isFinishing()) return;
        mLoadingDialog.dismiss();
    }
}
