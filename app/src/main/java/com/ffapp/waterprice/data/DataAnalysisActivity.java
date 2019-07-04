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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DataAnalysisActivity extends BasisActivity {

    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    MyViewPagerAdapter myViewPagerAdapter;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int currentPosition = 0;
    Fragment[] fms;
    DataChartFragment dayFragment;
    DataChartFragment monthFragment;
    DataChartFragment yearFragment;

    public static void newInstant(Context mContext, String data) {
        Intent intent = new Intent(mContext, DataAnalysisActivity.class);
        intent.putExtra("data", data);
        mContext.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.data_analysis_activity);
        setTitle("流量分析");
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

}
