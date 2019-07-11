package com.ffapp.waterprice.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;
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
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.DataOverviewBean;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.bean.WeatherInfoData;
import com.ffapp.waterprice.home.site.SiteActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.jaygoo.bean.Site;
import com.jaygoo.selector.MultiSelectPopWindow;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.TimeUtils;
import my.http.HttpRestClient;
import my.http.MyBaseBean;
import my.http.MyHttpListener;
import my.http.OkGoClient;

/**
 * 首页
 */
public class HomeIndexActivity extends HomeBaseActivity implements AMapLocationListener, LocationSource {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pic_chart)
    PieChart picChart;

    DeviceListBean mDeviceListBean;

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

        swipeRefreshLayout.setRefreshing(true);
        refreshData();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(isFinishing())return;
//                setMapView();
//            }
//        },2000);

        getDataOverview();//  获取数据概况数据


        initPicChart();
    }

    private void getDataOverview(){    //获取数据概况数据
        RequestParams params = new RequestParams();
        params.put("token", LoginBean.getUserToken());

        OkGoClient.get(mContext,Constants.URL_GET_DATA_OVERVIEW, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
//                mBlockListBeanSoil = (BlockListBean) result;
                DataOverviewBean bean = (DataOverviewBean) result;
            }

            @Override
            public void onFinish(int httpWhat) {

            }
        },0, DataOverviewBean.class);

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

    private void initPicChart(){
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(30f,"aaa"));
        strings.add(new PieEntry(70f,"bbb"));

        PieDataSet dataSet = new PieDataSet(strings,"Label");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.red));
        colors.add(getResources().getColor(R.color.base_blue));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);

        picChart.setData(pieData);
        picChart.invalidate();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("StaticFieldLeak")
    void refreshData(){

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

    void setMapView(){
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

    void getWeather(){
        HttpParams params = new HttpParams();
        OkGoClient.get(mContext, Constants.URL_HOME_WEATHER, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                WeatherInfoData weatherData = JSON.parseObject(response.body(), WeatherInfoData.class);
                setWeatherView(weatherData);
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

    void setWeatherView(WeatherInfoData weatherData){
        if(weatherData == null)return;
            ((TextView)findViewById(R.id.text_title_city)).setText(weatherData.getSecondaryname());
        ((TextView)findViewById(R.id.text_title_time)).setText(TimeUtils.getCurrentTimeByFormat("yyyy/MM/dd")+"    "+TimeUtils.getWeekName());
//        BasisApp.loadImg(mContext,weatherData.getIconSrc(),((ImageView)findViewById(R.id.img_weather)));
        ((TextView)findViewById(R.id.text_weather)).setText(weatherData.getConditionDay());
        ((TextView)findViewById(R.id.text_weather_temp)).setText(weatherData.getTempDay()+"℃");
        ((TextView)findViewById(R.id.text_weather_wet)).setText(weatherData.getHumidity()+"%");
        ((TextView)findViewById(R.id.text_weather_wind_direction)).setText(weatherData.getWindDirDay());
        ((TextView)findViewById(R.id.text_weather_wind_power)).setText(weatherData.getWindLevelDay()+"级");
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
    public void toSite(){
        ActivityTool.skipActivity(mContext,SiteActivity.class);
    }
    @OnClick(R.id.img_list)
    public void toList(){

    }
    @OnClick(R.id.img_layer)
    public void toLayer(){
        ArrayList<Site> siteArrayList = new ArrayList<>();
        Site site = new Site();
        site.setId(1);
        site.setName("水流流量站");
        siteArrayList.add(site);
        site = new Site();
        site.setId(2);
        site.setName("土壤墒情站");
        siteArrayList.add(site);
        site = new Site();
        site.setId(3);
        site.setName("气象站");
        siteArrayList.add(site);
        site = new Site();
        site.setId(4);
        site.setName("雨量站");
        siteArrayList.add(site);
        site = new Site();
        site.setId(5);
        site.setName("视频测试站");
        siteArrayList.add(site);

        new MultiSelectPopWindow.Builder(this)
                .setArray(siteArrayList)
                .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onClick(ArrayList<Integer> indexList, ArrayList<Site> selectedList) {
                        String siteName ="";
                        for (Site site : selectedList){
                            siteName += site.getName()+",";
                        }
                        Toast.makeText(getApplication(), ""+siteName, Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancel("取消")
                .setConfirm("完成")
                .setTitle("选择图层")
                .build()
                .show(findViewById(R.id.mBottom));
    }

    @OnClick(R.id.img_zoom_in)
    public void toZoonInIm(){


    }

    @OnClick(R.id.view_weather)
    void weather(){
//        ActivityTool.skipActivity(mContext, WeatherActivity.class);
    }

}
