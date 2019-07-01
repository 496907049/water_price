package com.ffapp.waterprice.home.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.LogUtil;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class ListDataActivity extends BasisActivity {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;


    @BindView(R.id.spin_site_type)
    Spinner mSpinSiteType;



    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;
    private String keyword = "";

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_list_data);
        setTitle("列表信息");
        setTitleLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
//        mRecyclerView.refresh();
        getList();
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
            ((ImageView) findViewById(R.id.refresh_img)).setImageResource(R.mipmap.null_img_searchresult);
            ((TextView) findViewById(R.id.refresh_text_empty)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.refresh_text_empty)).setText("没有搜到任何信息");
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

    private void getList() {
//        RequestParams params = new RequestParams();
////        showProgress();
//        params.put(BaseListDataListBean.PAGE_NAME, mListBean.getNextPage());
//        params.put(BaseListDataListBean.PAGE_SIZE_NAME, BaseListDataListBean.PAGE_SIZE);
//        params.put("keyword", keyword);
//        HttpRestClient.get(Constants.aaa, params, new MyHttpListener() {
//                    @Override
//                    public void onSuccess(int httpWhat, Object result) {
//                        DeviceListBean listBean = (DeviceListBean) result;
//                        mListBean = listBean;
//                        LogUtil.i("onSuccess---->" + listBean.getList().size());
//                        setListView();
//                    }
//
//                    @Override
//                    public void onFailure(int httpWhat, Object result) {
//                        super.onFailure(httpWhat, result);
////            mListBean = new ServiceNewListBean();
//                        setListView();
//                    }
//
//                    @Override
//                    public void onFinish(int httpWhat) {
//                        hideLoading();
//                        onListViewComplete();
//                    }
//                },
//                0, DeviceListBean.class);
        hideLoading();
        onListViewComplete();
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_item, viewGroup, false);
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
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.tv_device_code)
            TextView tvDeviceCode;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.img_online)
            ImageView imgOnline;
            @BindView(R.id.tv_temperature)
            TextView tvTemperature;
            @BindView(R.id.img_temperature)
            ImageView imgTemperature;
            @BindView(R.id.ll_temperature)
            LinearLayout llTemperature;
            @BindView(R.id.tv_water_level)
            TextView tvWaterLevel;
            @BindView(R.id.img_water_level)
            ImageView imgWaterLevel;
            @BindView(R.id.ll_water_level)
            LinearLayout llWaterLevel;
            @BindView(R.id.tv_get_up_water_level)
            TextView tvGetUpWaterLevel;
            @BindView(R.id.img_get_up_water_level)
            ImageView imgGetUpWaterLevel;
            @BindView(R.id.ll_get_up_water_level)
            LinearLayout llGetUpWaterLevel;
            @BindView(R.id.tv_get_down_water_level)
            TextView tvGetDownWaterLevel;
            @BindView(R.id.img_get_down_water_level)
            ImageView imgGetDownWaterLevel;
            @BindView(R.id.ll_get_down_water_level)
            LinearLayout llGetDownWaterLevel;
            @BindView(R.id.tv_current_speed)
            TextView tvCurrentSpeed;
            @BindView(R.id.img_current_speed)
            ImageView imgCurrentSpeed;
            @BindView(R.id.ll_current_speed)
            LinearLayout llCurrentSpeed;
            @BindView(R.id.tv_vmean)
            TextView tvVmean;
            @BindView(R.id.img_mean)
            ImageView imgMean;
            @BindView(R.id.ll_vmean)
            LinearLayout llVmean;
            @BindView(R.id.tv_wink_flow)
            TextView tvWinkFlow;
            @BindView(R.id.img_wink_flow)
            ImageView imgWinkFlow;
            @BindView(R.id.ll_wink_flow)
            LinearLayout llWinkFlow;
            @BindView(R.id.tv_total_flow)
            TextView tvTotalFlow;
            @BindView(R.id.img_total_flow)
            ImageView imgTotalFlow;
            @BindView(R.id.ll_total_flow)
            LinearLayout llTotalFlow;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                DeviceListData listData = mListBean.getList().get(position);
                list_item.setTag(position);
            }


            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
//                int position = (int) v.getTag();
//                DeviceListData deviceListData = mListBean.getList().get(position);
//                Bundle extras = new Bundle();
//                extras.putSerializable("bg", deviceListData.getImgurl());
//                extras.putSerializable("stcd", deviceListData.getStcd());
//                extras.putSerializable("title", deviceListData.getStnm());
//                ActivityTool.skipActivity(mContext, VideoInfoActivity.class, extras);
            }
        }
    }


    @OnClick(R.id.ll_spinner)
    public void spinnerClick(){
        SpinnerAdapter apsAdapter = mSpinSiteType.getAdapter();
        int count = apsAdapter.getCount();
        for (int i = 0; i < count; i++) {
            if ("".equals(apsAdapter.getItem(i).toString())) {
                mSpinSiteType.setSelection(i, true);
                break;
            }
        }
    }
}
