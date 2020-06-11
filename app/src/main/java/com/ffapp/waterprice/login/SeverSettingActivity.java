package com.ffapp.waterprice.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.util.MyUtils;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;


public class SeverSettingActivity extends BasisActivity {

    @BindView(R.id.edit_service)
    EditText edit_ip;
    @BindView(R.id.edit_tenant)
    TextView edit_tenant;
    @BindView(R.id.edit_accesskey)
    TextView edit_accesskey;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.login_service_setting_activity);
        setTitleBg(R.drawable.base_transparent);

//        setTitleLeftButton(null);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

//        edit_ip.setText("47.99.32.215");
//        edit_port.setText("9142");
//        edit_ip.setText("192.168.25.48:12400");

//        edit_port.setText("12400");
        edit_tenant.setText("app");
        edit_accesskey.setText("09b40a8892444fabbc72a0f35f068066");

        BaseListData serciceData = MyUtils.getSerciceData();
        if(serciceData != null){
            edit_ip.setText(serciceData.getId());
//            edit_tenant.setText(serciceData.getTenant());
//            edit_accesskey.setText(serciceData.getAccessKey());
        }else {
//            edit_ip.setText("http://218.85.131.36:7219/");
//            edit_tenant.setText("app");
//            edit_accesskey.setText("45bd5cc0c8694cdc92c43a6edc094089");

        }

//        edit_ip.setText("http://47.112.178.65:5001/");
//        edit_tenant.setText("app");
//        edit_accesskey.setText("81142101244c4308a5532ecb4e007d47");
    }


    @OnClick(R.id.btn_ok)
    void checkAndPost() {
        String ip = edit_ip.getText().toString().trim();
        String tenant = edit_tenant.getText().toString().trim();
        String accesskey = edit_accesskey.getText().toString().trim();

        if (CheckUtils.isStrEmpty(ip)) {
            showToast("请输入IP");
            return;
        }

//        if (CheckUtils.isStrEmpty(tenant)) {
//            showToast("请输入tenant");
//            return;
//        }
//
//        if (CheckUtils.isStrEmpty(accesskey)) {
//            showToast("请输入accesskey");
//            return;
//        }

        if(!ip.startsWith("http")){
            ip = "http://"+ip;
        }
        if(!ip.endsWith("/")){
           ip = ip+"/";
        }
        BaseListData baseListData = new BaseListData(ip,"自定义服务器");
        baseListData.setTenant(tenant);
        baseListData.setAccessKey(accesskey);

        MyUtils.putSerciceData(baseListData);
        setResult(Activity.RESULT_OK);
        finish();
    }

}
