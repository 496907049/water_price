package com.ffapp.waterprice.home.map;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.home.list.ListDataActivity;
import com.ffapp.waterprice.home.site.SiteActivity;
import com.jaygoo.bean.Site;
import com.jaygoo.selector.MultiSelectPopWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;

public class MapActivity extends BasisActivity implements AMapLocationListener, LocationSource {

    private AMap aMap;
    @BindView(R.id.map_view)
    MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    DeviceListBean mDeviceListBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_map);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mapView.onCreate(savedInstanceState); // 此方法必须重写

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }
        mDeviceListBean = (DeviceListBean) extras.getSerializable("mDeviceListBean");
        initMap();
        setMapView();
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {

                }
            });// 设置amap加载成功事件监听器
            //去掉高德地图右下角隐藏的缩放按钮
            aMap.getUiSettings().setZoomControlsEnabled(false);
        }
    }


    void setMapView() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        DeviceListData DeviceListData;
        for (int i = 0, l = mDeviceListBean.getList().size(); i < l; i++) {
            DeviceListData = mDeviceListBean.getList().get(i);
            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .fromResource(DeviceListData.getMapMarkerResid()))
                    .position(DeviceListData.getLatlng())
                    .title(DeviceListData.getStnm())
                    .snippet(DeviceListData.getStlc());
            Marker marker = aMap.addMarker(markerOption);
            marker.setObject(DeviceListData);
            builder.include(DeviceListData.getLatlng());
//            marker.showInfoWindow();
        }
        LatLngBounds bounds = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @OnClick({R.id.img_site, R.id.img_list, R.id.img_layer, R.id.img_zoom_in,R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_site:
                ActivityTool.skipActivityAndFinish(mContext,SiteActivity.class);
                break;
            case R.id.img_list:
                ActivityTool.skipActivityAndFinish(mContext,ListDataActivity.class);
                break;
            case R.id.img_layer:

                break;
            case R.id.img_zoom_in:
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

}
