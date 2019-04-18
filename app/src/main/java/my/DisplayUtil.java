package my;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class DisplayUtil {

	private static final String TAG = DisplayUtil.class.getSimpleName();

	private static float getScale(Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}

	/**
	 * 密度转换像素
	 * */
	public static int dip2px(Context context, float dipValue) {
		float scale = getScale(context);
		return (int) (dipValue * scale + 0.5f);

	}

	/**
	 * 像素转换密度
	 * */
	public static int px2dip(Context context, float pxValue) {
		float scale = getScale(context);
		return (int) (pxValue / scale + 0.5f);
	}

}