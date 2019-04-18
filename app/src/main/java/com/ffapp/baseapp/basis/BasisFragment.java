package com.ffapp.baseapp.basis;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import com.ffapp.baseapp.R;
import my.ViewUtils;
import my.base.BaseFragment;

public class BasisFragment extends BaseFragment {

	protected Activity mContext;
	// LoadingDialog mLoadingDialog;
	KProgressHUD mLoadingDialog;

	@Override
	public void initConfig(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext =  getActivity();
		mLoadingDialog = KProgressHUD.create(mContext,
				KProgressHUD.Style.SPIN_INDETERMINATE).setSize(75,75).setCancellable(true);
		EventBus.getDefault().register(this);
		super.initConfig(savedInstanceState);
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

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void showToast(String content) {
		try {
			Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showToast(int resId) {
		try {
			Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		// mLoadingDialog = new LoadingDialog(mContext);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

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
		mLoadingDialog.show();
	}

	public void hideLoadingDialog() {
		if(mLoadingDialog.isShowing())
		mLoadingDialog.dismiss();
	}

	public Fragment getRootFragment(){
		if(getParentFragment() == null){ return  this;}
		Fragment fragment = getParentFragment();
		while (fragment.getParentFragment() != null){
			fragment = getParentFragment();
		}
		return fragment;
	}

	public void onDataRefresh(){

	}

	@Subscribe
	public void onRefreshEvent(RefreshEvent event){
//        showToast("认证结果"+(event.isDone));
		if(getActivity() == null)return;
		onDataRefresh();
	}

	public static class RefreshEvent  {

		public static final int TYPE_APP_MINE = 1;
		public int type;
		public RefreshEvent() {
		}
		public RefreshEvent(int type) {
			this.type = type;
		}
	}

}
