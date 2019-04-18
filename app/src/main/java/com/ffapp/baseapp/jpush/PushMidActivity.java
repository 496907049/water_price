package com.ffapp.baseapp.jpush;

import android.os.Bundle;


import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisActivity;

/***
 * 推送打开中间处理activity
 * **/
public class PushMidActivity extends BasisActivity {


	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		setContentView(R.layout.base_recycler_with_empty);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.initData(savedInstanceState);

		JpushMessageBean data = (JpushMessageBean) getIntent().getSerializableExtra("data");
		if(data == null){
			finish();
			return;
		}

		data.toDetail(mContext);

	}
	boolean isFirstResum = true;

	@Override
	protected void onResume() {
		super.onResume();
		if(isFirstResum){
			isFirstResum = false;
			return;
		}
//			ActivityTool.skipActivity(mContext,HomeTabActivity.class);
			finish();
	}
}
