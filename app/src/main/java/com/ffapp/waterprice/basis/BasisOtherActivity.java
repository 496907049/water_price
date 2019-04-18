package com.ffapp.waterprice.basis;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.gyf.barlibrary.ImmersionBar;

import my.ViewUtils;
import my.base.BaseFragmentActivity;

public abstract class BasisOtherActivity extends BaseFragmentActivity {

	protected BasisOtherActivity mContext;

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

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		if (findViewById(R.id.main_view) != null) {
			findViewById(R.id.main_view).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							ViewUtils.hideInput(mContext, v);
						}
					});
		}
		if (findViewById(R.id.main_view2) != null) {
			findViewById(R.id.main_view2).setOnClickListener(
					new OnClickListener() {
						public void onClick(View v) {
							ViewUtils.hideInput(mContext, v);
						}
					});
		}

		mImmersionBar = ImmersionBar.with(this)
				.navigationBarColor(R.color.base_blue)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
				.statusBarColor(R.color.title_start) //状态栏透明度，不写默认0.0f
//		.transparentStatusBar()
//				.fitsSystemWindows(false)
//				.titleBarMarginTop(base_title_relative)
		;
		mImmersionBar.init();   //所有子类都将继承这些相同的属性

		setStatusBarTransparent(isDefautTrans);

	}
	public ImmersionBar getImmersionBar(){
		return  mImmersionBar;
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

	private void initDataS() {
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
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
		TextView title = (TextView) findViewById(R.id.base_title_text);
		if (title == null){
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

	public void setTitleRightButton(int drawableRes,
			OnClickListener onClickListener) {
		if(findViewById(R.id.base_btn_right) == null)return;
		findViewById(R.id.base_btn_right).setOnClickListener(
				onClickListener);
		((ImageView) findViewById(R.id.base_btn_right_icon)).setImageResource(drawableRes);
		findViewById(R.id.base_btn_right).setVisibility(drawableRes == 0? View.INVISIBLE: View.VISIBLE);
	}
	
	public void setTitleRightButton2(int drawableRes,
			OnClickListener onClickListener) {
		if(findViewById(R.id.base_btn_right2) == null)return;
		findViewById(R.id.base_btn_right2).setOnClickListener(
				onClickListener);
		((ImageView) findViewById(R.id.base_btn_right_icon2)).setImageResource(drawableRes);
		findViewById(R.id.base_btn_right2).setVisibility(View.VISIBLE);
	}
	
	public void setTitleLeftButton(int drawableRes,
			OnClickListener onClickListener) {
		if(findViewById(R.id.base_btn_back) == null)return;
		findViewById(R.id.base_btn_back).setOnClickListener(
				onClickListener);
		((ImageView) findViewById(R.id.base_btn_back_icon)).setImageResource(drawableRes);
		findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
	}
	
	
	public void setTitleLeftButton(
			OnClickListener onClickListener) {
		if(findViewById(R.id.base_btn_back) == null)return;
		if(onClickListener == null){
			onClickListener = new OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			};
		}
		findViewById(R.id.base_btn_back).setOnClickListener(
				onClickListener);
		findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
	}
	
	public void setTitleRightText(String text, OnClickListener listenner){
		TextView textRight = (TextView) findViewById(R.id.base_title_right_text);
		if(textRight == null)return;
		textRight.setVisibility(View.VISIBLE);
		textRight.setText(text);
		textRight.setOnClickListener(listenner);
	}
	public void setTitleRightText(int titleResId,OnClickListener listenner) {
		String titleStr = getResources().getString(titleResId);
		setTitleRightText(titleStr + "",listenner);
	}
	
	public void setTitleLeftText(String text, OnClickListener listenner){
		TextView textRight = (TextView) findViewById(R.id.base_title_left_text);
		if(textRight == null)return;
		textRight.setVisibility(View.VISIBLE);
		textRight.setText(text);
		textRight.setOnClickListener(listenner);
	}
	public void setTitleLeftText(int titleResId,OnClickListener listenner) {
		String titleStr = getResources().getString(titleResId);
		setTitleLeftText(titleStr + "",listenner);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
//		HttpRestClient.getAsyncHttpClient().cancelAllRequests(true);
		super.onDestroy();
		if (mImmersionBar != null)
			mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

	}

	public void setStatusBarTransparent(boolean isTransparent){
		if(isTransparent){
			View titleview = findViewById(R.id.base_title_view);
			View titleviewRoot = findViewById(R.id.base_title_root);
			if(titleview == null || titleviewRoot == null)return;
			titleview.setBackgroundResource(R.color.transparent);
			titleviewRoot.setBackgroundResource(R.drawable.user_index_bg);
			getImmersionBar().transparentStatusBar().titleBarMarginTop(titleview).init();
//			setTitleBg(R.color.transparent);
		}else{
			getImmersionBar()
//                .transparentStatusBar()
					.navigationBarColor(R.color.base_blue)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
					.statusBarColor(R.color.title_start)
//                .fitsSystemWindows(true)
					.init();
		}

	}
}
