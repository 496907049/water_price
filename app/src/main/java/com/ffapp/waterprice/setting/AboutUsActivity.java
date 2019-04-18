package com.ffapp.waterprice.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.SystemParamsUtils;

/***
 * 关于我们
 * **/
public class AboutUsActivity extends BasisActivity {


    @BindView(R.id.text_version)
    TextView text_version;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.setting_aboutus_activity);
        setTitle(R.string.app_aboutus);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);

        text_version.setText("v" + SystemParamsUtils.getAppVersonName(mContext));

    }


    @OnClick(R.id.view_company)
    void aboutCompany() {
        ActivityTool.skipActivity(mContext, AboutCompanyActivity.class);
    }

}
