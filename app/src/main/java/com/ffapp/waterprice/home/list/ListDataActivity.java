package com.ffapp.waterprice.home.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListBeanYL;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.ffapp.waterprice.bean.DeviceTypeListBean;
import com.ffapp.waterprice.home.site.SiteActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaygoo.bean.Site;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.TimeUtils;
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
    private String deviceId = "";
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

    private void getDeviceType() {
        showProgress();
        OkGoClient.post(mContext, Constants.URL_DEVICE_TYPE, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                DeviceTypeListBean listBean = (DeviceTypeListBean) result;
                final ArrayList<Site> siteList = listBean.getList();
                ArrayList<String> deviceNameList = new ArrayList<>();
                for (Site site : siteList) {
                    deviceNameList.add(site.getName());
                }
                spinner.setItems(deviceNameList);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                        showToast(siteList.get(position).getName()+"====id=="+);
                        deviceTypeId = String.valueOf(siteList.get(position).getId());
                    }
                });
                deviceTypeId = String.valueOf(siteList.get(0).getId());
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
        String param = "{\"areaId\": \"" + areaId + "\",\"deviceCode\": \"" + keyword + "\",\"deviceTypeId\":\"" + deviceTypeId + "\",\"deviceId\":\"" + deviceId + "\"," +
                "\"" + BaseListBeanYL.PAGE_NAME + "\":" + mListBean.getNextPage() + ",\"" + BaseListBeanYL.PAGE_SIZE_NAME + "\":" + BaseListBeanYL.PAGE_SIZE + "}";
        RequestBody body = RequestBody.create(mediaType, param);
        showProgress();
        OkGoClient.post(mContext, Constants.URL_MONITOR_PAGE, body, new MyHttpListener() {
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
            @BindView(R.id.tv_device_code)
            TextView tvDeviceCode;
            @BindView(R.id.tv_device_name)
            TextView tvDeviceName;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_temperature)
            TextView tvTemperature;
            @BindView(R.id.tv_humidity)
            TextView tvHumidity;
            @BindView(R.id.tv_water_level)
            TextView tvWaterLevel;
            @BindView(R.id.tv_rain_fall)
            TextView tvRainFall;
            @BindView(R.id.tv_depth)
            TextView tvDepth;
            @BindView(R.id.tv_current_speed)
            TextView tvCurrentSpeed;
            @BindView(R.id.tv_soil_water_potential)
            TextView tvSoilWaterPotential;
            @BindView(R.id.tv_soil_ph)
            TextView tvSoilPh;
            @BindView(R.id.tv_get_up_water_level)
            TextView tvGetUpWaterLevel;
            @BindView(R.id.tv_signal_intensity)
            TextView tvSignalIntensity;
            @BindView(R.id.tv_atmos)
            TextView tvAtmos;
            @BindView(R.id.tv_total_flow)
            TextView tvTotalFlow;
            @BindView(R.id.list_item)
            LinearLayout list_item;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                DeviceListData listData = mListBean.getList().get(position);
                tvDeviceCode.setText(getString(R.string.device_code) + "  " + listData.getDeviceCode());
                tvDeviceName.setText("" + listData.getDeviceName());
                tvTime.setText(TimeUtils.getTimeLongToStrByFormat(listData.getMessageAt(), "yyyy-MM-dd HH:mm:ss"));
                tvTemperature.setText((listData.getTemperature() == null) ? "" : listData.getTemperature());
                tvWaterLevel.setText((listData.getWaterLevel() == null) ? "" : listData.getWaterLevel());
                tvGetUpWaterLevel.setText((listData.getGateUp() == null) ? "" : listData.getGateUp());
                tvHumidity.setText((listData.getHumidity() == null) ? "" : listData.getHumidity());
                tvCurrentSpeed.setText((listData.getFlowVelocity() == null) ? "" : listData.getFlowVelocity());
                tvSoilWaterPotential.setText((listData.getSoilWaterPotential() == null) ? "" : listData.getSoilWaterPotential());
                tvRainFall.setText((listData.getRainfall() == null) ? "" : listData.getRainfall());
                tvTotalFlow.setText((listData.getTotalFlow() == null) ? "" : listData.getTotalFlow());
                tvSoilPh.setText((listData.getSoilPh() == null) ? "" : listData.getSoilPh());
                tvDepth.setText((listData.getDepth() == null) ? "" : listData.getDepth());
                tvSignalIntensity.setText((listData.getSignalIntensity() == null) ? "" : listData.getSignalIntensity());
                tvAtmos.setText((listData.getAtmos() == null) ? "" : listData.getAtmos());
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
    public void search() {
        getList();
    }

    @OnClick(R.id.get_site_icon)
    public void getSite() {
        ActivityTool.skipActivityForResult(mContext, SiteActivity.class, Constants.SITE_CALLBACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SITE_CALLBACK) {
            this.deviceId = data.getStringExtra("id");
            mRecyclerView.refresh();
        }
    }

}
