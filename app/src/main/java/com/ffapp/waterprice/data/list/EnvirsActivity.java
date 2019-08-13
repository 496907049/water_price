package com.ffapp.waterprice.data.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListBeanYL;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.TimeUtils;
import my.http.MyHttpListener;
import my.http.OkGoClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EnvirsActivity extends BasisActivity {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;


    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;
    private String beginTime;
    private String dateType;
    private String deviceArr;
    private String endTime;
    private String reportType;
    private String url;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_list_detail);
        setTitle("列表详情");
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

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }
        beginTime = extras.getString("beginTime");
        dateType = extras.getString("dateType");
        deviceArr = extras.getString("deviceArr");
        endTime = extras.getString("endTime");
        reportType = extras.getString("reportType");
        url = extras.getString("url");

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
        MediaType mediaType = MediaType.parse("application/json");

        String param = "{\"beginTime\":\"" + beginTime + "\",\"dateType\":\"" + dateType + "\",\"deviceArr\":\"" + deviceArr + "\",\"endTime\":\"" + endTime + "\",\"reportType\":\"" + reportType + "\"," +
                "\"" + BaseListBeanYL.PAGE_NAME + "\":" + mListBean.getNextPage() + ",\"" + BaseListBeanYL.PAGE_SIZE_NAME + "\":" + BaseListBeanYL.PAGE_SIZE + "}";

        RequestBody body = RequestBody.create(mediaType, param);
        OkGoClient.post(mContext, url + Constants.ANALYSIS_BASE_URL_END_PAGE, body, new MyHttpListener() {
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_envirs, viewGroup, false);
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
            @BindView(R.id.tv_rain_fall)
            TextView tvRainFall;
            @BindView(R.id.tv_atmos)
            TextView tvAtmos;
            @BindView(R.id.tv_wind_speed)
            TextView tvWindSpeed;
            @BindView(R.id.tv_soil_temperature)
            TextView tvSoilTemperature;
            @BindView(R.id.tv_soil_humidity)
            TextView tvSoilHumidity;
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
                tvRainFall.setText((listData.getRainfall() == null) ? "" : listData.getRainfall());
                tvAtmos.setText((listData.getAtmos() == null) ? "" : listData.getAtmos());
                tvWindSpeed.setText((listData.getWindSpeed() == null) ? "" : listData.getWindSpeed());
                tvSoilTemperature.setText((listData.getSoilTemperature() == null) ? "" : listData.getSoilTemperature());
                tvSoilHumidity.setText((listData.getSoilHumidity() == null) ? "" : listData.getSoilHumidity());
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


}

