package com.ffapp.waterprice.video;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.hcnetsdk.Control.DevManageGuider;
import com.hcnetsdk.Control.SDKGuider;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerView;
import my.LogUtil;

/***
 * 关于我们
 * **/
public class HKVideoDetailActivity extends BasisActivity implements SurfaceHolder.Callback {


    @BindView(R.id.view_play)
    View view_play;
    @BindView(R.id.img_play_icon)
    ImageView img_play_icon;

    @BindView(R.id.survace_view)
    SurfaceView m_osurfaceView;

    boolean isPlay  = false;
    String channel;

    private int m_iPreviewHandle = -1; // playback
    private int m_iSelectChannel = -1;
    private int m_iSelectStreamType = -1;
    private int m_iUserID = -1; // return by NET_DVR_Login_v30

    private int m_byChanNum = 0;// analog channel nums
    private int m_byStartChan = 0;//start analog channel

    private int m_IPChanNum = 0;//digital channel nums
    private int m_byStartDChan = 0;//start digital channel

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.video_hk_detail_activity);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);
        channel= getIntent().getStringExtra("id");
        if(TextUtils.isEmpty(channel)){
            showToast("视频地址不存在");
            finish();
            return;
        }
        LogUtil.i("海康----视频通道号----》"+channel);
        initHk();
        setPlayView();
        m_osurfaceView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startPlay();
            }
        },500);
//        startPlay();
    }

    void initHk(){
        //Channel type
        DevManageGuider.DeviceItem deviceInfo = SDKGuider.g_sdkGuider.m_comDMGuider.getCurrSelectDev();
        if(deviceInfo == null){
            showToast("设备信息不存在");
            finish();
            return;
        }

        m_iUserID = deviceInfo.m_lUserID;
        m_byChanNum = deviceInfo.m_struDeviceInfoV40_jna.struDeviceV30.byChanNum;
        m_byStartChan = deviceInfo.m_struDeviceInfoV40_jna.struDeviceV30.byStartChan;

        m_IPChanNum = deviceInfo.m_struDeviceInfoV40_jna.struDeviceV30.byIPChanNum + deviceInfo.m_struDeviceInfoV40_jna.struDeviceV30.byHighDChanNum * 256;
        m_byStartDChan = deviceInfo.m_struDeviceInfoV40_jna.struDeviceV30.byStartDChan ;

        m_iSelectStreamType = 0;//0，主码流，1次码流

        int channel_from_device = Integer.valueOf(channel);

        m_iSelectChannel = channel_from_device+m_byStartDChan-1;

        if(m_iPreviewHandle != -1){
            SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle);
        }



    }

    void startPlay(){
        if(m_iPreviewHandle != -1){
            SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle);
        }

        NET_DVR_PREVIEWINFO struPlayInfo = new NET_DVR_PREVIEWINFO();
        struPlayInfo.lChannel = m_iSelectChannel;
        struPlayInfo.dwStreamType = m_iSelectStreamType;
        struPlayInfo.bBlocked = 1;
//        m_osurfaceView = findViewById(R.id.Surface_Preview_Play);
        struPlayInfo.hHwnd = m_osurfaceView.getHolder();
        m_iPreviewHandle = SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_V40_jni(m_iUserID,struPlayInfo, null);
        if (m_iPreviewHandle < 0)
        {
            LogUtil.i("NET_DVR_RealPlay_V40 fail, Err:"+ SDKGuider.g_sdkGuider.GetLastError_jni());

            isPlay = false;
            showToast("播放失败");
        }else {
            LogUtil.i("NET_DVR_RealPlay_V40 Succ " );

            isPlay = true;
            showToast("播放成功");

        }
        setPlayView();
//        Toast.makeText(FragPreviewBySurfaceView.this,"NET_DVR_RealPlay_V40 Succ " ,Toast.LENGTH_SHORT).show();
    }

    void stopPlay(){
        if (!SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle))
        {
            LogUtil.i("未开始播放");
            LogUtil.i("NET_DVR_StopRealPlay m_iPreviewHandle：" + m_iPreviewHandle
                    + "  error:" + SDKGuider.g_sdkGuider.GetLastError_jni());
            return;
        }
        m_iPreviewHandle = -1;
        LogUtil.i("NET_DVR_StopRealPlay Succ");
        isPlay = false;
        setPlayView();
    }

    @OnClick(R.id.view_play)
    void viewplay() {
        isPlay = !isPlay;
        if(isPlay){
            startPlay();
        }else{
            stopPlay();
        }
        setPlayView();
    }

    void setPlayView(){
        if(isPlay){
            view_play.setBackground(null);
            img_play_icon.setVisibility(View.GONE);

        }else{
            view_play.setBackgroundResource(R.color.video_pause_bg);
            img_play_icon.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.btn_video)
    void btnvideo() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if(m_iPreviewHandle != -1){
            SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle);
            m_iPreviewHandle = -1;
        }
        super.onDestroy();
    }


     @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_osurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        if (-1 == m_iPreviewHandle) {
            return;
        }
        Surface surface = holder.getSurface();
        if (surface.isValid()) {
            if (-1 == SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlaySurfaceChanged_jni(m_iPreviewHandle, 0, holder))
                Toast.makeText(mContext,"NET_DVR_PlayBackSurfaceChanged"+ SDKGuider.g_sdkGuider.GetLastError_jni(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // m_osurfaceView.setZOrderOnTop(true);
        //Toast.makeText(FragPlayBackByTime.this,"surfaceChanged" + m_iPort ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (-1 == m_iPreviewHandle) {
            return;
        }
        if (holder.getSurface().isValid()) {
            if (-1 == SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlaySurfaceChanged_jni(m_iPreviewHandle, 0, null))
            {
                Toast.makeText(mContext,"NET_DVR_RealPlaySurfaceChanged"+ SDKGuider.g_sdkGuider.GetLastError_jni(),Toast.LENGTH_SHORT).show();
            }
        }
    }

}
