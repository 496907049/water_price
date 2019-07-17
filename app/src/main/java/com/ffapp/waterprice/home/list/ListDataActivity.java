package com.ffapp.waterprice.home.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.bean.DeviceTypeListBean;
import com.ffapp.waterprice.data.DataAnalysisActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaygoo.bean.Site;
import com.jaygoo.selector.MultiSelectPopWindow;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.LogUtil;
import my.TimeUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ListDataActivity extends BasisActivity {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.edit_search)
    EditText mSearchEt;


    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;
    private String keyword = "";
    private String areaId = "";
    private String deviceTypeId = "";

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        getDeviceType();
    }

    private void getDeviceType(){
        showProgress();
        OkGoClient.post(mContext, Constants.URL_DEVICE_TYPE, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DeviceTypeListBean listBean = (DeviceTypeListBean) result;
                final ArrayList<Site> siteList = listBean.getList();
                ArrayList<String> deviceNameList = new ArrayList<>();
                for (Site site : siteList){
                    deviceNameList.add(site.getName());
                }
                spinner.setItems(deviceNameList);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                        showToast(siteList.get(position).getName()+"====id=="+);
                        deviceTypeId = String.valueOf(siteList.get(position).getId());
                    }
                });
                deviceTypeId = String.valueOf(siteList.get(0).getId()) ;
                mRecyclerView.refresh();

            }

            @Override
            public void onFinish(int httpWhat) {
                dismissProgress();
            }
        }, 0, DeviceTypeListBean.class);
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
        keyword = mSearchEt.getText().toString().trim();
        MediaType mediaType = MediaType.parse("application/json");
        String param ="{\"areaId\": \""+areaId+"\",\"deviceCode\": \""+keyword+"\",\"deviceTypeId\":\""+deviceTypeId+"\"," +
                "\""+BaseListDataListBean.PAGE_NAME+"\":"+mListBean.getNextPage()+",\""+BaseListDataListBean.PAGE_SIZE_NAME+"\":"+BaseListDataListBean.PAGE_SIZE+"}";
        RequestBody body = RequestBody.create(mediaType,param);
        showProgress();
        OkGoClient.post(mContext,Constants.URL_MONITOR_PAGE, body, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        DeviceListBean listBean = (DeviceListBean) result;
                        mListBean = listBean;
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
            @BindView(R.id.tv_device_name)
            TextView tvDeviceName;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                DeviceListData listData = mListBean.getList().get(position);
                tvDeviceCode.setText(getString(R.string.device_code)+"  "+listData.getDeviceCode());
                tvDeviceName.setText(""+listData.getDeviceName());
                tvTime.setText(TimeUtils.getTimeLongToStrByFormat(listData.getMessageAt(), "yyyy-MM-dd HH:mm:ss"));
                switch (listData.getState()){
                    case 0:
                        imgOnline.setBackgroundResource(R.mipmap.list_img_offline);
                        break;
                    case 1:
                        imgOnline.setBackgroundResource(R.mipmap.list_img_online);
                        break;
                }
                tvTemperature.setText((listData.getTemperature()== null)?"":listData.getTemperature());
                tvWaterLevel.setText((listData.getWaterLevel()== null)?"":listData.getWaterLevel());
                tvGetUpWaterLevel.setText((listData.getGateUp()== null)?"":listData.getGateUp());
                tvGetDownWaterLevel.setText((listData.getGateDown()== null)?"":listData.getGateDown());
                tvCurrentSpeed.setText((listData.getFlowVelocity()== null)?"":listData.getFlowVelocity());
                tvVmean.setText((listData.getAverageFlowVelocity()== null)?"":listData.getAverageFlowVelocity());
                tvWinkFlow.setText((listData.getFlow()== null)?"":listData.getFlow());
                tvTotalFlow.setText((listData.getTotalFlow()== null)?"":listData.getTotalFlow());
                tvTotalFlow.setText((listData.getTotalFlow()== null)?"":listData.getTotalFlow());
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


    @OnClick(R.id.img_search)
    public  void search(){
        getList();
    }

}
