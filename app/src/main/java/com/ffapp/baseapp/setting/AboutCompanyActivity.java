package com.ffapp.baseapp.setting;

import android.os.Bundle;

import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisActivity;

/***
 * 关于我们-公司概况
 * **/
public class AboutCompanyActivity extends BasisActivity {


	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		setContentView(R.layout.setting_about_company_activity);
		setTitle("公司概况");
		setTitleLeftButton(null);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.initData(savedInstanceState);
	}

}
