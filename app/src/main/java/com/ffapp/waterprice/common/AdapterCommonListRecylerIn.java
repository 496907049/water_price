package com.ffapp.waterprice.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCommonListRecylerIn extends RecyclerView.Adapter<AdapterCommonListRecylerIn.ViewHolder> {

    BasisActivity mContext;
    BaseListDataListBean mListBean;

    int minWidth;


        public AdapterCommonListRecylerIn(BasisActivity context, BaseListDataListBean listBean) {
            mContext = context;
            mListBean = listBean;
        }

        public void setData( BaseListDataListBean listBean){
            this.mListBean = listBean;
            notifyDataSetChanged();
        }

        public void setTitleMinwidth( int width){
            minWidth = width;
        }


    //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.common_list_recyler_in_list_item, viewGroup, false);
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
            @BindView(R.id.text_name)
            public TextView text_name;
            @BindView(R.id.text_value)
            public TextView text_value;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                if(minWidth != 0){
                    text_name.setMinWidth(minWidth);
                }

            }

            public void bind(int position){
                BaseListData data = mListBean.getList().get(position);
                text_name.setText(data.getName());
                text_value.setText(data.getId());
                if(data.getResid() != -1 &&  data.getResid() != 0){
                    text_value.setTextColor(data.getResid());
                }else {
                    text_value.setTextColor(mContext.getResources().getColor(R.color.base_text_black));
                }

            }
        }
    }