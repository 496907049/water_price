package com.ffapp.waterprice.basis;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ffapp.waterprice.R;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class BasisApp extends MultiDexApplication {
    private static BasisApp APP;

    public static Context mContext;
    private static Handler handler;
    public static String DIR_PATH_USER;

    public static boolean isProgramExit = false;
    static Toast mToast;

    public static BaseAnimatorSet bas_in = new BounceBottomEnter();
    public static BaseAnimatorSet bas_out = new SlideBottomExit();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
//		mContext = getApplicationContext();
        isProgramExit = false;
        APP = this;
//		SDKInitializer.initialize(this);
        handler = new Handler();
        DIR_PATH_USER = getFilesDir().getAbsolutePath() + "/";
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//				SDKInitializer.initialize(this);
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
        ImageLoader.getInstance().init(configuration);
        initX5();

        initJpush();

//        initUmeng();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initJpush() {
//        CustomPushNotificationBuilder builder = new
//                CustomPushNotificationBuilder(this,
//                R.layout.customer_notitfication_layout,
//                R.id.icon,
//                R.id.title,
//                R.id.text);
//        // 指定定制的 Notification Layout
//        builder.statusBarDrawable = R.drawable.icon_notification_small;
//        // 指定最顶层状态栏小图标
//        builder.layoutIconDrawable = R.drawable.icon_notification_small;
//        // 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(2, builder);

        //        JPushInterface.init(this);     		// 初始化 JPush
        JPushInterface.setDebugMode(Constants.DEBUG); 	// 设置开启日志,发布时请关闭日志
        BasicPushNotificationBuilder builderB = new BasicPushNotificationBuilder(this);
        builderB.statusBarDrawable = R.drawable.icon_notification_small;
        builderB.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builderB.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builderB);

      //  极光统计
        JAnalyticsInterface.init(this);
        JAnalyticsInterface.setDebugMode(Constants.DEBUG);
        JAnalyticsInterface.initCrashHandler(this);
    }

    public static BasisApp getInstance() {
        return APP;
    }

    /**
     * 短消息提示
     */
    public static void showToast(final int resId) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    /**
     * 短消息提示
     */
    public static void showToast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//				if (mToast != null) {
//					mToast.cancel();
//				}
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderForimgpickup());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    public static void loadImg(Context mContext, String imgurl, ImageView imageView, int res_default) {

//		if(TextUtil.isEmpty(imgurl))return;
        Glide.with(mContext).load(imgurl)
//				.apply(new RequestOptions().placeholder(res_default).error(res_default))
                .placeholder(res_default).error(res_default)
                .into(imageView);
    }

    public static void loadImg(Context mContext, String imgurl, ImageView imageView) {
        loadImg(mContext, imgurl, imageView, R.drawable.base_transparent);
    }

    public static void loadImg(String imgurl, ImageView imageView) {
        loadImg(BasisApp.mContext, imgurl, imageView, R.drawable.base_transparent);
    }

    public static void loadImgAsbitmap(Context mContext, String imgurl, ImageView imageView, int res_default) {

//		if(TextUtil.isEmpty(imgurl))return;
        Glide.with(mContext)
//				.asBitmap()
                .load(imgurl)
                .asBitmap()
                .placeholder(res_default)
//				.apply(new RequestOptions().placeholder(res_default).error(res_default))
                .into(imageView);
    }

    private void initX5() {
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                LogUtil.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//            }
//        };
////		QbSdk.allowThirdPartyAppDownload(true);
//        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    //防止button短时间内多次点击
    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            fixViewMutiClickInShortTime(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    //防止短时间内多次点击，弹出多个activity 或者 dialog ，等操作
    private void fixViewMutiClickInShortTime(final Activity activity) {
        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                proxyOnlick(activity.getWindow().getDecorView(),5);
            }
        });
    }

    private void proxyOnlick(View view, int recycledContainerDeep) {
        if (view.getVisibility() == View.VISIBLE) {
            boolean forceHook = recycledContainerDeep == 1;
            if (view instanceof ViewGroup) {
                boolean existAncestorRecycle = recycledContainerDeep > 0;
                ViewGroup p = (ViewGroup) view;
                if (!(p instanceof AbsListView || p instanceof ListView) || existAncestorRecycle) {
                    getClickListenerForView(view);
                    if (existAncestorRecycle) {
                        recycledContainerDeep++;
                    }
                } else {
                    recycledContainerDeep = 1;
                }
                int childCount = p.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = p.getChildAt(i);
                    proxyOnlick(child, recycledContainerDeep);
                }
            } else {
                getClickListenerForView(view);
            }
        }
    }

    /**
     * 通过反射  查找到view 的clicklistener
     * @param view
     */
    public static void getClickListenerForView(View view) {
        try {
            Class viewClazz = Class.forName("android.view.View");
            //事件监听器都是这个实例保存的
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfoObj = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");

            if (null != onClickListenerField) {
                if (!onClickListenerField.isAccessible()) {
                    onClickListenerField.setAccessible(true);
                }
                View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
                if (!(mOnClickListener instanceof ProxyOnclickListener)) {
                    //自定义代理事件监听器
                    View.OnClickListener onClickListenerProxy = new ProxyOnclickListener(mOnClickListener);
                    //更换
                    onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
                }else{
                    Log.e("OnClickListenerProxy", "setted proxy listener ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//自定义的代理事件监听器


    static class ProxyOnclickListener implements View.OnClickListener {
        private View.OnClickListener onclick;

        private int MIN_CLICK_DELAY_TIME = 1000;

        private long lastClickTime = 0;

        public ProxyOnclickListener(View.OnClickListener onclick) {
            this.onclick = onclick;
        }

        @Override
        public void onClick(View v) {
            //点击时间控制
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                Log.e("OnClickListenerProxy", "OnClickListenerProxy"+this);
                if (onclick != null) onclick.onClick(v);
            }
        }
    }

}
