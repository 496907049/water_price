package my;


import android.util.Log;

import com.ffapp.waterprice.basis.Constants;

public class LogUtil {

	private static final String TAG = "FOUR-FAITH";

	public static void i(String message) {
		if (Constants.DEBUG) {
			Log.i(TAG, message);
//			Logger.t(TAG).i(message);
		}
	}

	public static void i(String tag, String message) {
		if (Constants.DEBUG) {
			Log.i(tag, message);
//			Logger.t(tag).i(message);
		}
	}

	public static void e(String message) {
		if (Constants.DEBUG) {
			Log.e(TAG, message);
//			Logger.t(TAG).e(message);
		}
	}

	public static void e(String tag, String message) {
		if (Constants.DEBUG) {
			Log.e(tag, message);
//			Logger.t(tag).e(message);
		}
	}
	
	public static void d(String message) {
		if (Constants.DEBUG) {
			Log.d(TAG, message);
//			Logger.t(TAG).d(message);
		}
	}

	public static void d(String tag, String message) {
		if (Constants.DEBUG) {
			Log.d(tag, message);
//			Logger.t(tag).d(message);
		}
	}
	public static void w(String message) {
		if (Constants.DEBUG) {
			Log.w(TAG, message);
//			Logger.t(TAG).w(message);
		}
	}

	public static void w(String tag, String message) {
		if (Constants.DEBUG) {
			Log.w(tag, message);
//			Logger.t(tag).i(message);
		}
	}
	public static void v(String message) {
		if (Constants.DEBUG) {
			Log.v(TAG, message);
//			Logger.t(TAG).v(message);
		}
	}

	public static void v(String tag, String message) {
		if (Constants.DEBUG) {
			Log.v(tag, message);
//			Logger.t(tag).v(message);
		}
	}
}
