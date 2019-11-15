package com.ffapp.waterprice.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import my.LogUtil;
import my.TimeUtils;


public class AndroidLocUtil implements LocationListener {

    static final String tag = "AndroidLocUtil";
    LocationManager mLocationManager;
    public static Location mLocation;
    private OnLocationGetListener mLocationGetListener;
    boolean isNeedGps = false;
    boolean isSpanMode = false;
    Context mContext;
    static final int MIN_DISTANCE = 0;
    //    static final int LOC_SCANSPAN = 5 * 60 * 1000; // 定位频率 单位s
    static final int LOC_SCANSPAN = 10; // 定位频率 单位s

    public AndroidLocUtil(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
    public void setOnLocationGetListener(OnLocationGetListener listener) {
        mLocationGetListener = listener;
    }

    public void setIsNeedGps(boolean isNeedGps) {
        this.isNeedGps = isNeedGps;
    }

    // 开始定位
    @SuppressLint("MissingPermission")
    public void starLocation() {
        LogUtil.i(tag,"starLocation");
        String provider = isNeedGps? LocationManager.GPS_PROVIDER: LocationManager.NETWORK_PROVIDER;
        LogUtil.i(tag,"starLocation isProviderEnabled-->"+provider+"--->"+mLocationManager.isProviderEnabled(provider));
        if(isSpanMode){
            mLocationManager.requestLocationUpdates(provider,LOC_SCANSPAN,MIN_DISTANCE,this);
        }else {
            mLocationManager.requestSingleUpdate(provider,this,null);
        }

//       Location location =  mLocationManager.getLastKnownLocation(provider);
//        if(location != null){
//            onLocationChanged(location);
//        }
    }

    @SuppressLint("MissingPermission")
    public void starLocation(LocationListener listener) {
        LogUtil.i(tag,"starLocation with listener");
        String provider = isNeedGps? LocationManager.GPS_PROVIDER: LocationManager.NETWORK_PROVIDER;
        LogUtil.i(tag,"starLocation isProviderEnabled-->"+provider+"--->"+mLocationManager.isProviderEnabled(provider));
        if(isSpanMode){
            mLocationManager.requestLocationUpdates(provider,LOC_SCANSPAN,MIN_DISTANCE,listener);
        }else {
            mLocationManager.requestSingleUpdate(provider,listener,null);
        }
    }

    public void stop(){
        if(mLocationManager == null)return;
        mLocationManager.removeUpdates(AndroidLocUtil.this);
    }

    public void stop(LocationListener listener){
        if(mLocationManager == null)return;
        mLocationManager.removeUpdates(listener);
    }


    // 设置相关参数
    @SuppressLint("MissingPermission")
    private void setLocationOption() {

        // 定义Criteria对象   
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略， Criteria.ACCURACY_FINE则比较精细   
        if (isNeedGps) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);

            // 设置是否需要海拔信息 Altitude   
            criteria.setAltitudeRequired(true);
            // 设置对电源的需求   
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            // 设置是否需要方位信息 Bearing   
            criteria.setBearingRequired(true);
        } else {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // 设置对电源的需求   
            criteria.setPowerRequirement(Criteria.POWER_LOW);
        }

        // 设置是否允许运营商收费   
        criteria.setCostAllowed(true);

//      Location location = mLocationManager.getLastKnownLocation(bestProvider);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        mLocation = location;
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append(TimeUtils.getTimeLongToStrByFormat(location.getTime(), "yyyy-MM-dd HH:mm:ss"));
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getAccuracy());
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());

        sb.append("\nAltitude : ");
        sb.append(location.getAltitude());
        LogUtil.i(tag, sb.toString());

        if(mLocationGetListener != null){
            mLocationGetListener.onLocationGet();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        LogUtil.i(tag,"onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        LogUtil.i(tag,"onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        LogUtil.i(tag,"onProviderDisabled");
        if(mLocationGetListener != null){
            mLocationGetListener.onLocationFail();
        }
    }

    public interface OnLocationGetListener {
        void onLocationGet();

        void onLocationFail();
    }


}
