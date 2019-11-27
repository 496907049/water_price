package com.ffapp.waterprice.manage.maintain;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.ManageMaintainDetailBean;
import com.ffapp.waterprice.bean.ManageMaintainListData;
import com.ffapp.waterprice.bean.ManagePatrolListData;
import com.ffapp.waterprice.bean.ManageTodoListData;
import com.ffapp.waterprice.common.AdapterCommonDetail;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.TimeUtils;
import my.ViewUtils;
import my.http.MyHttpListener;
import my.http.MyParams;
import my.http.OkGoClient;

/**
 * 维护管理-详情-提交
 */
public class MaintainDetailActivity extends BasisActivity {


    String id;
    ManageMaintainDetailBean mDetailBean;
    @BindView(R.id.recyclerview_detail)
    RecyclerView recyclerview_detail;
    boolean isEdit = true;

    Calendar calendarPatrol;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.manage_maintain_post_activity);
        setTitle("运维任务详情");
        setTitleLeftButton(null);
        recyclerview_detail.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);

        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            showToast("ID不存在");
            finish();
            return;
        }
        isEdit =  getIntent().getBooleanExtra("edit",true);
        getDetail();
        calendarPatrol = Calendar.getInstance();
        setPatrolTimeView();
    }

    private void setViews() {
        if (mDetailBean == null) return;
        if(mDetailBean.getState() == ManageTodoListData.STATUS_DONE){
            isEdit = false;
        }

        recyclerview_detail.setAdapter(new AdapterCommonDetail(mContext, mDetailBean.getListInfoMaintain_Detail(isEdit)));

        if(!isEdit){
            findViewById(R.id.view_edit_zone).setVisibility(View.GONE);
            return;
        }
        if(mDetailBean.getState() == ManageTodoListData.STATUS_DONE){
            findViewById(R.id.view_edit_zone).setVisibility(View.GONE);
        }else {
            findViewById(R.id.view_edit_zone).setVisibility(View.VISIBLE);
            if(mDetailBean.getType() == ManageMaintainDetailBean.TYPE_MACHINE){
                findViewById(R.id.view_replace_machine_zone).setVisibility(View.VISIBLE);
            }else {
                findViewById(R.id.view_replace_machine_zone).setVisibility(View.GONE);
                findViewById(R.id.divider_replace_machine).setVisibility(View.GONE);
            }

            if(mDetailBean.getExecutionTime() != 0){
                calendarPatrol.setTimeInMillis(mDetailBean.getExecutionTime());
                setPatrolTimeView();
            }
            ((EditText)findViewById(R.id.edit_replace_machine)).setText(mDetailBean.getUpdateDevice());
            ((EditText)findViewById(R.id.edit_content)).setText(mDetailBean.getSituation());
        }

    }

    void getDetail() {
        showProgress();
        OkGoClient.get(mContext, Constants.URL_MANAGE_MAINTAIN_DETAIL + id, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                mDetailBean = (ManageMaintainDetailBean) result;
                setViews();
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, ManageMaintainDetailBean.class);
    }


    @OnClick({R.id.view_patrol_time})
    void patrolTimeChoose() {
        TimePickerView startTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                calendarPatrol.setTime(date);
                setPatrolTimeView();
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setDate(calendarPatrol)
                .build();
        startTime.show();
        ViewUtils.hideInput(mContext);
    }

    void setPatrolTimeView() {
        if (calendarPatrol == null) return;
        ((TextView) findViewById(R.id.text_patrol_time_text)).setText(TimeUtils.getTimeByDate(calendarPatrol.getTime(), "yyyy-MM-dd HH:mm:ss"));
    }


    @OnClick(R.id.btn_ok)
    void checkPost() {
        post(true);
//        DialogUtils.DialogOKmsgFinish(mContext,"提交成功");
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {
        post(false);
    }

    void post(final boolean isPost) {

        String content = ((EditText) findViewById(R.id.edit_content)).getText().toString().trim();
        String updateDevice = ((EditText) findViewById(R.id.edit_replace_machine)).getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请填写维护情况");
            return;
        }

        MyParams params = new MyParams();
        params.put("executionTime", calendarPatrol.getTimeInMillis());
        params.put("finishTime", Calendar.getInstance().getTimeInMillis());
        params.put("id", id);
        params.put("situation", content);
        if(isPost){
            params.put("state", ManageTodoListData.STATUS_DONE);
        }else {
            params.put("state", ManageTodoListData.STATUS_DOING);
        }

        if(mDetailBean.getType() == ManageMaintainDetailBean.TYPE_MACHINE){
            params.put("updateDevice", updateDevice);
        }

        showProgress();
        OkGoClient.post(mContext, Constants.URL_MANAGE_MAINTAIN_UPDATE, params.getOkGoRequestBody(), new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                setResult(Activity.RESULT_OK);
                if(isPost){
                    DialogUtils.DialogOKmsgFinish(mContext, ((BasisBean)result).getStatusInfo());
                }else {
                    DialogUtils.DialogOkMsg(mContext, ((BasisBean)result).getStatusInfo());
                }

            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, BasisBean.class);

    }

}
