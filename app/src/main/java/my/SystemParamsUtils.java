package my;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.ffapp.waterprice.basis.BasisApp;

import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;


/**
 * 获取一些系统的参数
 *
 */
@SuppressLint("DefaultLocale")
public class SystemParamsUtils {
	public static int getWindowsDimensionSize() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = BasisApp.mContext.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight() {
		final WindowManager windowManager = (WindowManager) BasisApp.mContext
				.getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		boolean isPortrait = display.getWidth() < display.getHeight();
		return isPortrait ? display.getHeight() : display.getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth() {
		final WindowManager windowManager = (WindowManager) BasisApp.mContext
				.getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		boolean isPortrait = display.getWidth() < display.getHeight();
		return isPortrait ? display.getWidth() : display.getHeight();
	}

	@SuppressLint("MissingPermission")
	public static String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) BasisApp.mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {

			return telephonyManager.getDeviceId().toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getLocalMacAddress();
	}

	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "";
		}
		String imei = tm.getDeviceId();
//		if(TextUtils.isEmpty(imei)){
//			imei =  Secure.getString(context.getContentResolver(),
//					Secure.ANDROID_ID);
//		}

		return imei;
	}

	public static String getIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "";
		}
		return tm.getSubscriberId();
	}

	public static String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "";
		}
		return tm.getLine1Number();
	}

	public static String getSerrial() {

		return android.os.Build.SERIAL;
	}
	public static String getAndroidID(Context context) {

		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ifo", ex.toString());
			return null;
		}
		return null;
	}

	public static String getLocalMacAddress() {
		StringBuffer result = new StringBuffer(15);
		WifiManager wifi = (WifiManager) BasisApp.mContext
				.getSystemService(Context.WIFI_SERVICE);
		@SuppressLint("MissingPermission") WifiInfo info = wifi.getConnectionInfo();
		String[] address = info.getMacAddress().split(":");
		for (int i = 0; i < address.length; i++) {
			result.append(address[i]);
		}
		return result.toString() + "000";
	}

	/**
	 * 0：准备SD卡中 1：SD 可用 -1:SD卡不可用
	 * 
	 * @return
	 */
	public static boolean SDEnable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static long getSDCardAvailableMemory() {
		long availableMemory = 0;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(sdcardDir.getPath());
			availableMemory = (long) statFs.getBlockSize()
					* (long) statFs.getAvailableBlocks();
		}
		return availableMemory;
	}

	/**
	 * 
	 * @author sky
	 * 
	 *         Email vipa1888@163.com
	 * 
	 *         QQ:840950105
	 * 
	 *         获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static final int CMNET = 3;
	public static final int CMWAP = 2;
	public static final int WIFI = 1;
	public static final int NONET = -1;

	@SuppressLint("DefaultLocale")
	public static int getAPNType(Context context) {

		int netType = -1;

		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null) {

			return netType;

		}

		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {

//			Log.e("networkInfo.getExtraInfo()",
//					"networkInfo.getExtraInfo() is "
//							+ networkInfo.getExtraInfo());

			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {

				netType = CMNET;

			}

			else {

				netType = CMWAP;

			}

		}

		else if (nType == ConnectivityManager.TYPE_WIFI) {

			netType = WIFI;

		}

		return netType;

	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static String getPhoneModel() {
		String mtype = android.os.Build.MODEL; // 手机型号
		return mtype;
	}

	public static String getSystemReleaseVersion() {
		String release = android.os.Build.VERSION.RELEASE; // android系统版本号
		return release;
	}

	public static String getAppVersonName(Context context) {
		if (context == null)
			return null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);

			// 当前应用的版本名称
			String versionName = info.versionName;

			// // 当前版本的版本号
			// int versionCode = info.versionCode;
			//
			// // 当前版本的包名
			// String packageNames = info.packageName;
			return versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static int getAPPVersonCode(Context context) {
		if (context == null)
			return 0;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);

			// 当前应用的版本名称
//			String versionName = info.versionName;

			// // 当前版本的版本号
			int versionCode = info.versionCode;
			//
			// // 当前版本的包名
			// String packageNames = info.packageName;
			return versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

}
