package com.ffapp.waterprice.data.fragement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisFragment;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.ChartInfoBean;
import com.ffapp.waterprice.data.DataAnalysisActivity;
import com.ffapp.waterprice.data.list.EnvirsActivity;
import com.ffapp.waterprice.data.list.FlowActivity;
import com.ffapp.waterprice.data.list.RainActivity;
import com.ffapp.waterprice.data.list.SoilActivity;
import com.ffapp.waterprice.data.list.WarnActivity;
import com.ffapp.waterprice.data.list.WaterActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.TimeUtils;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DataChartFragment extends BasisFragment {

    private String dayOrMonthOrYear;
    private String url;
    private String title;
    private boolean[] timePickerType;
    private String formatStr;

    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.tv_start_timme)
    TextView tvStartTimme;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.btn_line_char)
    Button mLineCharBtn;
    Calendar calendarStart;
    Calendar calendarEnd;

    /**
     * 获取折线图接口所需要的参数
     */
    private String reportType;
    private String submitArr = null;
    private String dateType = "day";


    public static DataChartFragment newInstance(String dayOrMonthOrYear, String url, String title, String reportType) {
        DataChartFragment fragment = new DataChartFragment();
        fragment.dayOrMonthOrYear = dayOrMonthOrYear;
        fragment.url = url;
        fragment.title = title;
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_data_chart);
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();

        switch (dayOrMonthOrYear) {
            case "day":
                timePickerType = new boolean[]{true, true, true, false, false, false};
                formatStr = "yyyy-MM-dd";
                break;
            case "month":
                timePickerType = new boolean[]{true, true, false, false, false, false};
                formatStr = "yyyy-MM";
                break;
            case "year":
                timePickerType = new boolean[]{true, false, false, false, false, false};
                formatStr = "yyyy";
                break;
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        lineChart.setNoDataText("暂无数据");
        barChart.setNoDataText("暂无数据");

    }

    public void tabChange(int position, String submitArr, String reportType) {
        switch (position) {
            case 0:
                this.dateType = "day";
                break;
            case 1:
                this.dateType = "month";
                break;
            case 2:
                this.dateType = "year";
                break;
        }
        this.submitArr = submitArr;
        this.reportType = reportType;
        getLineCharge();
    }


    @OnClick({R.id.tv_start_timme, R.id.tv_end_time, R.id.btn_line_char, R.id.btn_bar_char})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_timme:
                TimePickerView startTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ((TextView) findViewById(R.id.tv_start_timme)).setText(TimeUtils.getTimeByDate(date, formatStr));
                        calendarStart.setTime(date);
                        getLineCharge();
                    }
                })
                        .setType(timePickerType)// 默认全部显示
                        .setDate(calendarStart)
                        .build();
                startTime.show();
                break;
            case R.id.tv_end_time:
                if (calendarEnd == null) calendarEnd = Calendar.getInstance();
                TimePickerView endTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ((TextView) findViewById(R.id.tv_end_time)).setText(TimeUtils.getTimeByDate(date, formatStr));
                        calendarEnd.setTime(date);
                        getLineCharge();
                    }
                })
                        .setType(timePickerType)// 默认全部显示
                        .setDate(calendarEnd)
                        .build();
                endTime.show();
                break;
            case R.id.btn_line_char:
                switch (mLineCharBtn.getText().toString().trim()) {
                    case "切换柱状图":
                        mLineCharBtn.setText("切换条形图");
                        lineChart.setVisibility(View.GONE);
                        barChart.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mLineCharBtn.setText("切换柱状图");
                        lineChart.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                        break;
                }
                break;
            case R.id.btn_bar_char:
                if (submitArr == null) {
                    showToast("请区域地址");
                    return;
                }

                if (TextUtils.isEmpty(tvStartTimme.getText().toString().trim())) {
                    showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(tvEndTime.getText().toString().trim())) {
                    showToast("请选择结束时间");
                    return;
                }

                if (tvStartTimme.getText().toString().trim().equals(tvEndTime.getText().toString().trim())) {
                    showToast("开始时间和结束时间不能相同");
                    return;
                }

                Bundle extras = new Bundle();
                extras.putString("beginTime", tvStartTimme.getText().toString().trim());
                extras.putString("dateType", dateType);
                extras.putString("endTime", tvEndTime.getText().toString().trim());
                extras.putString("reportType", reportType);
                extras.putString("url", url);

                switch (title){
                    case "流量分析":
                        extras.putString("deviceArr", submitArr);
                        ActivityTool.skipActivity(mContext, FlowActivity.class, extras);
                        break;
                    case "用水户分析":
                        extras.putString("waterUserArr", submitArr);
                        ActivityTool.skipActivity(mContext, WaterActivity.class, extras);
                        break;
                    case "土壤墒情分析":
                        extras.putString("deviceArr", submitArr);
                        ActivityTool.skipActivity(mContext, SoilActivity.class, extras);
                        break;
                    case "环境分析":
                        extras.putString("deviceArr", submitArr);
                        ActivityTool.skipActivity(mContext, EnvirsActivity.class, extras);
                        break;
                    case "降雨量分析":
                        extras.putString("deviceArr", submitArr);
                        ActivityTool.skipActivity(mContext, RainActivity.class, extras);
                        break;
                    case "报警分析":
                        extras.putString("deviceArr", submitArr);
                        ActivityTool.skipActivity(mContext, WarnActivity.class, extras);
                        break;
                }
        }
    }


    private void getLineCharge() {

        String beginTime =tvStartTimme.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();

        if (submitArr == null || TextUtils.isEmpty(beginTime) ||TextUtils.isEmpty(tvEndTime.getText().toString().trim())) {
            return;
        }

        if(calendarStart.after(calendarEnd)){
            showToast("开始时间不能大于结束时间");
            return;
        }

        if (beginTime.equals(endTime)) {
            showToast("开始时间和结束时间不能相同");
            return;
        }

        MediaType mediaType = MediaType.parse("application/json");
        String param = null;
        switch (title) {
            case "用水户分析":
                param = "{\"beginTime\": \"" + beginTime + "\",\"dateType\": \"" + dateType + "\",\"waterUser\":\"" + submitArr + "\",\"endTime\":\"" + endTime + "\",\"reportType\":\"" + reportType + "\"}";
                break;
            default:
                param = "{\"beginTime\": \"" + beginTime + "\",\"dateType\": \"" + dateType + "\",\"deviceArr\":\"" + submitArr + "\",\"endTime\":\"" + endTime + "\",\"reportType\":\"" + reportType + "\"}";
                break;

        }
        showProgress();
        RequestBody body = RequestBody.create(mediaType, param);
        OkGoClient.post(mContext, url + Constants.ANALYSIS_BASE_URL_END_PORT, body, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                ChartInfoBean chartInfoBean = JSON.parseObject(response.body(), ChartInfoBean.class);
                chartInfoBean.setChartLine(mContext, lineChart);
                chartInfoBean.setChartBar(mContext, barChart);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissProgress();
            }
        }, 0, ChartInfoBean.class);
    }


}
