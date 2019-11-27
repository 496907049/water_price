package com.ffapp.waterprice.manage.patrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.ManageMaintainDetailBean;
import com.ffapp.waterprice.bean.ManageMaintainListData;
import com.ffapp.waterprice.bean.ManagePatrolDetailBean;
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
 * 维护管理-巡检详情-提交
 */
public class PatrolPostActivity extends BasisActivity {


    String id;
    ManagePatrolDetailBean mDetailBean;
    @BindView(R.id.recyclerview_detail)
    RecyclerView recyclerview_detail;
    boolean isEdit = true;

    Calendar calendarPatrol;
    BaseListData mParamsPatrolResult;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.manage_patrol_post_activity);
        setTitle("巡检管理");
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
            if(mDetailBean.getExecutionTime() != 0){
                calendarPatrol.setTimeInMillis(mDetailBean.getExecutionTime());
                setPatrolTimeView();
            }
            ((EditText)findViewById(R.id.edit_address)).setText(mDetailBean.getAddress());
            ((EditText)findViewById(R.id.edit_content)).setText(mDetailBean.getSituation());
            mParamsPatrolResult = new BaseListData(mDetailBean.getConclusion(),mDetailBean.getConclusion());
            ((TextView) findViewById(R.id.text_patrol_result_text)).setText(mParamsPatrolResult.getName());

        }

    }

    void getDetail() {
        showProgress();
        OkGoClient.get(mContext, Constants.URL_MANAGE_PATROL_DETAIL + id, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                mDetailBean = (ManagePatrolDetailBean) result;
                setViews();
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, ManagePatrolDetailBean.class);
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


    @OnClick({R.id.view_patrol_result})
    void patrolResultChoose() {

        final BaseListDataListBean listBean = new BaseListDataListBean();
        listBean.getList().add(new BaseListData("正常","正常"));
        listBean.getList().add(new BaseListData("硬件故障","硬件故障"));
        listBean.getList().add(new BaseListData("软件故障","软件故障"));
        listBean.getList().add(new BaseListData("站点被破坏","站点被破坏"));

        OptionsPickerView pickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mParamsPatrolResult = listBean.getList().get(options1);
                ((TextView) findViewById(R.id.text_patrol_result_text)).setText(mParamsPatrolResult.getName());
            }
        })
                .build();
        pickerView.setPicker(listBean.getListString());
        pickerView.show();
        ViewUtils.hideInput(mContext);
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
        String address = ((EditText) findViewById(R.id.edit_address)).getText().toString().trim();
        String patrol_result = ((TextView) findViewById(R.id.text_patrol_result_text)).getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请填写巡检情况");
            return;
        }

        MyParams params = new MyParams();
        params.put("address", address);
        params.put("conclusion", patrol_result);
        params.put("executionTime", calendarPatrol.getTimeInMillis());
//        params.put("finishTime", Calendar.getInstance().getTimeInMillis());
        params.put("id", id);
        params.put("situation", content);
        if(isPost){
            params.put("state", ManageTodoListData.STATUS_DONE);
        }else {
            params.put("state", ManageTodoListData.STATUS_DOING);
        }

        showProgress();
        OkGoClient.post(mContext, Constants.URL_MANAGE_PATROL_UPDATE, params.getOkGoRequestBody(), new MyHttpListener() {
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
