package com.ffapp.waterprice.home;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.data.DataIndexActivity;
import com.ffapp.waterprice.manage.ManageIndexActivity;
import com.ffapp.waterprice.manage.MapTianActivity;
import com.ffapp.waterprice.manage.maintain.MaintainListActivity;
import com.ffapp.waterprice.manage.patrol.PatrolListActivity;
import com.ffapp.waterprice.manage.todo.TodoListActivity;
import com.ffapp.waterprice.user.UserIndexActivity;
import com.ffapp.waterprice.video.VideoIndexActivity;
import com.flyco.dialog.listener.OnBtnClickL;
import com.gyf.barlibrary.ImmersionBar;
import com.hcnetsdk.Control.DevManageGuider;
import com.hcnetsdk.Control.SDKGuider;
import com.loopj.android.http.RequestParams;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import my.DialogUtils;
import my.LogUtil;
import my.SystemParamsUtils;
import my.http.MyHttpListener;


/**
 * @作者 suncco
 * @备注 首页的TabActivity ，作为容器装载各个页面activity
 */
@SuppressWarnings("deprecation")
public class HomeTabActivity extends TabActivity implements
        OnClickListener {

    private final String TAG = "HomeTabActivity";
    /**
     * Called when the activity is first created.
     */
    public static final String TAB_TAG_FIRST = "home";
    public static final String TAB_TAG_SECOND = "hot";
    public static final String TAB_TAG_THIRD = "msg";
    public static final String TAB_TAG_FOUR = "me";
    public static final String TAB_TAG_FIVE = "more";

    // public String mHomeTag = TAB_TAG_FIRST_APPLE;
    // public String mHomeTag = TAB_TAG_FIRST_APPLE;
    // public static final String TAB_TAG_SEARCH = "search";
    // public static final String TAB_TAG_CAR = "shoppingcar";
    // public static final String TAB_TAG_MYtebu = "mytebu";

    // mTitleBut2 tag 0: 登录 1：筛选

    public TabHost mTabHost;
    public TextView mTitlleText; // 标题文字
    private Animation left_in, left_out;
    private Animation right_in, right_out;
    public ImageView tabImage_1, tabImage_2, tabImage_3, tabImage_4,
            tabImage_5;
    public TextView tabText_1, tabText_2, tabText_3, tabText_4, tabText_5;

    Intent mIntentFirst;
    Intent mIntentSecond, mIntentThird, mIntentFourth, mIntentFive;
    public View mMainTabBut1, mMainTabBut2, mMainTabBut3, mMainTabBut4,
            mMainTabBut5;

    View mMainTabBgView;

    public static int mCurTag = -1;
    int width = 0;
    private int TAB_COUNTS = 4;
    Resources rs;

    //    MySharedPreferences mSp;
    HomeTabActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        setContentView(R.layout.base_tab_activity);
//        ImmersionBar.with(this)
//                .fullScreen(true)
//                .statusBarAlpha(1f).init();  //状态栏透明度，不写默认0.0f

        ImmersionBar.with(this)
                .navigationBarColor(R.color.base_blue)
                .statusBarColor(R.color.title_start)
                .transparentStatusBar()
//                .statusBarDarkFont(true)
//                .barAlpha(0.1f)  //状态栏和导航栏透明度，不写默认0.0f
                .init();
        mContext = this;
        initViews();
        initData();
        width = SystemParamsUtils.getScreenWidth() / TAB_COUNTS;
        EventBus.getDefault().register(this);
    }

    private void initViews() {
//        mSp = new MySharedPreferences(this);
        tabImage_1 = (ImageView) findViewById(R.id.main_tab_imge_1);
        tabImage_2 = (ImageView) findViewById(R.id.main_tab_imge_2);
        tabImage_3 = (ImageView) findViewById(R.id.main_tab_imge_3);
        tabImage_4 = (ImageView) findViewById(R.id.main_tab_imge_4);
        tabImage_5 = (ImageView) findViewById(R.id.main_tab_imge_5);

        tabText_1 = (TextView) findViewById(R.id.main_tab_text_1);
        tabText_2 = (TextView) findViewById(R.id.main_tab_text_2);
        tabText_3 = (TextView) findViewById(R.id.main_tab_text_3);
        tabText_4 = (TextView) findViewById(R.id.main_tab_text_4);
        tabText_5 = (TextView) findViewById(R.id.main_tab_text_5);

        setupIntent();
        mMainTabBut1 = findViewById(R.id.main_tab_but_1);
        mMainTabBut1.setOnClickListener(this);
        mMainTabBut2 = findViewById(R.id.main_tab_but_2);
        mMainTabBut2.setOnClickListener(this);
        mMainTabBut3 = findViewById(R.id.main_tab_but_3);
        mMainTabBut3.setOnClickListener(this);
        mMainTabBut4 = findViewById(R.id.main_tab_but_4);
        mMainTabBut4.setOnClickListener(this);
        mMainTabBut5 = findViewById(R.id.main_tab_but_5);
        mMainTabBut5.setOnClickListener(this);

        mMainTabBgView = findViewById(R.id.scroll_bg);
        left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
        left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

        right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
        right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);

        // initSlideMenu();
    }

    private void initData() {
        // if (new MySharedPreferences(this).getAutoLogin()) {
        // LoginBean.getFromCache();
        // }

        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions( Manifest.permission.INTERNET
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_WIFI_STATE
                                ,Manifest.permission.READ_PHONE_STATE
                        ).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        BasisApp.showToast(permissions.toString() + "权限拒绝");
                    }
                });


        mMainTabBut1.performClick();
//        mMainTabBut4.performClick();
//        Intent intent = getIntent();
//        String fromLogin = intent.getStringExtra("FROM_LOGIN");
//        if (fromLogin != null && fromLogin.equals("fromLogin")) {
//            mMainTabBut4.performClick();
//            setCurrentTabForTag(TAB_TAG_FOUR, true);
//
//        }

//		mLocationUtil = new BaiduLocUtil(this);
//
//		mLocationUtil.starLocation();

        // if (mSp.getBoolean("first_home", true)) {
        // new GuideDialog(this, R.drawable.home_guide).show();
        // mSp.putBoolean("first_home", false);
        // }
        RequestParams params = new RequestParams();
        params.put("page", 1);
        params.put("page_size", 10);
//        HttpRestClient.get(Constants.URL_CHECK_VERSION, params, myHttpListener,
//                HTTP_CHECK_VERSION, BaseBean.class);

    }

    private static final int HTTP_CHECK_VERSION = 864;
    MyHttpListener myHttpListener = new MyHttpListener() {

        @Override
        public void onSuccess(int what, Object result) {
            // TODO Auto-generated method stub
            switch (what) {
                case HTTP_CHECK_VERSION:

                    break;

                default:
                    break;
            }
        }

        @Override
        public void onFinish(int what) {
            // TODO Auto-generated method stub

//            onListViewComplete();
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void setupIntent() {
        mIntentFirst = new Intent(this, HomeIndexActivity.class);
        mIntentSecond = new Intent(this, VideoIndexActivity.class);
        mIntentThird = new Intent(this, DataIndexActivity.class);
        mIntentFourth = new Intent(this, ManageIndexActivity.class);

//        mIntentFirst = new Intent(this, TodoListActivity.class);
//        mIntentSecond = new Intent(this, PatrolListActivity.class);
//        mIntentThird = new Intent(this, MaintainListActivity.class);
//        mIntentFourth = new Intent(this, MapTianActivity.class);

        mIntentFive = new Intent(this, UserIndexActivity.class);

        mTabHost = getTabHost();

        mTabHost.addTab(buildTabSpec(TAB_TAG_FIRST, R.string.app_name,
                R.drawable.base_icon_arrow_right, mIntentFirst));
        mTabHost.addTab(buildTabSpec(TAB_TAG_SECOND, R.string.app_name,
                R.drawable.base_icon_arrow_right, mIntentSecond));
        mTabHost.addTab(buildTabSpec(TAB_TAG_THIRD, R.string.app_name,
                R.drawable.base_icon_arrow_right, mIntentThird));
        mTabHost.addTab(buildTabSpec(TAB_TAG_FOUR, R.string.app_name,
                R.drawable.base_icon_arrow_right, mIntentFourth));
        mTabHost.addTab(buildTabSpec(TAB_TAG_FIVE, R.string.app_name,
                R.drawable.base_transparent, mIntentFive));
        // mTabHost.addTab(buildTabSpec(TAB_TAG_FIVE, R.string.app_name,
        // R.drawable.ic_launcher, mMoreIntent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
                                         final Intent content) {
        return mTabHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel),
                        getResources().getDrawable(resIcon))
                .setContent(content);
    }


    @Override
    public void onClick(final View v) {
        // TODO Auto-generated method stub
        // BaseApp.showToast(v.getTag() + "");
        final int temp = mCurTag;
        switch (v.getId()) {
            case R.id.main_tab_but_1:
                mCurTag = 0;
                setCurrentTabForTag(TAB_TAG_FIRST, mCurTag > temp);
                setButtons(v);
                break;
            case R.id.main_tab_but_2:
//			if (!LoginActivity.toLoginIfNotLogin(mContext, 0))
//				return;
                mCurTag = 1;
                setCurrentTabForTag(TAB_TAG_SECOND, mCurTag > temp);
                setButtons(v);
                break;
            case R.id.main_tab_but_3:
//			if (!LoginActivity.toLoginIfNotLogin(mContext, 0))
//				return;
                mCurTag = 2;
                setCurrentTabForTag(TAB_TAG_THIRD, mCurTag > temp);
                setButtons(v);
                break;
            case R.id.main_tab_but_4:
//			if (!LoginActivity.toLoginIfNotLogin(mContext, 0))
//				return;
                mCurTag = 3;
                setCurrentTabForTag(TAB_TAG_FOUR, mCurTag > temp);
                setButtons(v);
                break;
            case R.id.main_tab_but_5:
//			if (!LoginActivity.toLoginIfNotLogin(mContext, 0))
//				return;
                mCurTag = 4;
                setCurrentTabForTag(TAB_TAG_FIVE, mCurTag > temp);
                setButtons(v);
                break;
            default:
                break;
        }

        if(temp == mCurTag){
            if(HomeBaseActivity.class.isInstance(getCurrentActivity())){
                ((HomeBaseActivity)getCurrentActivity()).onDoubleClick();
            }
        }
        // BaseApp.showToast("from : " + temp + "to : " + mCurTag + "");
        TranslateAnimation translate = new TranslateAnimation(temp * width,
                mCurTag * width, 0, 0);
        translate.setFillAfter(true);
        translate.setDuration(200);
        mMainTabBgView.startAnimation(translate);
    }

    private void setButtons(View v) {
        mMainTabBut1.setSelected(false);
        mMainTabBut2.setSelected(false);
        mMainTabBut3.setSelected(false);
        mMainTabBut4.setSelected(false);
        mMainTabBut5.setSelected(false);
        v.setSelected(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        LogUtil.i("onNewIntent");
        super.onNewIntent(intent);
        int index = intent.getIntExtra("index", 1);
        switch (index) {
            case 1:
                mMainTabBut1.performClick();
                break;
            case 2:
                mMainTabBut2.performClick();
                break;
            case 3:
                mMainTabBut3.performClick();
                break;
            case 4:
                mMainTabBut4.performClick();
                break;
            case 5:
                mMainTabBut5.performClick();
                break;
        }
    }

    public void setCurrentTabForTag(String tag, boolean dire) {
        if (tag == null) {
            return;
        }
        if (mTabHost.getCurrentTabTag().equals(tag)) {
            return;
        }
        if (dire) {
            mTabHost.getCurrentView().startAnimation(left_out);
        } else {
            mTabHost.getCurrentView().startAnimation(right_out);
        }
        mTabHost.setCurrentTabByTag(tag);
        if (dire) {
            mTabHost.getCurrentView().startAnimation(left_in);
        } else {
            mTabHost.getCurrentView().startAnimation(right_in);
        }
    }

//    @Override
//    public void onBackPressed() {
//        // TODO Auto-generated method stub
//        super.onBackPressed();
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        LogUtil.i("tabactivity----onKeyDown---->");
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mCurTag != 0) {
//                findViewById(R.id.main_tab_but_1).performClick();
//                return true;
//            }
//        }
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            DialogUtils.DialogTwo(mContext, "", "是否立即退出", "立即退出", "取消", new OnBtnClickL() {
//                @Override
//                public void onBtnClick() {
//                    finish();
//                }
//            }, null);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mCurTag != 0) {
                findViewById(R.id.main_tab_but_1).performClick();
                return true;
            }
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                DialogUtils.DialogTwo(mContext, "", "是否立即退出", "立即退出", "取消", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                    }
                }, null);
                return true;
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SDKGuider.g_sdkGuider.m_comDMGuider.logout_jna(0);
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        // overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }

    @Subscribe
    public void onHomeTabEvent(EventHomeTab event){
//        showToast("认证结果"+(event.isDone));
        if(isFinishing())return;
        switch (event.toposition) {
            case 1:
                mMainTabBut1.performClick();
                break;
            case 2:
                mMainTabBut2.performClick();
                break;
            case 3:
                mMainTabBut3.performClick();
                break;
            case 4:
                mMainTabBut4.performClick();
                break;
            case 5:
                mMainTabBut5.performClick();
                break;
        }
    }

    public static class EventHomeTab{
        int toposition;

        public EventHomeTab(int toposition) {
            this.toposition = toposition;
        }
    }

}