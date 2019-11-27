package com.ffapp.waterprice.manage;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.ManageLocationListBean;
import com.ffapp.waterprice.bean.ManageLocationListData;
import com.ffapp.waterprice.bean.ManagePatrolListBeanOld;
import com.loopj.android.http.RequestParams;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.overlay.PolylineOverlay;
import com.tianditu.android.maps.renderoption.LineOption;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.IntentUtils;
import my.LogUtil;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class MapTianActivity extends BasisActivity {


    @BindView(R.id.mapview)
    MapView mMapView;
    MyLocationOverlay mLocationOverlay;

    @BindView(R.id.img_begin)
    ImageView img_begin;
    @BindView(R.id.btn_begin)
    View btn_begin;

    PolylineOverlay mPolylineOverlay;

    @Override
    public void initViews() {
        super.initViews();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manage_map_tian_activity);

//        setTitle("巡查轨迹");
//        setTitleLeftButton(null);

        findViewById(R.id.base_title_root).setVisibility(View.GONE);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initMap();
        checkPatrolStatus();
        setMapTrackView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    void initMap() {
        mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
        mMapView.setBuiltInZoomControls(true);

        mLocationOverlay = new MyOverlay(mContext, mMapView);
        mMapView.addOverlay(mLocationOverlay);
        mLocationOverlay.setGpsFollow(false);
        mLocationOverlay.enableMyLocation();

    }

    void setMapTrackView() {
        if (mPolylineOverlay != null) {
            mMapView.removeOverlay(mPolylineOverlay);
        }
        mPolylineOverlay = new PolylineOverlay();

        LineOption option = new LineOption();

        option.setStrokeWidth(5);
        option.setDottedLine(false);
        option.setStrokeColor(0xAA4d7bf3);
        mPolylineOverlay.setOption(option);

        ManageLocationListBean listBean = ManageLocationListBean.getFromCache();
        ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
        if (listBean != null) {
            LogUtil.i("ManageLocationListBean--->" + listBean.getList().size());
            for (ManageLocationListData data : listBean.getList()) {
//                LogUtil.i("data--->"+data.getLatitude()+"--"+data.getLongtitude());
                points.add(new GeoPoint((int) (data.getLatitude() * 1E6), (int) (data.getLongtitude() * 1E6)));
            }
        }
        mPolylineOverlay.setPoints(points);
        mMapView.addOverlay(mPolylineOverlay);
    }


    @OnClick(R.id.btn_begin)
    void onBtnBegin() {

        RequestParams params = new RequestParams();
        HttpRestClient.get(Constants.URL_PATROL_LIST, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                ManagePatrolListBeanOld listBean = (ManagePatrolListBeanOld) result;
                if (listBean.getList().size() <= 0) {
                    DialogUtils.DialogOkMsg(mContext, "当前无巡检任务");
                } else {
                    BackService.startWork(mContext);
                    DialogUtils.DialogOkMsg(mContext, "已开始巡查");
                    checkPatrolStatus();
                }
            }

            @Override
            public void onFinish(int httpWhat) {

            }
        }, 0, ManagePatrolListBeanOld.class);


    }

    @OnClick(R.id.btn_sign)
    void onBtnSign() {
//        DialogUtils.DialogOkMsg(mContext, "签到");
        IntentUtils.sendBrocastAction(mContext, BackService.ACTION_LOCATION_AND_UPLOAD);
    }

    @OnClick(R.id.btn_post)
    void onBtnPost() {
//        DialogUtils.DialogOkMsg(mContext, "巡查上报");
//        ActivityTool.skipActivity(mContext, GroupTestPostActivity.class);
    }

    @OnClick(R.id.btn_end)
    void onBtnEnd() {
        BackService.stopWork(mContext);
        DialogUtils.DialogOkMsg(mContext, "已结束巡查");
        checkPatrolStatus();
    }

    void checkPatrolStatus() {
        if (IntentUtils.checkServiceRunning(mContext, BackService.class)) {
            btn_begin.setEnabled(false);
            img_begin.setImageResource(R.drawable.group_test_icon_begin_disable);
        } else {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(BackService.EventLocation message) {
        if (isFinishing()) return;
        LogUtil.i("onGetMessage-->");
        setMapTrackView();
    }
}
