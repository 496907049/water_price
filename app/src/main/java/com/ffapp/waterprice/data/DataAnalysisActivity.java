package com.ffapp.waterprice.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.MyViewPagerAdapter;
import com.ffapp.waterprice.data.fragement.DataChartFragment;
import com.ffapp.waterprice.video.VideoFragment;
import com.ffapp.waterprice.video.VideoIndexActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DataAnalysisActivity extends BasisActivity {

    String title;
    String siteType;

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

    public static void newInstant(Context mContext, String title) {
        Intent intent = new Intent(mContext, DataAnalysisActivity.class);
        intent.putExtra("title", title);
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
        if(extras == null){
            return;
        }
        title = extras.getString("title");
        setTitle(""+title);
        switch (title){
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
                String[] alarms = getResources().getStringArray(R.array.alarm_array);
                spinner.setItems(alarms);
                break;
        }

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                getChart(item);
            }
        });

        setView();
    }

    private  void setView(){

        fms = new Fragment[3];
//        HomeGrideListData data = new HomeGrideListData();
//        data.setModuleCode("XF_BFM_WATER");
        dayFragment =  new DataChartFragment();
        monthFragment =  new DataChartFragment();
        yearFragment =  new DataChartFragment();
        fms[0] = dayFragment;
        fms[1] = monthFragment;
        fms[2] = yearFragment;
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fms);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setOffscreenPageLimit(2);
        mTabEntities = new ArrayList<>();
        mTabEntities.add(new VideoIndexActivity.TabEntity("日",0,0));
        mTabEntities.add(new VideoIndexActivity.TabEntity("月",0,0));
        mTabEntities.add(new VideoIndexActivity.TabEntity("年",0,0));

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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setCurrentItem(0);
    }
    public static  class TabEntity implements CustomTabEntity {
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


    private void getChart(String siteType){

    }

}
