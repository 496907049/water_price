package com.ffapp.waterprice.data.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisFragment;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.ChartInfoBean;
import com.github.mikephil.chart_3_0_1v.charts.BarChart;
import com.github.mikephil.chart_3_0_1v.charts.LineChart;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import my.TimeUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class DataChartFragment extends BasisFragment {

    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.tv_start_timme)
    TextView tvStartTimme;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    Calendar calendarStart;
    Calendar calendarEnd;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_data_chart);
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }


    @OnClick({R.id.tv_start_timme, R.id.tv_end_time, R.id.btn_line_char, R.id.btn_bar_char})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_timme:
                TimePickerView startTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ((TextView) findViewById(R.id.tv_start_timme)).setText(TimeUtils.getTimeByDate(date, "yyyy-MM-dd HH:mm:ss"));
                        calendarStart.setTime(date);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setDate(calendarStart)
                        .build();
                startTime.show();
                break;
            case R.id.tv_end_time:
                if(calendarEnd == null )calendarEnd = Calendar.getInstance();
                TimePickerView endTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ((TextView) findViewById(R.id.tv_end_time)).setText(TimeUtils.getTimeByDate(date, "yyyy-MM-dd HH:mm:ss"));
                        calendarEnd.setTime(date);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setDate(calendarEnd)
                        .build();
                endTime.show();
                break;
            case R.id.btn_line_char:
                lineChart.setVisibility(View.VISIBLE);
                barChart.setVisibility(View.GONE);
                break;
            case R.id.btn_bar_char:
                lineChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void getLineCharge() {
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("reportType",type_report_type);
//        params.put("startType", startType);
//        params.put("endTime", endTime);
        HttpRestClient.post(Constants.aaa, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        ChartInfoBean chartInfoBean = (ChartInfoBean) result;
//                        chartInfoBean.setChartLine(mContext, lineChart);
//                        chartInfoBean.setChartBar(mContext, barChart);
                    }

                    @Override
                    public void onFailure(int httpWhat, Object result) {
//                        super.onFailure(httpWhat, result);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                    }
                },
                0, ChartInfoBean.class);

    }



}
