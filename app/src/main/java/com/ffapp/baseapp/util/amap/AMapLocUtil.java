package com.ffapp.baseapp.util.amap;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import my.LogUtil;


public class AMapLocUtil implements AMapLocationListener {

    static final String tag = "AMapLocUtil";

    //	static final String LOC_AK = "Z43VKEapjSSlsQ0IglVSWkMC";
//	static final String LOC_AK = "NR8lynw77iOu1HpT4AVCMHN4";
    static final int LOC_SCANSPAN = 5 * 60 * 1000; // 定位频率 单位s
    // static final int LOC_SCANSPAN = 5 * 1000; // 定位频率 单位s
    static final int MIN_RADIUS = 10000; // 定位精度最小10000m
    AMapLocationClient mLocationClient;

    public static String mAddress;
    public static String mLocationCity;
    public static AMapLocation mLocation;
    private OnLocationGetListener mLocationGetListener;
    boolean isOnScanSpanMode = false;
    boolean isNeedGps = true;
    boolean isRequestRadius = false;

    Context mContext;

    public AMapLocUtil(Context context) {
        mContext = context;
        mLocationClient = new AMapLocationClient(context);
//		 mLocationClient.setAK(LOC_AK);
//		mLocationClient.setForBaiduMap(true);
        // setLocationOption();
    }

    public void setOnLocationGetListener(OnLocationGetListener listener) {
        mLocationGetListener = listener;
    }

    public void setOnScanSpanMode(boolean isNeedScanSpan) {
        this.isOnScanSpanMode = isNeedScanSpan;
    }

    public void setIsNeedGps(boolean isNeedGps) {
        this.isNeedGps = isNeedGps;
    }

    // 开始定位
    public void starLocation() {
//        if (mSearch == null) {
//            mSearch = GeoCoder.newInstance();
//            mSearch.setOnGetGeoCodeResultListener(this);
//        }
        setLocationOption();
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    public void starLocation(AMapLocationListener listener) {
        mLocationClient.setLocationListener(listener);
        setLocationOption();
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.unRegisterLocationListener(this);
    }

    public void stopLocation(AMapLocationListener listener) {
        mLocationClient.unRegisterLocationListener(listener);
    }

    public void stop() {
        mLocationClient.stopLocation();
    }

    public void starLocAgain() {
        mLocationClient.startLocation();
    }

    // 设置相关参数
    private void setLocationOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        if (isOnScanSpanMode) {
            option.setInterval(LOC_SCANSPAN);
        }
        if (isNeedGps) {
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            option.setGpsFirst(true); // 打开gps
        } else {
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        }
        // option.setNeedDeviceDirect(false);
        option.setNeedAddress(true);

        // option.setPriority(LocationClientOption.GpsFirst); // 设置网络优先
        // option.setPoiExtraInfo(true);
        // option.disableCache(false);
        mLocationClient.setLocationOption(option);
    }

    private void onAddressGet(String strAddr) {

        mAddress = strAddr;

        if (mLocation != null & !TextUtils.isEmpty(mLocation.getCity())) {
            mLocationCity = mLocation.getCity();
        }

        if (TextUtils.isEmpty(mLocationCity) && !TextUtils.isEmpty(mAddress)) {
            mLocationCity = mAddress.substring(mAddress.indexOf("省") + 1,
                    mAddress.indexOf("市"));
        }
        // result.
        if (mLocationGetListener != null) {
            mLocationGetListener.onLocationGet();
        }
        stop();
        // Intent intent = new
        // Intent(MyBroadcastReceiver.ACTION_LOCATION_CHANGE);
        // mContext.sendBroadcast(intent);
    }

    private void onAddressGetFailed() {
        if (mLocationGetListener != null) {
            mLocationGetListener.onLocationFail();
        }

    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location == null)
            return;

        mLocation = location;
        if (isRequestRadius
                && (location.getAccuracy() == 0 || location.getAccuracy() < MIN_RADIUS)) {
            // Log.i(tag, "定位精度不足，小于" + MIN_RADIUS);
            // starLocation();
            // onAddressGetFailed();
            stop();
        } else {
            stop();
        }
        if (TextUtils.isEmpty(location.getAddress())) {
//            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
//                    .location(BaiduMapUtil.parseXYtoGeoPoint(
//                            location.getLatitude(), location.getLongitude())));
        } else {
            onAddressGet(location.getAddress());
        }
        // BasisApp.mBaiduMapUtil.setMyPosGeoPoint(
        // location.getLatitude(), location.getLongitude());

        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocationType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getAccuracy());
        if (location.getLocationType() == AMapLocation.LOCATION_TYPE_GPS) {
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());
        } else if (location.getLocationType() == AMapLocation.LOCATION_TYPE_WIFI) {
            /**
             * 格式化显示地址信息
             */
            // sb.append("\n省：");
            // sb.append(location.getProvince());
            // sb.append("\n市：");
            // sb.append(location.getCity());
            // sb.append("\n区/县：");
            // sb.append(location.getDistrict());
            sb.append("\naddr : ");
            sb.append(location.getAddress());
        }
        sb.append("\nsdk version : ");
        sb.append(mLocationClient.getVersion());
        // logMsg(sb.toString());
        LogUtil.i(tag, sb.toString());
    }

    public interface OnLocationGetListener {
        void onLocationGet();

        void onLocationFail();
    }

//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult result) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        // TODO Auto-generated method stub
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(mContext, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
//            return;
//        }
//        // mBaiduMap.clear();
//        // mBaiduMap.addOverlay(new
//        // MarkerOptions().position(result.getLocation())
//        // .icon(BitmapDescriptorFactory
//        // .fromResource(R.drawable.icon_marka)));
//        // mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
//        // .getLocation()));
//        // Toast.makeText(GeoCoderDemo.this, result.getAddress(),
//        // Toast.LENGTH_LONG).show();
//        onAddressGet(result.getAddress());
//    }

}
