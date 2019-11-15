package com.ffapp.waterprice.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.bean.BannerListBean;
import com.ffapp.waterprice.bean.BannerListData;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.home.HomeBaseActivity;
import com.ffapp.waterprice.manage.maintain.MaintainListActivity;
import com.ffapp.waterprice.manage.patrol.PatrolListActivity;
import com.ffapp.waterprice.manage.todo.TodoListActivity;
import com.ffapp.waterprice.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.SystemParamsUtils;

/**
 * 预警监测首页
 */
public class ManageIndexActivity extends HomeBaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview_enter;
    private AdapterEnter mAdapter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    BannerListBean mBannerList;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.manage_index_activity);

        recyclerview_enter.setLayoutManager(new GridLayoutManager(mContext, 4));

        swipeRefreshLayout.setColorSchemeResources(R.color.base_blue, R.color.base_text_green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        BaseListDataListBean listEnter = new BaseListDataListBean();
        listEnter.getList().add(new BaseListData("", "待办任务", R.drawable.alarm_icon_enter_monitor, 0));
        listEnter.getList().add(new BaseListData("", "巡检管理", R.drawable.alarm_icon_enter_alarm, 0));
        listEnter.getList().add(new BaseListData("", "维护管理", R.drawable.alarm_icon_enter_manage, 0));
        listEnter.getList().add(new BaseListData("", "巡查轨迹", R.drawable.alarm_icon_enter_operate_manage, 0));
        recyclerview_enter.setAdapter(new AdapterEnter(listEnter));

        swipeRefreshLayout.setRefreshing(true);
        refreshData();
    }


    void refreshData() {
//        getNews();
        getBanner();

    }

    private void getBanner() {

        mBannerList = new BannerListBean();
        for (int i = 0; i < 5; i++) {
            BannerListData bannerListData = new BannerListData();
//            bannerListData.setImage_url("http://img0.imgtn.bdimg.com/it/u=3215222857,1774393785&fm=26&gp=0.jpg");
            bannerListData.setImage_url(R.drawable.test_alarm_img_banner);
            mBannerList.getList().add(bannerListData);
        }
        setBannerView();
        swipeRefreshLayout.setRefreshing(false);
    }

    void setBannerView() {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = SystemParamsUtils.getScreenWidth() * 40 / 75;
        banner.setLayoutParams(params);
        ArrayList<Object> listImgs = new ArrayList<>();
        ArrayList<String> listTitles = new ArrayList<>();

        for (int i = 0; i < mBannerList.getList().size(); i++) {
//            listImgs.add("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
//            listImgs.add(mBannerList.getList().get(i).getImage_url75x30());
            listImgs.add(mBannerList.getList().get(i).getImage_url());
            listTitles.add(mBannerList.getList().get(i).getTitle());
        }
//        if (listBanner == null) {
//            listImgs = new ArrayList<>();
//            listTitles = new ArrayList<>();
//        } else {
//            listImgs = listBanner.getListImage();
//            listTitles = listBanner.getListTitle();
//        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader(R.drawable.base_img_default_square));
        //设置图片集合
        banner.setImages(listImgs);
        //设置banner动画效果
//        banner.setBannerAnimation(getRandomTransfer());
//        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(listTitles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerListData data = mBannerList.getList().get(position);
//                data.open(mContext);
            }
        });
    }

    public class AdapterEnter extends RecyclerView.Adapter<AdapterEnter.ViewHolder> {

//        BasisActivity mContext;

        BaseListDataListBean mListBean;

        public AdapterEnter(BaseListDataListBean mListBean) {
            this.mListBean = mListBean;
        }


        //创建新View，被LayoutManager所调用
        @Override
        public AdapterEnter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.manage_enter_grid_item, viewGroup, false);
            return new AdapterEnter.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(AdapterEnter.ViewHolder viewHolder, int position) {
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
            @BindView(R.id.img_icon)
            public ImageView img_icon;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                BaseListData data = mListBean.getList().get(position);
                text_name.setText(data.getName());
                img_icon.setImageResource(data.getResid());
                list_item.setTag(position);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                int position = (int) v.getTag();
                BaseListData data = mListBean.getList().get(position);
                switch (data.getName()) {
                    case "待办任务":
                        ActivityTool.skipActivity(mContext, TodoListActivity.class);
                        break;
                    case "巡检管理":
                        ActivityTool.skipActivity(mContext, PatrolListActivity.class);
                        break;
                    case "维护管理":
                        ActivityTool.skipActivity(mContext, MaintainListActivity.class);
                        break;
                    case "巡查轨迹":
                        ActivityTool.skipActivity(mContext, MapTianActivity.class);
                        break;
                    default:
                        showToast(R.string.app_building);
                        break;
                }
            }
        }
    }


}
