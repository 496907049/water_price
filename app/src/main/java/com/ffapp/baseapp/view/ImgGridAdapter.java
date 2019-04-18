package com.ffapp.baseapp.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisApp;
import com.ffapp.baseapp.other.PhotoScanActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImgGridAdapter extends RecyclerView.Adapter<ImgGridAdapter.ViewHolder> {


    Activity mContext;
    ArrayList<String> mListBean;

    public ImgGridAdapter(Activity mContext, ArrayList<String> data) {

        this.mContext = mContext;
        this.mListBean = data;
    }

    public void setData(ArrayList<String> data) {
        this.mListBean = data;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
                String  data =mListBean.get(position);
        viewHolder.img_delete.setVisibility(View.INVISIBLE);

        BasisApp.loadImg(mContext,data,viewHolder.img_icon,R.drawable.base_img_default_square);
        viewHolder.list_item.setTag(position);
//            viewHolder.img_icon.setTag(R.id.img_tag, position);

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mListBean == null ? 0 : (mListBean.size());
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item)
        public View list_item;
        @BindView(R.id.img_icon)
        public ImageView img_icon;
        @BindView(R.id.img_delete)
        public ImageView img_delete;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.list_item)
        public void onItemClick(View v) {
                    int data = (int) v.getTag();
//                NewsTextDetailActivity.toDetail(mContext,data.getArticle_id());
//                ActivityTool.skipActivity(mContext, ScaningActivity.class);
            PhotoScanActivity.toImgScanActivityt(mContext, mListBean, data);
        }
    }
}