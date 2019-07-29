package com.ffapp.waterprice.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.AreaBean;
import com.ffapp.waterprice.bean.DataOverviewBean;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.bean.DeviceTypeListBean;
import com.ffapp.waterprice.bean.WaterUserListData;
import com.ffapp.waterprice.bean.WeatherInfoData;
import com.ffapp.waterprice.home.list.ListDataActivity;
import com.ffapp.waterprice.home.map.MapActivity;
import com.ffapp.waterprice.home.site.SiteActivity;
import com.ffapp.waterprice.piechart.PieChart;
import com.ffapp.waterprice.util.MyUtils;
import com.jaygoo.bean.Site;
import com.jaygoo.selector.MultiSelectPopWindow;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.TimeUtils;
import my.http.MyHttpListener;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 首页
 */
public class HomeIndexActivity extends HomeBaseActivity implements AMapLocationListener, LocationSource {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pic_chart)
    PieChart picChart;


    DeviceListBean oldDeviceListBean = new DeviceListBean();
    DeviceListBean mDeviceListBean;

    @BindView(R.id.tv_site_num)
    TextView tvSiteNum;
    @BindView(R.id.tv_ol_num)
    TextView tvOlNum;
    @BindView(R.id.tv_water_user)
    TextView tvWaterUser;
    @BindView(R.id.tv_water_num)
    TextView tvWaterNum;
    @BindView(R.id.tv_actual_num)
    TextView tvActualNum;
    @BindView(R.id.tv_water_surplus)
    TextView tvWaterSurplus;

    private ArrayList<String> typeList = new ArrayList<>();


    private AMap aMap;
    @BindView(R.id.map_view)
    MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @BindView(R.id.scroll_view)
    NestedScrollView scroll_view;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.home_index_activity);
        swipeRefreshLayout.setColorSchemeResources(R.color.base_blue, R.color.base_text_green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        initMap();

        if(isFinishing())return;
        swipeRefreshLayout.setRefreshing(true);
        refreshData();

    }

    private void getDataOverview() {    //获取数据概况数据
        showProgress();
        OkGoClient.get(mContext, Constants.URL_GET_DATA_OVERVIEW, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DataOverviewBean bean = (DataOverviewBean) result;
                setDataOverview(bean);
            }


            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, DataOverviewBean.class);
    }

    private void setDataOverview(DataOverviewBean bean) {
        if (bean == null) return;
        tvSiteNum.setText("" + bean.getSumDevice());
        tvOlNum.setText("" + bean.getSumLineDevice());
        tvWaterUser.setText("" + bean.getSumWaterUser());
        tvWaterNum.setText("" + bean.getSumRatifiedWaterConsumption());
        tvActualNum.setText(bean.getSumRealWaterConsumption() + "万m³");
        tvWaterSurplus.setText("" + bean.getLastWaterConsumption() + "万m³");
    }

    private void getArea() {    //当前登录用户区域信息
        showProgress();
        OkGoClient.get(mContext, Constants.URL_API_AREA, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                AreaBean bean = (AreaBean) result;
                if (bean != null) {
                    MyUtils.putAreaId(bean.getId());
                    getCoordinateById(bean.getId(),"");
                }
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, AreaBean.class);

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

    private void initPicChart() {
        showProgress();
        HttpParams params = new HttpParams();
        params.put("topNum", 5);

        OkGoClient.get(mContext, Constants.URL_GET_WATER_USER, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                WaterUserListData listBean = (WaterUserListData) result;
                listBean.setPicChart(mContext, picChart);
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, WaterUserListData.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("StaticFieldLeak")
    void refreshData() {
        new AsyncTask<String, String, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute("");

        getWeather();
    }


    @Override
    public void onDoubleClick() {
        super.onDoubleClick();
        swipeRefreshLayout.setRefreshing(true);
        refreshData();
    }

    private void getCoordinateById(String areaId,String deviceId){
        MediaType mediaType = MediaType.parse("application/json");
        String param = "{\"areaId\":\"" + isNullOrEmpty(areaId) + "\",\"deviceId\":\""+isNullOrEmpty(deviceId)+"\"}";
        RequestBody body = RequestBody.create(mediaType, param);
        OkGoClient.post(mContext, Constants.URL_DEVICE_PAGE, body, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                mDeviceListBean = (DeviceListBean) result;
                oldDeviceListBean = mDeviceListBean;
                setMapView();
            }

            @Override
            public void onFinish(int httpWhat) {
                hideLoading();
            }
        }, 0, DeviceListBean.class);
    }

    private String isNullOrEmpty(String name){
        if(TextUtils.isEmpty(name)){
            return "";
        }
        return name;
    }

    void setMapView() {
        aMap.clear();
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        DeviceListData deviceListData;
        for (int i = 0, l = mDeviceListBean.getList().size(); i < l; i++) {
                deviceListData = mDeviceListBean.getList().get(i);
                MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                        .fromResource(deviceListData.getMapMarkerResid()))
                        .position(deviceListData.getLatlng())
                        .title(deviceListData.getStnm())
                        .snippet(deviceListData.getStlc());
                Marker marker = aMap.addMarker(markerOption);
                marker.setObject(deviceListData);
                builder.include(deviceListData.getLatlng());
//            marker.showInfoWindow();
        }
        LatLngBounds bounds = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
    }

    void getWeather() {    //获取天气
        HttpParams params = new HttpParams();
        OkGoClient.get(mContext, Constants.URL_HOME_WEATHER, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                WeatherInfoData weatherData = JSON.parseObject(response.body(), WeatherInfoData.class);
                setWeatherView(weatherData);
                getDataOverview();//  获取数据概况数据
                initPicChart();//获取用水情况
                getArea();//获取个人区域信息
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        }, 0, WeatherInfoData.class);
    }

    void setWeatherView(WeatherInfoData weatherData) {
        if (weatherData == null) return;
        ((TextView) findViewById(R.id.text_title_city)).setText(weatherData.getSecondaryname());
        ((TextView) findViewById(R.id.text_title_time)).setText(TimeUtils.getCurrentTimeByFormat("yyyy/MM/dd") + "    " + TimeUtils.getWeekName());
//        BasisApp.loadImg(mContext,weatherData.getIconSrc(),((ImageView)findViewById(R.id.img_weather)));
        ((TextView) findViewById(R.id.text_weather)).setText(weatherData.getConditionDay());
        ((TextView) findViewById(R.id.text_weather_temp)).setText(weatherData.getTempDay() + "℃");
        ((TextView) findViewById(R.id.text_weather_wet)).setText(weatherData.getHumidity() + "%");
        ((TextView) findViewById(R.id.text_weather_wind_direction)).setText(weatherData.getWindDirDay());
        ((TextView) findViewById(R.id.text_weather_wind_power)).setText(weatherData.getWindLevelDay() + "级");
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


    @OnClick(R.id.img_site)
    public void toSite() {
        ActivityTool.skipActivityForResult(mContext, SiteActivity.class, Constants.SITE_CALLBACK);
    }

    @OnClick(R.id.img_list)
    public void toList() {
        ActivityTool.skipActivity(mContext, ListDataActivity.class);
    }

    @OnClick(R.id.img_layer)
    public void toLayer() {
        showProgress();
        typeList.clear();
        OkGoClient.post(mContext, Constants.URL_DEVICE_TYPE, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DeviceTypeListBean listBean = (DeviceTypeListBean) result;
                new MultiSelectPopWindow.Builder(mContext)
                        .setArray(listBean.getList())
                        .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                            @Override
                            public void onClick(ArrayList<Integer> indexList, ArrayList<Site> selectedList) {
                                mDeviceListBean = oldDeviceListBean;
                                for (Site site : selectedList) {
                                    typeList.add(""+site.getId());
                                }
                                DeviceListBean deviceListBean = new DeviceListBean();
                                for(int i =0;i<mDeviceListBean.getList().size();i++){
                                    if(typeList.contains(mDeviceListBean.getList().get(i).getTypeId())){
                                        deviceListBean.getList().add(mDeviceListBean.getList().get(i));
                                    }
                                }
                                mDeviceListBean = deviceListBean;
                                setMapView();
                            }
                        })
                        .setCancel("取消")
                        .setConfirm("完成")
                        .setTitle("选择图层")
                        .build()
                        .show(findViewById(R.id.mBottom));
            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, DeviceTypeListBean.class);


    }

    @OnClick(R.id.img_zoom_in)
    public void toZoonInIm() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mDeviceListBean",mDeviceListBean);
        ActivityTool.skipActivity(mContext,MapActivity.class,bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SITE_CALLBACK) {
           String deviceId = data.getStringExtra("id");
            getCoordinateById("",deviceId);
        }
    }
}
