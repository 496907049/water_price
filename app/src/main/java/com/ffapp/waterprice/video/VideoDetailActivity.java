package com.ffapp.waterprice.video;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.pili.pldroid.player.widget.PLVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 关于我们
 * **/
public class VideoDetailActivity extends BasisActivity {


    @BindView(R.id.view_top_bar)
    View view_top_bar;

    @BindView(R.id.playerview)
    PLVideoView playerview;

    boolean isPlay  = false;
    MediaController mMediaController;
    boolean mIsLiveStreaming = true;
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


        mMediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mMediaController.setOnShownListener(new MediaController.OnShownListener() {
            @Override
            public void onShown() {
                view_top_bar.setVisibility(View.VISIBLE);
            }
        });
        mMediaController.setOnHiddenListener(new MediaController.OnHiddenListener() {
            @Override
            public void onHidden() {
                view_top_bar.setVisibility(View.GONE);
            }
        });
        playerview.setMediaController(mMediaController);

        View loadingView = findViewById(R.id.LoadingView);
        playerview.setBufferingIndicator(loadingView);
        View mCoverView = findViewById(R.id.CoverView);
        playerview.setCoverView(mCoverView);
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

//        playerview.setVideoURI(Uri.parse("http://192.168.25.234:6713/mag/hls/50f33773f884427e8ff7aab9c83516ce/0/live.m3u8"));
        playerview.setVideoURI(Uri.parse(url));

        playerview.start();
//        playerview
//        viewplay();
    }

//
//    @OnClick(R.id.view_play)
//    void viewplay() {
//        isPlay = !isPlay;
//        if(isPlay){
//            playerview.start();
//        }else{
//            playerview.pause();
//        }
//        setPlayView();
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerview.stopPlayback();
    }

    //
    @OnClick(R.id.btn_back)
    void btnback() {
        finish();
    }

}
