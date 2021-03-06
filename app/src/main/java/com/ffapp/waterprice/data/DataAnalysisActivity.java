package com.ffapp.waterprice.data;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.basis.MyViewPagerAdapter;
import com.ffapp.waterprice.data.area.AreaActivity;
import com.ffapp.waterprice.data.area.WaterActivity;
import com.ffapp.waterprice.data.fragement.DataChartFragment;
import com.ffapp.waterprice.video.VideoDetailActivity;
import com.ffapp.waterprice.video.VideoIndexActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.StringUtil;

public class DataAnalysisActivity extends BasisActivity {

    public static String title;
    String url;
    String reportType = "1";

    @BindView(R.id.tv_address)
    TextView mAddressTv;
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    MyViewPagerAdapter myViewPagerAdapter;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int currentPosition = 0;
    Fragment[] fms;
    DataChartFragment dayFragment;
    DataChartFragment monthFragment;
    DataChartFragment yearFragment;

    String deviceCodes = null;

    public static void newInstant(Context mContext, String title, String url) {
        Intent intent = new Intent(mContext, DataAnalysisActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.data_analysis_activity);
        setTitleLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        title = extras.getString("title");
        url = extras.getString("url");
        setTitle("" + title);
        switch (title) {
            case "流量分析":
                String[] flows = getResources().getStringArray(R.array.flow_array);
                spinner.setItems(flows);
                break;
            case "用水户分析":
                String[] waters = getResources().getStringArray(R.array.water_array);
                spinner.setItems(waters);
                break;
            case "土壤墒情分析":
                String[] soils = getResources().getStringArray(R.array.soil_array);
                spinner.setItems(soils);
                break;
            case "环境分析":
                String[] envirs = getResources().getStringArray(R.array.envir_array);
                spinner.setItems(envirs);
                break;
            case "降雨量分析":
                String[] rains = getResources().getStringArray(R.array.rain_array);
                spinner.setItems(rains);
                break;
            case "报警分析":
                reportType = "20";
                String[] alarms = getResources().getStringArray(R.array.alarm_array);
                spinner.setItems(alarms);
                break;
        }

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                reportType = String.valueOf(position + 1);
                if(title.equals("报警分析")){
                    switch (item){
                        case "雨量":
                            reportType = "20";
                            break;
                        case "水位":
                            reportType = "39";
                            break;
                        case "闸上水位":
                            reportType = "3B";
                            break;
                        case "闸下水位":
                            reportType = "3A";
                            break;
                        case "瞬时流速":
                            reportType = "37";
                            break;
                        case "平均流速":
                            reportType = "36";
                            break;
                        case "瞬时流量":
                            reportType = "27";
                            break;
                        case "累计流量":
                            reportType = "30";
                            break;
                        case "土壤温度":
                            reportType = "0D";
                            break;
                        case "土壤湿度":
                            reportType = "18";
                            break;
                        case "土壤盐分":
                            reportType = "48";
                            break;
                        case "土壤PH值":
                            reportType = "46";
                            break;
                        case "气温":
                            reportType = "02";
                            break;
                        case "水温":
                            reportType = "03";
                            break;
                        case "大气压力":
                            reportType = "08";
                            break;
                        case "风速":
                            reportType = "35";
                            break;
                        case "风向":
                            reportType = "33";
                            break;
                    }
                }
                tabChange(currentPosition);
            }
        });

        setView();
    }

    private void setView() {

        fms = new Fragment[3];
//        HomeGrideListData data = new HomeGrideListData();
//        data.setModuleCode("XF_BFM_WATER");
        dayFragment = DataChartFragment.newInstance("day", url, title,reportType);
        monthFragment = DataChartFragment.newInstance("month", url, title,reportType);
        yearFragment = DataChartFragment.newInstance("year", url, title,reportType);
        fms[0] = dayFragment;
        fms[1] = monthFragment;
        fms[2] = yearFragment;
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fms);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setOffscreenPageLimit(2);
        mTabEntities = new ArrayList<>();
        mTabEntities.add(new VideoIndexActivity.TabEntity("日", 0, 0));
        mTabEntities.add(new VideoIndexActivity.TabEntity("月", 0, 0));
        mTabEntities.add(new VideoIndexActivity.TabEntity("年", 0, 0));

        String[] listTitle = new String[3];

        listTitle[0] = "日";
        listTitle[1] = "月";
        listTitle[2] = "年";
        tablayout.setTabData(mTabEntities);
//        tablayout.setViewPager(viewpager,  listTitle);

        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
                currentPosition = position;
                tabChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setCurrentItem(0);
    }

    public static class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }


    @OnClick(R.id.img_address)
    public void toAreaActvity() {
        switch (title) {
            case "用水户分析":
                ActivityTool.skipActivityForResult(mContext, WaterActivity.class, Constants.AREA_CALLBACK);
                break;
            default:
                Bundle extras = new Bundle();
                extras.putSerializable("title",title);
                ActivityTool.skipActivityForResult(mContext, AreaActivity.class,extras,Constants.AREA_CALLBACK);
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.AREA_CALLBACK) {
            ArrayList<String> deviceCodeList = data.getStringArrayListExtra("deviceCodeList");
            deviceCodes = StringUtil.listToString(deviceCodeList);
            mAddressTv.setText(data.getStringExtra("allSiteName"));
            tabChange(currentPosition);
        }
    }

    private void tabChange(int position) {
        switch (position) {
            case 0:
                dayFragment.tabChange(position, deviceCodes, reportType);
                break;
            case 1:
                monthFragment.tabChange(position, deviceCodes, reportType);
                break;
            case 2:
                yearFragment.tabChange(position, deviceCodes, reportType);
                break;
        }
    }

}
