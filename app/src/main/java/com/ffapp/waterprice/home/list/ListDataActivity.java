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
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.TimeUtils;
import my.http.MyHttpListener;
import my.http.MyParams;
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
                        mRecyclerView.refresh();
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
        MyParams params = new MyParams();
        params.put("areaId",areaId);
        params.put("deviceCode",keyword);
        params.put("deviceTypeId",deviceTypeId);
        params.put("deviceId",deviceId);
        params.put(BaseListBeanYL.PAGE_NAME,mListBean.getNextPage());
        params.put(BaseListBeanYL.PAGE_SIZE_NAME,BaseListBeanYL.PAGE_SIZE);
        RequestBody body = params.getOkGoRequestBody();
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
            @BindView(R.id.recyclerview)
            RecyclerView recyclerview;
            @BindView(R.id.list_item)
            LinearLayout list_item;
            MyChildAdapter childAdapter;
            List<String> keyList;
            List<String> valueList;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(layoutManager);
            }

            public void bind(int position) {
                DeviceListData listData = mListBean.getList().get(position);
                tvDeviceCode.setText(getString(R.string.device_code) + "  " + listData.getDeviceCode());
                tvDeviceName.setText("" + listData.getTitle());
                Map<String,String> map = listData.getContent();
                keyList = new ArrayList<>();
                valueList = new ArrayList<>();
                for(Map.Entry<String, String> entry : map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if(key.equals("时间")){
                        tvTime.setText(""+value);
                    }else {
                        keyList.add(key);
                        if(value.equals("--")){
                            valueList.add("0");
                        }else {
                            valueList.add(value);
                        }
                    }
                }
                childAdapter = new MyChildAdapter(keyList,valueList);
                recyclerview.setAdapter(childAdapter);
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

    public class MyChildAdapter extends RecyclerView.Adapter<MyChildAdapter.ViewHolder> {

        List<String> keyList;
        List<String> valueList;

        public MyChildAdapter() {
        }

        public MyChildAdapter(List<String> keyList,List<String> valueList) {
            this.keyList = keyList;
            this.valueList = valueList;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_child_item, viewGroup, false);
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
            return keyList == null ? 0 : keyList.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_key)
            TextView tvKey;
            @BindView(R.id.tv_value)
            TextView tvValue;
            @BindView(R.id.iv_line)
            ImageView lineImg;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                tvKey.setText(keyList.get(position));
                tvValue.setText(valueList.get(position));
                if(position == keyList.size()-1){
                    lineImg.setVisibility(View.GONE);
                }else {
                    lineImg.setVisibility(View.VISIBLE);
                }
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
