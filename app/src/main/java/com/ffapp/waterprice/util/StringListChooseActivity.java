package com.ffapp.waterprice.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;

/**
 * 字符串选择
 * */
public class StringListChooseActivity extends BasisActivity {


    public static void toStringListChoose(Activity mContext,String title,ArrayList<String> list,int requestcode){

        Bundle extras = new Bundle();
        extras.putStringArrayList("list",list);
        extras.putString("title",title);
        ActivityTool.skipActivityForResult(mContext,StringListChooseActivity.class,extras,requestcode);
    }


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private ArrayList<String> mListBean;

  int current_position = -1;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.base_recycler_with_top);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));

        setTitle("");
        setTitleLeftButton(null);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        mListBean = extras.getStringArrayList("list");
        current_position = extras.getInt("position",-1);
        String title = extras.getString("title");
        if(!TextUtils.isEmpty(title)){
            setTitle(title);
        }
        setListView();
    }

    private void setListView() {
        if (mAdapter == null) {
            mAdapter = new MyAdapterList();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }




    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

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
            String data = mListBean.get(position);
            viewHolder.text_name.setText(data);

            viewHolder.list_item.setTag(position);


        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean == null ? 0 : mListBean.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.text_name)
            public TextView text_name;
            @BindView(R.id.img_icon)
            public ImageView img_icon;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

            }

            @OnClick(R.id.list_item)
            void onItemClick(View v){
                int position = (int) v.getTag();
                String value = mListBean.get(position);
                Intent data = new Intent();
                data.putExtra("position",position);
                data.putExtra("data",value);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        }


    }

}
