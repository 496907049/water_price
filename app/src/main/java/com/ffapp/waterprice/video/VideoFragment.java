package com.ffapp.waterprice.video;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisFragment;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListBeanYL;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.other.WebViewX5Activity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VideoFragment extends BasisFragment {

    public static VideoFragment newInstance(String state) {
        VideoFragment fragment = new VideoFragment();
        fragment.state = state;
        return fragment;
    }

    private String state;    //1表示在线  其他就是不在线
    private String deviceName;
    private String deviceId;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.base_xrecycler_with_empty);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mListBean.refresh();
                getList();
            }

            @Override
            public void onLoadMore() {
                getList();
            }
        });
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new DeviceListBean();
        setListView();
        mRecyclerView.refresh();
    }

    private void setListView() {
        if (mAdapter == null) {
            mAdapter = new MyAdapterList();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void onListViewComplete() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
        mRecyclerView.setLoadingMoreEnabled(mListBean.hasNextPage());
        setEmptyView();
    }

    private void setEmptyView() {
        if (mListBean == null || mListBean.getList().size() == 0) {
            findViewById(R.id.refresh_view).setVisibility(View.VISIBLE);
            findViewById(R.id.refresh_view).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            mRecyclerView.refresh();
                        }
                    });
        } else {
            findViewById(R.id.refresh_view).setVisibility(View.INVISIBLE);
        }
    }

    public void getListFromName(String deviceName){
        this.deviceName = deviceName;
        this.deviceId = "";
        mListBean.refresh();
        getList();
    }

    public void getListFromDeviceId(String deviceId){
        this.deviceName = "";
        this.deviceId = deviceId;
        mListBean.refresh();
        getList();
    }

    public void getList() {
        MediaType mediaType = MediaType.parse("application/json");
        String param = "{\"deviceName\":\"" + isNullReturnEmpty(deviceName) + "\",\"deviceId\":\""+isNullReturnEmpty(deviceId)+"\",\"state\":\""+state+"\",\"" + BaseListBeanYL.PAGE_NAME + "\":" + mListBean.getNextPage() + ",\"" + BaseListBeanYL.PAGE_SIZE_NAME + "\":" + BaseListBeanYL.PAGE_SIZE + "}";
        RequestBody body = RequestBody.create(mediaType, param);
        OkGoClient.post(mContext, Constants.URL_DEVICE_PAGE, body, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DeviceListBean listBean = (DeviceListBean) result;
                mListBean.addListBean(listBean);
                setListView();
            }

            @Override
            public void onFailure(int httpWhat, Object result) {
                super.onFailure(httpWhat, result);
                setListView();
            }

            @Override
            public void onFinish(int httpWhat) {
                hideLoading();
                onListViewComplete();
            }
        }, 0, DeviceListBean.class);
    }

    private String isNullReturnEmpty(String name){
        if(TextUtils.isEmpty(name)){
            return "";
        }
        return name;
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.bind(position);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.img_online)
            ImageView imgOnLine;
            @BindView(R.id.tv_device_name)
            TextView deviceNameTv;
            @BindView(R.id.list_item)
            LinearLayout listItem;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }


            public void bind(int position) {
                DeviceListData data = mListBean.getList().get(position);
                switch (data.getState()) {
                    case 1:    //1表示在线
                        imgOnLine.setBackgroundResource(R.mipmap.icon_equipment_online);
                        break;
                    default:
                        imgOnLine.setBackgroundResource(R.mipmap.icon_equipment_offline);
                        break;
                }

                deviceNameTv.setText(data.getDeviceName());
                listItem.setTag(data);
            }

            @OnClick(R.id.list_item)
            public void viewFile(View v) {
                DeviceListData data = (DeviceListData) v.getTag();
                Bundle extras = new Bundle();
                extras.putSerializable("url",data.getVideoUrl());
                ActivityTool.skipActivity(mContext,VideoDetailActivity.class,extras);
//                WebViewX5Activity.toWebView(mContext,url,getTitle());
            }
        }
    }
}
