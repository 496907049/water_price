package com.ffapp.waterprice.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.EditText;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.MyViewPagerAdapter;
import com.ffapp.waterprice.home.HomeBaseActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class VideoIndexActivity extends HomeBaseActivity {

    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.edit_search)
    EditText mSearchEdit;
    MyViewPagerAdapter myViewPagerAdapter;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int currentPosition = 0;
    Fragment[] fms;

    VideoFragment allFragment;
    VideoFragment olFragment;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.video_index_activity);
        super.initViews();
        setTitle("实时视频");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setView();
    }


    private  void setView(){

        fms = new Fragment[2];
//        HomeGrideListData data = new HomeGrideListData();
//        data.setModuleCode("XF_BFM_WATER");
        allFragment = VideoFragment.newInstance("all");
        olFragment = VideoFragment.newInstance("ol");
        fms[0] = allFragment;
        fms[1] = olFragment;
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fms);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setOffscreenPageLimit(2);
        mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity("全部",0,0));
        mTabEntities.add(new TabEntity("在线",0,0));

        String[] listTitle = new String[2];

        listTitle[0] = "全部";
        listTitle[1] = "在线";
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

    @OnClick(R.id.img_search)
    public void search(){
        if(TextUtils.isEmpty(mSearchEdit.getText().toString().trim())){
            showToast("请输入设备名称");
            return;
        }
      switch (currentPosition){
          case 0:
              allFragment.getList(mSearchEdit.getText().toString().trim());
              break;
          case 1:
              olFragment.getList(mSearchEdit.getText().toString().trim());
              break;
      }
    }


}
