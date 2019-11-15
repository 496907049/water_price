package com.ffapp.waterprice.manage.patrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.common.AdapterCommonListRecylerIn;
import com.ffapp.waterprice.common.PopFilterCommon;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;

/**
 * 运维管理-巡检-列表
 */
public class PatrolListActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private BaseListDataListBean mListBean;

    @BindView(R.id.edit_search)
    EditText edit_search;
    String searchkey = "";
    boolean isSearch = false;

    BaseListData mParamsStatus;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.common_list_activity_with_filter);
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

        setTitle("巡检管理");
        setTitleLeftButton(null);

        findViewById(R.id.view_filter_zone).setVisibility(View.VISIBLE);
        findViewById(R.id.view_filter_2).setVisibility(View.GONE);
        findViewById(R.id.img_divider_ver).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.text_filter_1)).setHint("全部");

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return true;
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        isSearch = getIntent().getBooleanExtra("search", false);
        if (isSearch) {
            findViewById(R.id.view_title_zone).setVisibility(View.GONE);
            findViewById(R.id.view_search_zone).setVisibility(View.VISIBLE);
            getImmersionBar().titleBar(R.id.view_search_zone).init();
        } else {
            findViewById(R.id.view_title_zone).setVisibility(View.VISIBLE);
            findViewById(R.id.view_search_zone).setVisibility(View.GONE);
//            getImmersionBar().transparentBar();

//                setTitleRightButton(R.drawable.top_icon_search, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Bundle extras = new Bundle();
//                        extras.putSerializable("search", true);
//                        ActivityTool.skipActivity(mContext, CheckPlanListActivity.class, extras);
//                    }
//                });


        }
        mListBean = new BaseListDataListBean();
//        BaseListData data;
//        for(int i = 0,l = 10; i < l ;i ++){
//            data = new BaseListData("日常巡查","日常巡查",R.drawable.manage_icon_daily,R.drawable.manage_bg_item_1);
//            mListBean.getList().add(data);
//        }

        setListView();

        if(!isSearch){
            mRecyclerView.refresh();
        }
//        getList();
//        getFromCache();
    }

    void addFake(){
        mListBean = new BaseListDataListBean();
        BaseListData data;
        for(int i = 0,l = 10; i < l ;i ++){
            data = new BaseListData("日常巡查","日常巡查");
            mListBean.getList().add(data);
        }
        onListViewComplete();
    }

    @OnClick(R.id.img_search)
    void search() {
        searchkey = edit_search.getText().toString().trim();
        if (TextUtils.isEmpty(searchkey)) {
            showToast(R.string.search_please_input_searchkey);
            return;
        }
        mListBean.refresh();
        getList();
    }

    @OnClick(R.id.img_cancel)
    void searchCancel() {
        edit_search.setText("");
    }

    @OnClick(R.id.text_cancel)
    void canel() {
        finish();
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
//            ((ImageView)findViewById(R.id.refresh_img)).setImageResource(R.drawable.lack_caveat);
//            findViewById(R.id.refresh_text_empty).setVisibility(View.VISIBLE);
//            ((TextView)findViewById(R.id.refresh_text_empty)).setText("暂无任何告警信息");
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
        addFake();
//        MyParams params = new MyParams();
//        showProgress();
//        if(isSearch){
//            params.put("keyWord", searchkey);
//        }else {
//            params.put("keyWord", "");
//
//        }
//        params.put("s_begindate","");
//        params.put("s_enddate", "");
//
//        params.put(BaseListBeanYL.PAGE_NAME, mListBean.getNextPage());
//        params.put(BaseListBeanYL.PAGE_SIZE_NAME, BaseListBeanYL.PAGE_SIZE);
//
//        HttpRestClient.post(Constants.URL_CHECK_CHECKPLAN_LIST, params, new MyHttpListener() {
//                    @Override
//                    public void onSuccess(int httpWhat, Object result) {
//                        CheckPlanListBean bean = (CheckPlanListBean) result;
//                        mListBean.addListBean(bean);
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
//                0, CheckPlanListBean.class);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mRecyclerView.refresh();
        }
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_list_item_with_btnzone_imgtag, viewGroup, false);
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
            @BindView(R.id.text_title)
            public TextView text_title;
            @BindView(R.id.recyclerview)
            public RecyclerView recyclerview;

            @BindView(R.id.view_detail)
            public TextView view_detail;
            @BindView(R.id.view_file)
            public TextView view_file;
            @BindView(R.id.img_divider_ver)
            public ImageView img_divider_ver;
            public AdapterCommonListRecylerIn myAdapterListChild;
            @BindView(R.id.text_status)
            public TextView text_status;
            @BindView(R.id.img_divider_hor)
            public View img_divider_hor;
            @BindView(R.id.view_btn_zone)
            public View view_btn_zone;
            @BindView(R.id.img_type)
            public ImageView img_type;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                view.findViewById(R.id.view_title_zone).setVisibility(View.GONE);

                view_detail.setVisibility(View.GONE);
                img_divider_ver.setVisibility(View.GONE);
                img_type.setVisibility(View.GONE);
                view_file.setText("详情");
                view_btn_zone.setVisibility(View.GONE);
                img_divider_hor.setVisibility(View.INVISIBLE);

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                recyclerview.setLayoutManager(layoutManager);
                myAdapterListChild = new AdapterCommonListRecylerIn(mContext, null);
                recyclerview.setAdapter(myAdapterListChild);

                text_status.setVisibility(View.VISIBLE);
                recyclerview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return list_item.onTouchEvent(motionEvent);
                    }
                });


            }

            public void bind(int position) {
                BaseListData data = mListBean.getList().get(position);
                myAdapterListChild.setData(data.getListInfoTodo());

                view_file.setTag(position);
                list_item.setTag(position);
                recyclerview.setTag(position);
            }


            @OnClick({R.id.list_item,R.id.recyclerview,R.id.view_file})
            public void onItemClick(View v) {
                int position = (int) v.getTag();
                BaseListData data = mListBean.getList().get(position);
                Bundle extras = new Bundle();
                extras.putSerializable("data",data);
                ActivityTool.skipActivityForResult(mContext, PatrolPostActivity.class,extras,1);
            }
        }
    }
    @OnClick(R.id.view_filter_1)
    void filterDevicetype(View v) {
        BaseListDataListBean mListBean = new BaseListDataListBean();
        BaseListData data;
        data = new BaseListData("9", "全部设备");
        mListBean.getList().add(data);
        data = new BaseListData("1", "未完成任务");
        mListBean.getList().add(data);
        data = new BaseListData("2", "历史任务");
        mListBean.getList().add(data);

        PopFilterCommon popFilter = new PopFilterCommon(mContext, mListBean, new PopFilterCommon.FilterStatusListener() {
            @Override
            public void onTypeChoose(BaseListData type) {
                mParamsStatus = type;
                ((TextView) findViewById(R.id.text_filter_1)).setText(mParamsStatus.getName());
                mRecyclerView.refresh();
            }
        });
        popFilter
                .showAnim(null)
                .dismissAnim(null)
//                .dimEnabled(true)
                .anchorView(v)
                .gravity(Gravity.BOTTOM)
                .show();
    }

}
