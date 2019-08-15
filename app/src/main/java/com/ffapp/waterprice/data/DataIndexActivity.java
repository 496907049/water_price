package com.ffapp.waterprice.data;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.home.HomeBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataIndexActivity extends HomeBaseActivity {


    private BaseListDataListBean listEnter;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.data_index_activity);
        setTitle("数据分析");
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        listEnter = new BaseListDataListBean();

        listEnter.getList().add(new BaseListData("","流量分析",R.mipmap.data_img_flow,0));
        listEnter.getList().add(new BaseListData("","用水户分析",R.mipmap.data_img_user,0));
        listEnter.getList().add(new BaseListData("","土壤墒情分析",R.mipmap.data_img_soil,0));
        listEnter.getList().add(new BaseListData("","环境分析",R.mipmap.data_img_environment,0));
        listEnter.getList().add(new BaseListData("","降雨量分析",R.mipmap.data_img_rain,0));
//                listEnter.getList().add(new BaseListData("","泵房分析",R.mipmap.data_img_pumproom,0));
        listEnter.getList().add(new BaseListData("","报警分析",R.mipmap.data_img_alarm,0));
        recycler.setAdapter(new MyAdapterListEnter());
    }

    public class MyAdapterListEnter extends RecyclerView.Adapter<MyAdapterListEnter.ViewHolder> {

        public MyAdapterListEnter() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapterListEnter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_index_list_item, viewGroup, false);
            return new MyAdapterListEnter.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterListEnter.ViewHolder viewHolder, int position) {
            viewHolder.bind(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return listEnter == null ? 0 : listEnter.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.img_icon)
            public ImageView img_icon;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position){
                BaseListData data = listEnter.getList().get(position);
                img_icon.setImageResource(data.getResid());
                list_item.setTag(data);

            }

            @OnClick(R.id.list_item)
            public void viewDetail(View v) {
                BaseListData data = (BaseListData) v.getTag();
                switch (data.getName()){
                    case "流量分析":
                    DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_FLOW);
                        break;
                    case "用水户分析":
                        DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_WATER);
                        break;
                    case "土壤墒情分析":
                        DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_SOIL);
                        break;
                    case "环境分析":
                        DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_MOTEOROLOGY);
                        break;
                    case "降雨量分析":
                        DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_RAIN);
                        break;
                    case "报警分析":
                        DataAnalysisActivity.newInstant(mContext,data.getName(),Constants.URL_WARNING);
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getImmersionBar().statusBarDarkFont(true,0.5f);
        getImmersionBar().statusBarDarkFont(true).init();
    }
}
