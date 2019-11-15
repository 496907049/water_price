package com.ffapp.waterprice.manage;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;

import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.map.AMapLocUtil;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import my.IntentUtils;
import my.LogUtil;
import my.MySharedPreferences;
import my.TimeUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class BackService extends Service {

	private final String tag = "BackService";

	private MyBinder myBinder = new MyBinder();
	public static BackService mService;
	private MyReceiver myReceiver;
	private MySharedPreferences mySharedPreferences;
	private Handler mHandler;

//	private static Timer mTimer;
//	private final static int TIME_PERIOD = 5000;
	AMapLocUtil mapLocUtil;

	private int count_for_upload = 0;
	private final static int TRIGER_COUNT_UPLOAD = 1 ;//单位每5分钟触发一次 定位和上传.

	public static final BackService getInstance() {

		if (mService != null)
			return mService;
		return null;
	}

	public static void startWork(Context context) {
		if(IntentUtils.checkServiceRunning(context,BackService.class)){
			LogUtil.i("BackService---startWork--->running");
		}else {
			Intent intent = new Intent(context, BackService.class);
			context.startService(intent);
		}

	}

	public static void stopWork(Context context) {
		if(!IntentUtils.checkServiceRunning(context,BackService.class)){
			LogUtil.i("BackService---不在运行状态--->");
			return;
		}
		getInstance().stopSelf();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogUtil.i(tag, "BackService------>onCreate");
		init();
	}

	private void init() {
		mService = this;
		mySharedPreferences = new MySharedPreferences(this);
		mHandler = new Handler();
		registReceiver();
//		mapLocUtil = new AMapLocUtil(this);
//		initTimer();
	}


//	private void initTimer(){
//		if(mTimer != null){
//			mTimer.cancel();
//			mTimer = null;
//		}
//		mTimer = new Timer();
//		mTimer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				IntentUtils.sendBrocastAction(mService,ACTION_LOCATION_AND_UPLOAD);
//			}
//		},1,TIME_PERIOD);
//
//	}

	void locateAndUpload(){
		if(LoginBean.getInstance() == null)return;
		LogUtil.i(tag,"locateAndUpload");
		if(AMapLocUtil.mLocation != null){
			RequestParams params = new RequestParams();
			params.put("user_id",LoginBean.getInstance() .getUuid());
			params.put("patrol_data","");
			HttpRestClient.post(Constants.URL_PATROL_TRACK_ADD, params, new MyHttpListener() {
				@Override
				public void onSuccess(int httpWhat, Object result) {
					LogUtil.i(tag,"locateAndUpload--->onSuccess");
				}

				@Override
				public void onFailure(int httpWhat, Object result) {
//					super.onFailure(httpWhat, result);
				}

				@Override
				public boolean onHttpFailure(int httpWhat, Throwable arg3) {
//					return super.onHttpFailure(httpWhat, arg3);
					return true;
				}

				@Override
				public void onFinish(int httpWhat) {
					LogUtil.i(tag,"locateAndUpload--->onFinish");
				}
			},0);
		}

		if(mapLocUtil != null){
//			mapLocUtil.stop();
		}else {
			mapLocUtil = new AMapLocUtil(this);
		}
//		mapLocUtil.setGPSonly(true);
		mapLocUtil.starLocation();
	}


	private void registReceiver() {
		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter(ACTION_ONTOUCH);
		intentFilter.addAction(ACTION_ONE_MINIT);
		intentFilter.addAction(Intent.ACTION_TIME_TICK);
		intentFilter.addAction(ACTION_UPDATA_DOWNLOAD);
		registerReceiver(myReceiver, intentFilter);
	}

	private void unregistReceiver() {
		if (myReceiver != null) {
			myReceiver.clearAbortBroadcast();
			unregisterReceiver(myReceiver);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.i(tag, "BackService------>onDestroy");
		unregistReceiver();
		mService = null;
//		if(mTimer != null){
//			mTimer.cancel();
//			mTimer  = null;
//		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	public class MyBinder extends Binder {

		public BackService getService() {
			return BackService.this;
		}
	}

	private void onMinitCount() {
		Calendar calendar = Calendar.getInstance();
		int min = calendar.get(Calendar.MINUTE);
		LogUtil.i(tag,"时间---》" + TimeUtils.getCurrentTimeByFormat("yyyy-MM-dd HH:mm:ss"));
		// LogUtil.i("BackService------>onMinitCount" + timeMinNoTouch);

		count_for_upload ++;
		if(count_for_upload >= TRIGER_COUNT_UPLOAD){
			locateAndUpload();
			count_for_upload = 0;
		}
	}


	/**
	 * 发送ACTION给BackService
	 * 
	 * @param action
	 * @see BackService
	 */
	public static void SendAction(Context context, String action) {
		Intent intent = new Intent(action);
		context.sendBroadcast(intent);
	}

	/**
	 * 发送带intent的ACTION给Backservice
	 */
	public static void SendActionIntent(Context context, Intent intent) {
		// Intent intent = new Intent(action);
		context.sendBroadcast(intent);
	}

	public static final String ACTION_ONTOUCH = "backservice_ontouch";
	public static final String ACTION_ONE_MINIT = "backservice_onminit";
	public static final String ACTION_UPDATA_DOWNLOAD = "backservice_updata_download";

	public static final String ACTION_LOCATION_AND_UPLOAD = "backservice_location_and_upload";

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (TextUtils.isEmpty(action))
				return;
			LogUtil.i(tag, "onReceive------" + action);
			if (action.equals(ACTION_ONE_MINIT) || action.equals(Intent.ACTION_TIME_TICK)) {
				onMinitCount();
			}else if(action.equals(ACTION_LOCATION_AND_UPLOAD)){
				locateAndUpload();
			}
		}
	}


}
