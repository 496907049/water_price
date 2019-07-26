package com.ffapp.waterprice.video;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerView;
import my.LogUtil;

/***
 * 关于我们
 * **/
public class VideoDetailActivity extends BasisActivity {


    @BindView(R.id.view_play)
    View view_play;
    @BindView(R.id.img_play_icon)
    ImageView img_play_icon;

    @BindView(R.id.playerview)
    NodePlayerView playerview;
    NodePlayer mNodePlayer;

    boolean isPlay  = false;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.video_detail_activity);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        if(TextUtils.isEmpty(url)){
            showToast("视频地址不存在");
            finish();
            return;
        }
        LogUtil.i("url----->"+url);
        mNodePlayer =  new NodePlayer(this);
        playerview.setRenderCallback(mNodePlayer);
        mNodePlayer.setAudioEnable(false);
//        mNodePlayer.setInputUrl("rtmp:// 120.25.60.232:1936/dvrlive/1008510085_0.live");
        mNodePlayer.setInputUrl(url);
        viewplay();
    }


    @OnClick(R.id.view_play)
    void viewplay() {
        isPlay = !isPlay;
        if(isPlay){
            mNodePlayer.start();
        }else{
            mNodePlayer.pause();
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

    @OnClick(R.id.btn_pic)
    void pic() {
    }
    @OnClick(R.id.btn_video)
    void btnvideo() {
        finish();
    }

}
