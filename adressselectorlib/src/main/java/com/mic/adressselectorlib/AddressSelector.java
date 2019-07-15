package com.mic.adressselectorlib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/9.
 * Description:收货地址选择器
 */
public class AddressSelector extends LinearLayout implements View.OnClickListener{
    private int TextSelectedColor = getResources().getColor(R.color.blue);
    private int TextEmptyColor = Color.parseColor("#333333");
    //顶部的tab集合
    private ArrayList<Tab> tabs;
    //列表的适配器  一行一个
    private AddressAdapter addressAdapter;
    //列表的适配器   一行2个
    private AddressAdapterTwo addressAdapterTwo;
    //列表的适配器   一行多选
    private addressAdapterThree addressAdapterThree;
    private ArrayList<City> cities;
    private ArrayList<City> selectedCityList;
    private OnItemClickListener onItemClickListener;
    private OnTabSelectedListener onTabSelectedListener;
    private RecyclerView list;
    private RecyclerView grid;
    private LinearLayout emptyLayout;
    private Button okBtn;
    private LinearLayout right_layout;
    private LinearLayout top_layout;
    private LinearLayout top_top_layout;
    //tabs的外层layout
    private LinearLayout tabs_layout;
    private LinearLayout list_layout;
    private TextView mAddressTv;
    private ImageView topImg;
    //会移动的横线布局
    private Line line;
    private Context mContext;
    //总共tab的数量
    private int tabAmount = 3;
    //当前tab的位置
    private int tabIndex = 0;
    //分隔线
    private View grayLine;
    //列表文字大小
    private int listTextSize = -1;
    //列表文字颜色
    private int listTextNormalColor = Color.parseColor("#333333");
    //列表文字选中的颜色
    private int listTextSelectedColor = getResources().getColor(R.color.blue);
    //列表icon资源
    private int listItemIcon = -1;
    public AddressSelector(Context context) {
        super(context);
        init(context);
    }

    public AddressSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        removeAllViews();
        this.mContext = context;
        setOrientation(VERTICAL);
//        setBackgroundColor(R.color.transparent);


        top_top_layout = new LinearLayout(mContext);
        top_top_layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        top_top_layout.setOrientation(VERTICAL);
        top_top_layout.setBackgroundResource(R.color.base_grey);
        addView(top_top_layout);

        top_layout = new LinearLayout(mContext);
        LayoutParams topParams = new LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        topParams.setMargins(20,50,20,50);
        top_layout.setLayoutParams(topParams);
        top_layout.setOrientation(HORIZONTAL);
        top_layout.setBackgroundResource(R.drawable.ll_white);
        top_top_layout.addView(top_layout);


        topImg = new ImageView(mContext);
//        topImg.setBackgroundResource(R.mipmap.tab_icon_station);
        LayoutParams imgParams = new LayoutParams(150,ViewGroup.LayoutParams.WRAP_CONTENT);
        imgParams.setMargins(20,50,50,50);
        topImg.setLayoutParams(imgParams);
//        imageView.setPadding(400,140,40,0);
        top_layout.addView(topImg);

        right_layout = new LinearLayout(mContext);
        right_layout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,1));
        right_layout.setOrientation(VERTICAL);
        top_layout.addView(right_layout);

        tabs_layout = new LinearLayout(mContext);
        tabs_layout.setWeightSum(tabAmount);
        tabs_layout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        tabs_layout.setOrientation(HORIZONTAL);
//        addView(tabs_layout);
        right_layout.addView(tabs_layout);
        tabs = new ArrayList<>();

        Tab tab = newTab("请选择",true);
        tabs_layout.addView(tab);
        tabs.add(tab);
        for(int i = 1;i<tabAmount;i++){
            Tab _tab = newTab("",false);
            _tab.setIndex(i);
            tabs_layout.addView(_tab);
            tabs.add(_tab);
        }
        line = new Line(mContext);
        line.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,6));
        line.setSum(tabAmount);
//        addView(line);
        right_layout.addView(line);

//        grayLine = new View(mContext);
//        grayLine.setLayoutParams(new LayoutParams(
//                LayoutParams.MATCH_PARENT,2));
//        grayLine.setBackgroundColor(mContext.getResources().getColor(R.color.line_DDDDDD));
//        addView(grayLine);

        mAddressTv = new TextView(mContext);
        LayoutParams tvAddressParams = new LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        tvAddressParams.setMargins(20,0,20,50);
        mAddressTv.setLayoutParams(tvAddressParams);
        mAddressTv.setText("选择镇/街道");
        mAddressTv.setTextSize(10);
        mAddressTv.setTextColor(getResources().getColor(R.color.base_text_grey));
        mAddressTv.setBackgroundResource(R.color.base_grey);
        top_top_layout.addView(mAddressTv);


        list_layout = new LinearLayout(mContext);
        list_layout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        list_layout.setOrientation(VERTICAL);
        list_layout.setBackgroundResource(R.color.base_grey);
        addView(list_layout);



        list = new RecyclerView(mContext);
        list.setLayoutParams(new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setBackgroundResource(R.color.white);
        list_layout.addView(list);
        grid = new RecyclerView(mContext);
//        grid.setLayoutParams(new ViewGroup.LayoutParams(
//                LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,1);
        grid.setLayoutParams(params);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        grid.setLayoutManager(gridLayoutManager);
        grid.setVisibility(GONE);
//        addView(grid);
        list_layout.addView(grid);



        LinearLayout.LayoutParams emptyParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,1);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        emptyLayout = (LinearLayout) inflater.inflate(R.layout.base_refresh_view,null);
        emptyLayout.setLayoutParams(emptyParams);
        emptyLayout.setVisibility(GONE);
        list_layout.addView(emptyLayout);

        okBtn = new Button(mContext);
        okBtn.setLayoutParams(new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        okBtn.setPadding(0,40,0,40);
        okBtn.setText("确定");
        okBtn.setBackgroundResource(R.color.base_blue_pressed);
        okBtn.setTextColor(mContext.getResources().getColor(R.color.white));
        okBtn.setTextSize(16);
        okBtn.setVisibility(GONE);
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    try {
                        onItemClickListener.itemClick(AddressSelector.this,selectedCityList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        list_layout.addView(okBtn);
    }
    /**
     * 得到一个新的tab对象
     * */
    private Tab newTab(CharSequence text,boolean isSelected){
        Tab tab = new Tab(mContext);
        tab.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,1));
        tab.setGravity(Gravity.CENTER);
        tab.setPadding(0,40,0,40);
        tab.setSelected(isSelected);
        tab.setText(text);
        tab.setTextEmptyColor(TextEmptyColor);
        tab.setTextSelectedColor(TextSelectedColor);
        tab.setOnClickListener(this);
        return tab;
    }
    /**
     * 设置tab的数量，默认3个,不小于2个
     * @param tabAmount tab的数量
     * */
    public void setTabAmount(int tabAmount) {
        if(tabAmount >= 2){
            this.tabAmount = tabAmount;
            init(mContext);
        }
        else
            throw new RuntimeException("AddressSelector tabAmount can not less-than 2 !");
    }
    /**
     * 设置列表的点击事件回调接口
     * */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**
     * 设置列表的数据源，设置后立即生效   一行一个
     * */
    public void setCities(ArrayList cities) {
        if(cities == null||cities.size() <= 0){
            Toast.makeText(mContext,"没数据",Toast.LENGTH_LONG).show();
            list.setVisibility(GONE);
            emptyLayout.setVisibility(VISIBLE);
            return;
        }
        emptyLayout.setVisibility(GONE);
        list.setVisibility(VISIBLE);
        grid.setVisibility(GONE);
        mAddressTv.setText("选择镇/街道");
        if(cities.get(0) instanceof CityInterface){
            this.cities = cities;
            if(addressAdapter == null){
                addressAdapter = new AddressAdapter();
                list.setAdapter(addressAdapter);
            }
            addressAdapter.notifyDataSetChanged();
        }else{
            throw new RuntimeException("AddressSelector cities must implements CityInterface");
        }
    }

    /**
     * 设置列表的数据源，设置后立即生效   一行2个
     * */
    public void setCitiesTwo(ArrayList cities) {
        if(cities == null||cities.size() <= 0)
            return;
        list.setVisibility(GONE);
        grid.setVisibility(VISIBLE);
        mAddressTv.setText("选择站点");
        if(cities.get(0) instanceof CityInterface){
            this.cities = cities;
            if(addressAdapterTwo == null){
                addressAdapterTwo = new AddressAdapterTwo();
                grid.setAdapter(addressAdapterTwo);
            }
            addressAdapterTwo.notifyDataSetChanged();
        }else{
            throw new RuntimeException("AddressSelector cities must implements CityInterface");
        }
    }

    /**
     * 设置列表的数据源，设置后立即生效
     * */
    public void setCitiesThree(ArrayList cities) {
        if(cities == null||cities.size() <= 0)
            return;
        list.setVisibility(GONE);
        grid.setVisibility(VISIBLE);
        mAddressTv.setText("选择站点");
        okBtn.setVisibility(VISIBLE);
        if(cities.get(0) instanceof CityInterface){
            this.cities = cities;
            selectedCityList = new ArrayList<>();
            if(addressAdapterThree == null){
                addressAdapterThree = new addressAdapterThree();
                grid.setAdapter(addressAdapterThree);
            }
            addressAdapterThree.notifyDataSetChanged();
        }else{
            throw new RuntimeException("AddressSelector cities must implements CityInterface");
        }
    }

    /**
     * 设置顶部tab的点击事件回调
     * */
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    @Override
    public void onClick(View v) {
        Tab tab = (Tab) v;
        //如果点击的tab大于index则直接跳出
        if(tab.index > tabIndex)
            return;
        tabIndex = tab.index;
        if(onTabSelectedListener != null){
            if(tab.isSelected)
                onTabSelectedListener.onTabReselected(AddressSelector.this,tab);
            else
                onTabSelectedListener.onTabSelected(AddressSelector.this,tab);
        }
        resetAllTabs(tabIndex);
        line.setIndex(tabIndex);
        tab.setSelected(true);
    }

    private void resetAllTabs(int tabIndex){
        if(tabs != null)
            for(int i =0;i<tabs.size();i++){
                tabs.get(i).resetState();
                if(i > tabIndex){
                    tabs.get(i).setText("");
                }
            }
    }
    /**
     * 设置Tab文字选中的颜色
     * */
    public void setTextSelectedColor(int textSelectedColor) {
        TextSelectedColor = textSelectedColor;
    }
    /**
     * 设置Tab文字默认颜色
     * */
    public void setTextEmptyColor(int textEmptyColor) {
        TextEmptyColor = textEmptyColor;
    }
    /**
     * 设置Tab横线的颜色
     * */
    public void setLineColor(int lineColor) {
        line.setSelectedColor(lineColor);
    }
    /**
     * 设置tab下方分隔线的颜色
     * */
    public void setGrayLineColor(int grayLineColor) {
        grayLine.setBackgroundColor(grayLineColor);
    }
    /**
     * 设置列表文字大小
     * */
    public void setListTextSize(int listTextSize) {
        this.listTextSize = listTextSize;
    }
    /**
     * 设置列表文字颜色
     * */
    public void setListTextNormalColor(int listTextNormalColor) {
        this.listTextNormalColor = listTextNormalColor;
    }
    /**
     * 设置列表选中文字颜色
     * */
    public void setListTextSelectedColor(int listTextSelectedColor) {
        this.listTextSelectedColor = listTextSelectedColor;
    }
    /**
     * 设置列表icon资源
     * */
    public void setListItemIcon(int listItemIcon) {
        this.listItemIcon = listItemIcon;
    }

    /**
     * 标签控件
     * */
    public class Tab extends TextView{
        private int index = 0;
        private int TextSelectedColor =  getResources().getColor(R.color.blue);
        private int TextEmptyColor = Color.parseColor("#333333");
        /**
         * 是否选中状态
         * */
        private boolean isSelected = false;
        public Tab(Context context) {
            super(context);
            init();
        }

        public Tab(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public Tab(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }
        private void init(){
            setTextSize(15);
        }
        @Override
        public void setText(CharSequence text, BufferType type) {
            if(isSelected)
                setTextColor(TextSelectedColor);
            else
                setTextColor(TextEmptyColor);
            super.setText(text, type);
        }

        @Override
        public void setSelected(boolean selected) {
            isSelected = selected;
            setText(getText());
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
        public void resetState(){
            isSelected = false;
            setText(getText());
        }

        public void setTextSelectedColor(int textSelectedColor) {
            TextSelectedColor = textSelectedColor;
        }

        public void setTextEmptyColor(int textEmptyColor) {
            TextEmptyColor = textEmptyColor;
        }
    }
    /**
     * 横线控件
     * */
    private class Line extends LinearLayout{
        private int sum = 3;
        private int oldIndex = 0;
        private int nowIndex = 0;
        private View indicator;
        private int SelectedColor = getResources().getColor(R.color.blue);
        public Line(Context context) {
            super(context);
            init(context);
        }

        public Line(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public Line(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }
        private void init(Context context){
            setOrientation(HORIZONTAL);
            setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,6));
            setWeightSum(tabAmount);
            indicator= new View(context);
            indicator.setLayoutParams(new LayoutParams(0,LayoutParams.MATCH_PARENT,1));
            indicator.setBackgroundColor(SelectedColor);
            addView(indicator);
        }
        public void setIndex(int index){
            int onceWidth =getWidth()/sum;
            this.nowIndex = index;
            ObjectAnimator animator = ObjectAnimator.ofFloat(indicator, "translationX", indicator.getTranslationX(), (nowIndex-oldIndex)*onceWidth);
            animator.setDuration(300);
            animator.start();
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setSelectedColor(int selectedColor) {
            SelectedColor = selectedColor;
        }
    }

    public void setTopImg(int resid){
        topImg.setBackgroundResource(resid);
    }

    private class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder =
                    new MyViewHolder(LayoutInflater.from(mContext).inflate(
                            R.layout.item_address,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            if(listItemIcon != -1)
                holder.img.setImageResource(listItemIcon);
            if(listTextSize != -1)
                holder.tv.setTextSize(listTextSize);
            if(TextUtils.equals(tabs.get(tabIndex).getText(),cities.get(position).getName())){
                holder.img.setVisibility(View.VISIBLE);
                holder.tv.setTextColor(listTextSelectedColor);
            }else{
                holder.img.setVisibility(View.INVISIBLE);
                holder.tv.setTextColor(listTextNormalColor);
            }
            holder.tv.setText(cities.get(position).getName());
            holder.itemView.setTag(cities.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        try {
                            onItemClickListener.itemClick(AddressSelector.this,(City) v.getTag(),position,tabIndex);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tabs.get(tabIndex).setText(((CityInterface) v.getTag()).getCityName());
                        tabs.get(tabIndex).setTag(v.getTag());
                        if(tabIndex+1 < tabs.size()){
                            tabIndex ++;
                            resetAllTabs(tabIndex);
                            line.setIndex(tabIndex);
                            tabs.get(tabIndex).setText("请选择");
                            tabs.get(tabIndex).setSelected(true);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;
            public ImageView img;
            public View itemView;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tv = (TextView) itemView.findViewById(R.id.item_address_tv);
                img = (ImageView) itemView.findViewById(R.id.item_address_img);
            }
        }
    }

    private class AddressAdapterTwo extends RecyclerView.Adapter<AddressAdapterTwo.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                    R.layout.grid_list_item,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            if(listTextSize != -1)
                holder.tv.setTextSize(listTextSize);
            if(TextUtils.equals(tabs.get(tabIndex).getText(),cities.get(position).getName())){
                holder.tv.setTextColor(listTextSelectedColor);
            }else{
                holder.tv.setTextColor(listTextNormalColor);
            }
            holder.tv.setText(cities.get(position).getName());
            holder.itemView.setTag(cities.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        try {
                            onItemClickListener.itemClick(AddressSelector.this,(City) v.getTag(),position,tabIndex+1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;
            public View itemView;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tv = (TextView) itemView.findViewById(R.id.item_address_tv);
            }
        }
    }


    private class addressAdapterThree extends RecyclerView.Adapter<addressAdapterThree.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                    R.layout.grid_list_item,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(listItemIcon != -1)
                holder.img.setImageResource(listItemIcon);
            if(listTextSize != -1)
                holder.tv.setTextSize(listTextSize);

            if(selectedCityList.contains(cities.get(position))){
                holder.img.setVisibility(View.VISIBLE);
                holder.tv.setTextColor(listTextSelectedColor);
            }else {
                holder.img.setVisibility(View.INVISIBLE);
                holder.tv.setTextColor(listTextNormalColor);
            }
            holder.tv.setText(cities.get(position).getName());
            holder.itemView.setTag(cities.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedCityList.contains((City) v.getTag())){
                        selectedCityList.remove((City) v.getTag());
                    }else {
                        selectedCityList.add((City) v.getTag());
                    }
                    addressAdapterThree.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;
            public ImageView img;
            public View itemView;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tv = (TextView) itemView.findViewById(R.id.item_address_tv);
                img = (ImageView) itemView.findViewById(R.id.img_check);
            }
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(AddressSelector addressSelector,Tab tab);
        void onTabReselected(AddressSelector addressSelector,Tab tab);
    }
}
