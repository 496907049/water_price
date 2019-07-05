package com.ffapp.waterprice.user.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.bean.MsgListData;
import com.ffapp.waterprice.view.H5contentWebview;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 通告详情
 */
public class MsgDetailActivity extends BasisActivity {

    public static void toDetail(Context context,String id){
        Intent intent= new Intent(context,MsgDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @BindView(R.id.webview)
    H5contentWebview webview;
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_time)
    TextView text_time;

    String id;
    MsgListData mDetailBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.msg_detail_activity);

        setTitle("通告详情");
      setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        id = getIntent().getStringExtra("id");

        if(TextUtils.isEmpty(id)){
            showToast("文章ID不存在");
            finish();
            return;
        }
        getDetail();
    }



    private void getDetail() {
        RequestParams params = new RequestParams();
        showProgress();
        params.put("token", LoginBean.getUserToken());
        params.put("article_id", id);

        showLoading();
        HttpRestClient.get(Constants.aaa, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        mDetailBean = (MsgListData) result;
                        setDetailView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                    hideLoading();
                    }
                },
                0, MsgListData.class);

    }

    void setDetailView(){
        if(mDetailBean == null)return;
        text_title.setText(mDetailBean.getTitle());
        text_time.setText(mDetailBean.getCreate_time());
        webview.setContent(mDetailBean.getContent());
//        webview.setText(Html.fromHtml(mDetailBean.getContent(),new UrlImageGetter(webview,mContext),null));
    }
}