package com.ffapp.waterprice.site;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListBeanBc;
import com.ffapp.waterprice.bean.DeviceListBean;
import com.ffapp.waterprice.bean.DeviceListData;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 站点演示
 * */
public class SiteSearchActivity extends BasisActivity {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private DeviceListBean mListBean;

    @BindView(R.id.edit_search)
    EditText edit_search;
    String searchkey;

    AsyncHttpClient mAsyncHttpClient;
    @Override
    public void initViews() {
        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.site_search_activity);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
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


        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH){
                    search();
                }
                return true;
            }
        });

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mAsyncHttpClient != null){
                    mAsyncHttpClient.cancelRequests(mContext,true);
                }
                search();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new DeviceListBean();
        setListView();
    }

    @OnClick(R.id.img_cancel)
    void searchCancel(){
        edit_search.setText("");
    }
    @OnClick(R.id.base_btn_back)
    void btnBack(){
       finish();
    }
    @OnClick(R.id.text_search)
    void search(){
            searchkey = edit_search.getText().toString().trim();
            if(TextUtils.isEmpty(searchkey)){
                showToast(R.string.search_please_input_searchkey);
                return;
            }
            mListBean.refresh();
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

    private void getList() {
//        RequestParams params = new RequestParams();
////        showProgress();
//        params.put("word", searchkey);
//        params.put(BaseListBeanBc.PAGE_NAME, mListBean.getNextPage());
//        params.put(BaseListBeanBc.PAGE_SIZE_NAME, BaseListBeanBc.PAGE_SIZE);
//       HttpRestClient.post(Constants.aaa, params, myHttpListener,
//                HTTP_LIST, DeviceListBean.class);

        DeviceListBean bean = new DeviceListBean();
        DeviceListData data;
        for (int i =0;i<6;i++){
            data = new DeviceListData();
            data.setName("展示厅"+i);
            bean.getList().add(data);
            mListBean.addListBean(bean);
        }
        setListView();

        hideLoading();
        onListViewComplete();
    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
//            DeviceListBean bean = (DeviceListBean) result;
//            DeviceListData data = new
//            bean.addListBean(new Bas);
//            mListBean.addListBean(bean);
//            setListView();
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
    };


    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.site_search_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
            @BindView(R.id.tv_name)
            public TextView text_name;
            @BindView(R.id.img_check)
            public ImageView checkImg;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }


            @SuppressLint("NewApi")
            public void bind(int postion){
                DeviceListData data = mListBean.getList().get(postion);
                text_name.setText(data.getName()+"");
                if (data.isCheck() == true){
                    text_name.setTextColor(getColor(R.color.base_text_blue));
                    list_item.setBackground(getDrawable(R.drawable.site_btn_select));
                    checkImg.setVisibility(View.VISIBLE);
                }else {
                    text_name.setTextColor(getColor(R.color.base_text_black));
                    list_item.setBackground(getDrawable(R.drawable.site_btn_default));
                    checkImg.setVisibility(View.GONE);
                }
                list_item.setTag(data);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                mListBean.setAllCheckDefault();
                DeviceListData data = (DeviceListData) v.getTag();
                data.setCheck(true);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


}
