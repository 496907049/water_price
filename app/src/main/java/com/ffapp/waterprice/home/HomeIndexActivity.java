package com.ffapp.waterprice.home;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BasisBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 首页
 */
public class HomeIndexActivity extends HomeBaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.scroll_view)
    NestedScrollView scroll_view;


    @Override
    public void initViews() {
//        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.home_index_activity);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        recyclerview.setLayoutManager(layoutManager);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    void getData(){
        RequestParams params = new RequestParams();
        params.put("token", LoginBean.getUserToken());
        params.put("type", 1);

        HttpRestClient.get(Constants.URL_HOME_BLOCK_LIST, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
//                mBlockListBean = (BlockListBean) result;
            }

            @Override
            public void onFinish(int httpWhat) {

            }
        },0, BasisBean.class);

    }



//    public class MyAdapterListEnter extends RecyclerView.Adapter<MyAdapterListEnter.ViewHolder> {
//
//        public MyAdapterListEnter() {
//
//        }
//
//        //创建新View，被LayoutManager所调用
//        @Override
//        public MyAdapterListEnter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_grid_enter_item, viewGroup, false);
//            return new MyAdapterListEnter.ViewHolder(view);
//        }
//
//
//        //将数据与界面进行绑定的操作
//        @Override
//        public void onBindViewHolder(MyAdapterListEnter.ViewHolder viewHolder, int position) {
//            viewHolder.bind(position);
//        }
//
//        //获取数据的数量
//        @Override
//        public int getItemCount() {
//            return listEnter == null ? 0 : listEnter.getList().size();
//        }
//
//        //自定义的ViewHolder，持有每个Item的的所有界面元素
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            @BindView(R.id.list_item)
//            public View list_item;
//            @BindView(R.id.text_name)
//            public TextView text_name;
//            @BindView(R.id.img_icon)
//            public ImageView img_icon;
//
//            public ViewHolder(View view) {
//                super(view);
//                ButterKnife.bind(this, view);
//            }
//
//            public void bind(int position){
//                BaseListData data = listEnter.getList().get(position);
////                list_item.setTag(position);
//                text_name.setText(data.getName());
//                img_icon.setImageResource(data.getResid());
//
//                list_item.setTag(position);
//
//            }
//
//            @OnClick(R.id.list_item)
//            public void viewDetail(View v) {
//                int position =  (int) v.getTag();
//                BaseListData data = listEnter.getList().get(position);
//                switch (data.getName()){
//                    case "自动灌溉":
//                        ActivityTool.skipActivity(mContext, AutoWateringBaseActivity.class);
//                        break;
//                    case "手动灌溉":
//                        ActivityTool.skipActivity(mContext, ManualBaseActivity.class);
//                        break;
//                    case "灌溉报告":
//                        ActivityTool.skipActivity(mContext, WateringReportActivity.class);
//                        break;
//                    case "土壤墒情":
//                        SoilListActivity.newInstant(mContext,null);
//                        break;
//                }
//            }
//        }
//    }

}
