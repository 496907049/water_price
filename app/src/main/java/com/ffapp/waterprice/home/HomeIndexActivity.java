package com.ffapp.waterprice.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolygonOptions;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.bean.WeatherInfoData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.LogUtil;
import my.TimeUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 首页
 */
public class HomeIndexActivity extends HomeBaseActivity implements AMapLocationListener, LocationSource {

    @BindView(R.id.recyclerview_data)
    RecyclerView recyclerview_data;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pic_chart)
    PieChart picChart;


    BaseListDataListBean listEnter;
    DeviceListBean mDeviceListBean;
    DeviceListData mDeviceListData;



    private AMap aMap;
    @BindView(R.id.map_view)
    MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @BindView(R.id.scroll_view)
    NestedScrollView scroll_view;

    private String massifId;

    @Override
    public void initViews() {
//        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.home_index_activity);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        recyclerview_data.setLayoutManager(layoutManager);
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
        listEnter = new BaseListDataListBean();
        listEnter.getList().add(new BaseListData("站点总数","258",R.drawable.main_icon_auto));
        listEnter.getList().add(new BaseListData("在线数量","190",R.drawable.main_icon_auto));
        listEnter.getList().add(new BaseListData("用水户","350",R.drawable.main_icon_auto));
        listEnter.getList().add(new BaseListData("核定水量","90.01"+"万m³",R.drawable.main_icon_auto));
        listEnter.getList().add(new BaseListData("实际用量","40"+"万m³",R.drawable.main_icon_auto));
        listEnter.getList().add(new BaseListData("剩余水量","50.01"+"万m³",R.drawable.main_icon_auto));
        recyclerview_data.setAdapter(new MyAdapterListEnter());

        swipeRefreshLayout.setRefreshing(true);
        refreshData();

//        EventBus.getDefault().register(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFinishing())return;
                setMapView();
            }
        },2000);

        initPicChart();
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

//    @OnClick(R.id.view_soil_station)
    void  onSoilChoose(View view){
//        if(mBlockListBeanSoil == null || mBlockListBeanSoil.getList().size()<1){
//            getListBlockSoil();
//            return;
//        }
//        OptionsPickerView pickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                BlockListData blockListData = mBlockListBeanSoil.getList().get(options1);
//                SoilStationListData soilStationListData = blockListData.getSoilMoistrue() == null?null:blockListData.getSoilMoistrue().get(options2);
//                onSoilChoose(blockListData,soilStationListData);
//            }
//
//        })
//                .build();
//        pickerView.setTitleText("选择地块和土壤墒情站");
//        pickerView.setPicker(mBlockListBeanSoil.getListNames(),mBlockListBeanSoil.getListSoilStation());
//        pickerView.show();
    }



    void getWeather(){
        RequestParams params = new RequestParams();
        params.put("token", LoginBean.getUserToken());
        params.put("type", 0);

        HttpRestClient.get(Constants.URL_HOME_WEATHER, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
//                mBlockListBeanSoil = (BlockListBean) result;
                WeatherInfoData weatherData = (WeatherInfoData) result;
                setWeatherView(weatherData);
            }

            @Override
            public void onFinish(int httpWhat) {

            }
        },0, WeatherInfoData.class);

    }

    void setWeatherView(WeatherInfoData weatherData){
        if(weatherData == null)return;
        if(weatherData.getCityName().equals(weatherData.getAreaName())){
            ((TextView)findViewById(R.id.text_title_city)).setText(weatherData.getCityName());
        }else {
            ((TextView)findViewById(R.id.text_title_city)).setText(weatherData.getCityName()+weatherData.getAreaName());
        }
        ((TextView)findViewById(R.id.text_title_time)).setText(TimeUtils.getCurrentTimeByFormat("yyyy/MM/dd")+"    "+TimeUtils.getWeekName());
        BasisApp.loadImg(mContext,weatherData.getIconSrc(),((ImageView)findViewById(R.id.img_weather)));

        ((TextView)findViewById(R.id.text_weather)).setText(weatherData.getCondition());
        ((TextView)findViewById(R.id.text_weather_temp)).setText(weatherData.getTemp()+"℃");
        ((TextView)findViewById(R.id.text_weather_wet)).setText(weatherData.getHumidity()+"%");
        ((TextView)findViewById(R.id.text_weather_wind_direction)).setText(weatherData.getWindDir());
        ((TextView)findViewById(R.id.text_weather_wind_power)).setText(weatherData.getWindLevel()+"级");
    }

    @OnClick(R.id.view_weather)
    void weather(){
//        ActivityTool.skipActivity(mContext, WeatherActivity.class);
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

    public class MyAdapterListEnter extends RecyclerView.Adapter<MyAdapterListEnter.ViewHolder> {

        public MyAdapterListEnter() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapterListEnter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_grid_enter_item, viewGroup, false);
            return new MyAdapterListEnter.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterListEnter.ViewHolder viewHolder, int position) {
            viewHolder.bind(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return listEnter == null ? 0 : listEnter.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.tv_name)
            public TextView text_name;
            @BindView(R.id.tv_value)
            public TextView text_value;
            @BindView(R.id.img_icon)
            public ImageView img_icon;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position){
                BaseListData data = listEnter.getList().get(position);
//                list_item.setTag(position);
                text_name.setText(data.getName());
                text_value.setText(data.getValue());
                img_icon.setImageResource(data.getResid());

                list_item.setTag(position);

            }

            @OnClick(R.id.list_item)
            public void viewDetail(View v) {
                int position =  (int) v.getTag();
                BaseListData data = listEnter.getList().get(position);
                switch (data.getName()){
                    case "自动灌溉":
//                        ActivityTool.skipActivity(mContext, AutoWateringBaseActivity.class);
                        break;
                    case "手动灌溉":
//                        ActivityTool.skipActivity(mContext, ManualBaseActivity.class);
                        break;
                    case "灌溉报告":
//                        WateringReportActivity.newInstant(mContext,massifId);
                        break;
                    case "土壤墒情":
                        break;
                }
            }
        }
    }

}
