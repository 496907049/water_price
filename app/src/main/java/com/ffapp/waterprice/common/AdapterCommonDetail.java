package com.ffapp.waterprice.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterCommonDetail extends RecyclerView.Adapter<AdapterCommonDetail.ViewHolder> {

    BasisActivity mContext;
    BaseListDataListBean mListBean;
    OnDetailItemListener mListener;


    boolean isTopPadding = false;


        public AdapterCommonDetail(BasisActivity context, BaseListDataListBean listBean) {
            mContext = context;
            mListBean = listBean;
        }
        public AdapterCommonDetail(BasisActivity context, BaseListDataListBean listBean,boolean isTopPadding) {
            mContext = context;
            mListBean = listBean;
            this.isTopPadding = isTopPadding;
        }

        public void setListener( OnDetailItemListener listener){
            this.mListener = listener;
        }

        public void setData(BaseListDataListBean listBean){
            mListBean = listBean;
            notifyDataSetChanged();
        }


    //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.common_detail_list_item, viewGroup, false);
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
            @BindView(R.id.text_name)
            public TextView text_name;
            @BindView(R.id.text_value)
            public TextView text_value;
            @BindView(R.id.img_tag)
            public ImageView img_tag;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position){
                BaseListData data = mListBean.getList().get(position);
                text_name.setText(data.getName());
                text_value.setText(data.getId());
                list_item.setTag(position);

                if(data.getResid() != -1 &&  data.getResid() != 0){
                    text_value.setTextColor(data.getResid());
                }else {
                    text_value.setTextColor(mContext.getResources().getColor(R.color.base_text_black));
                }

                if(data.isClick()){
                    img_tag.setVisibility(View.VISIBLE);
                }else {
                    img_tag.setVisibility(View.GONE);
                }
//                if(!isTopPadding && position == 0){
//                    Resources res = mContext.getResources();
//                            list_item.setPadding(res.getDimensionPixelSize(R.dimen.base_padding_mid),0,res.getDimensionPixelSize(R.dimen.base_padding_mid),res.getDimensionPixelSize(R.dimen.base_padding_xsmall));
//                }
            }

            @OnClick(R.id.list_item)
            void onItemClick(View v){
                if(mListener == null)return;
                int position = (int) v.getTag();
                BaseListData data = mListBean.getList().get(position);
                if(data.isClick()){
                    mListener.onDetailItemClick(data);
                }
            }
        }

    public interface OnDetailItemListener{
        abstract void onDetailItemClick(BaseListData data);
    }
    }