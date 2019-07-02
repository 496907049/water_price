package com.ffapp.waterprice.video;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisFragment;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.other.WebViewX5Activity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class VideoFragment extends BasisFragment {

    public static VideoFragment newInstance(String allOrOl) {
        VideoFragment fragment = new VideoFragment();
        fragment.allOrOl = allOrOl;
        return fragment;
    }

    private String allOrOl;
    private String deviceName;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.base_xrecycler_with_empty);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mListBean.refresh();
                getList(deviceName);
            }

            @Override
            public void onLoadMore() {
                getList(deviceName);
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
//        mRecyclerView.setLoadingMoreEnabled(false);
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

    public void getList(String deviceName) {
        this.deviceName = deviceName;
//        RequestParams params = new RequestParams();
//        showProgress();
//        params.put("token", LoginBean.getUserToken());
//        params.put("allOrOl", allOrOl);
//        params.put("deviceName", deviceName);
//        params.put(BaseListBeanYL.PAGE_NAME, mListBean.getNextPage());
//        params.put(BaseListBeanYL.PAGE_SIZE_NAME, BaseListBeanYL.PAGE_SIZE);
//        HttpRestClient.post(Constants.URL_IRR_LIST, params, myHttpListener,
//                HTTP_LIST, IrrGroupListBean.class);
    }

    private final static int HTTP_LIST = 11;
//    MyHttpListener myHttpListener = new MyHttpListener() {
//        @Override
//        public void onSuccess(int httpWhat, Object result) {
//            IrrGroupListBean bean = (IrrGroupListBean) result;
//            mListBean.addListBean(bean);
//            setListView();
//        }
//
//        @Override
//        public void onFailure(int httpWhat, Object result) {
//            super.onFailure(httpWhat, result);
////            mListBean = new ServiceNewListBean();
//            setListView();
//        }
//
//        @Override
//        public void onFinish(int httpWhat) {
//            hideLoading();
//            onListViewComplete();
//        }
//    };


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
                imgOnLine.setBackgroundResource(R.mipmap.icon_equipment_offline);
                deviceNameTv.setText(data.getName());
                listItem.setTag(data);
            }

            @OnClick(R.id.list_item)
            public void viewFile(View v) {
                DeviceListData data = (DeviceListData) v.getTag();
//                WebViewX5Activity.toWebView(mContext,url,getTitle());
            }
        }
    }
}
