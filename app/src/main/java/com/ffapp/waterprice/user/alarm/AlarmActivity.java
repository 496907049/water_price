package com.ffapp.waterprice.user.alarm;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.AlarmMsgListBean;
import com.ffapp.waterprice.bean.AlarmMsgListData;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 通告
 */
public class AlarmActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    private MyAdapterList mAdapter;
    private AlarmMsgListBean mListBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_alarm);
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
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        spinner.setItems("asdf", "aaaa", "KitKat", "adff", "asdf");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        setTitle("报警预览");
        setTitleLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new AlarmMsgListBean();
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

    private void getList() {
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("token", LoginBean.getUserToken());
//
//        params.put(BaseListBeanYL.PAGE_NAME, mListBean.getNextPage());
//        params.put(BaseListBeanYL.PAGE_SIZE_NAME, BaseListBeanYL.PAGE_SIZE);
//        HttpRestClient.get(Constants.URL_MESSAGE_LIST, params, myHttpListener,
//                HTTP_LIST, MsgListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            AlarmMsgListBean bean = (AlarmMsgListBean) result;
            mListBean.addListBean(bean);
            setListView();
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
//            mListBean = new ServiceNewListBean();
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_msg_list_item, viewGroup, false);
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
            @BindView(R.id.text_time)
            public TextView text_time;
            @BindView(R.id.text_connect)
            public TextView text_connect;
            @BindView(R.id.text_type)
            public TextView text_type;
            @BindView(R.id.img_icon)
            public ImageView img_icon;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }


            public void bind(int position) {
                AlarmMsgListData data = mListBean.getList().get(position);
                text_title.setText(data.getTitle());
                text_time.setText(data.getTime());
//                text_connect.setText(Html.fromHtml(data.getContent()));
                text_connect.setText(data.getContent());
                text_type.setText(data.getType());
                if(data.isRead()){
                    img_icon.setBackgroundResource(R.mipmap.msg_icon_alarm_isread);
                }else {
                    img_icon.setBackgroundResource(R.mipmap.msg_icon_alarm);
                }

                list_item.setTag(position);
            }
            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                int position = (int) v.getTag();
                AlarmMsgListData data = mListBean.getList().get(position);
                if(!data.isRead()){
                    data.setRead(true);
                    notifyDataSetChanged();
                    setMsgRead(data);
                }
//                MsgDetailActivity.toDetail(mContext,data.getArticle_id()+"");
            }

        }
    }


    void setMsgRead(AlarmMsgListData data) {
        RequestParams params = new RequestParams();
        params.put("token", LoginBean.getUserToken());
//        params.put("readInfo", data.getJsonReadInfo());

//        showLoading();
        HttpRestClient.post(Constants.aaa , params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
//                        BasisBean bean = (BasisBean) result;
                        setResult(Activity.RESULT_OK);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                    }
                },
                0, BasisBean.class);
    }
}