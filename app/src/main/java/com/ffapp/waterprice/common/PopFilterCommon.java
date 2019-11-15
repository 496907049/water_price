package com.ffapp.waterprice.common;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.SystemParamsUtils;

public class PopFilterCommon extends BasePopup<PopFilterCommon> {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    Activity mActivity;
    private BaseListDataListBean mListBean;
    private MyAdapterList mAdapter;

    FilterStatusListener listener;

    public PopFilterCommon(Activity context, BaseListDataListBean listbean, FilterStatusListener listener) {
        super(context);
//            setCanceledOnTouchOutside(false);
        mActivity = context;
        mListBean = listbean;
//        mListBean = new BaseListDataListBean();
//        BaseListData data;
//        data = new BaseListData("","全部");
//        mListBean.getList().add(data);
//        data = new BaseListData("1","有效");
//        mListBean.getList().add(data);
//        data = new BaseListData("2","失效");
//        mListBean.getList().add(data);
        this.listener = listener;
    }

    @Override
    public View onCreatePopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.alarm_pop_view, null);
        ButterKnife.bind(this, inflate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SystemParamsUtils.getScreenWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
        recyclerview.setLayoutParams(params);


        return inflate;
    }


    @Override
    public void show() {
        offset(0,(Build.VERSION.SDK_INT < 24)?1:-40);
        super.show();
    }


    @Override
    public void setUiBeforShow() {
            initData();
    }

    private void initData() {
//        mListBean = new BaseListDataListBean();
//        BaseListData data;
//        data = new BaseListData("","全部设备");
//        mListBean.getList().add(data);
//        data = new BaseListData("","告警设备");
//        mListBean.getList().add(data);
//        data = new BaseListData("","安全设备");
//        mListBean.getList().add(data);
        mAdapter = new MyAdapterList();
        recyclerview.setAdapter(mAdapter);
    }

    public class MyAdapterList extends XRecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_filter_pop_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            BaseListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getName());
            viewHolder.list_item.setTag(data);
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
            @BindView(R.id.text_name)
            public TextView text_name;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                BaseListData data = (BaseListData) v.getTag();
                if(listener != null){
                    listener.onTypeChoose(data);
                }
                dismiss();
            }


        }
    }

    public interface FilterStatusListener{
        abstract void onTypeChoose(BaseListData type);
    }
}