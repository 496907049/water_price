package com.ffapp.waterprice.manage;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.IntentUtils;

public class MapTianActivity extends BasisActivity {


    @BindView(R.id.mapview)
    MapView mMapView;
    MyLocationOverlay mLocationOverlay;

    @BindView(R.id.img_begin)
    ImageView img_begin;
    @BindView(R.id.btn_begin)
    View btn_begin;

    @Override
    public void initViews() {
        super.initViews();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manage_map_tian_activity);

        setTitle("巡查轨迹");
        setTitleLeftButton(null);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initMap();
        checkPatrolStatus();
    }

    void initMap() {
        mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
        mMapView.setBuiltInZoomControls(true);

        mLocationOverlay = new MyOverlay(mContext, mMapView);
        mMapView.addOverlay(mLocationOverlay);
        mLocationOverlay.setGpsFollow(false);
        mLocationOverlay.enableMyLocation();

    }


    @OnClick(R.id.btn_begin)
    void onBtnBegin() {
        BackService.startWork(mContext);
        DialogUtils.DialogOkMsg(mContext, "已开始巡查");

        checkPatrolStatus();
    }

    @OnClick(R.id.btn_sign)
    void onBtnSign() {
        DialogUtils.DialogOkMsg(mContext, "签到");
    }

    @OnClick(R.id.btn_post)
    void onBtnPost() {
//        ActivityTool.skipActivity(mContext, GroupTestPostActivity.class);
    }

    @OnClick(R.id.btn_end)
    void onBtnEnd() {
        BackService.stopWork(mContext);
        DialogUtils.DialogOkMsg(mContext, "已结束巡查");
        checkPatrolStatus();
    }

    void checkPatrolStatus(){
        if(IntentUtils.checkServiceRunning(mContext,BackService.class)){
            btn_begin.setEnabled(false);
            img_begin.setImageResource(R.drawable.group_test_icon_begin_disable);
        }else {
            btn_begin.setEnabled(true);
            img_begin.setImageResource(R.drawable.group_test_icon_begin);
        }
    }


    boolean isFirstMyLocation = true;

    class MyOverlay extends MyLocationOverlay {
        public MyOverlay(Context context, MapView mapView) {
            super(context, mapView);
        }

        /*
         * 处理在"我的位置"上的点击事件
         */
        protected boolean dispatchTap() {
            return true;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (isFirstMyLocation) {
                GeoPoint geoPoint = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
                isFirstMyLocation = false;
//动画移动到当前位置
                mMapView.getController().animateTo(geoPoint);
            }


            super.onLocationChanged(location);
        }
    }
}
