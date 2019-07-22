package com.ffapp.waterprice.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffapp.waterprice.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsMarkerView extends MarkerView {


    private RecyclerView mRecyclerView;
    private TextView mTimeTv;

    private Context mContext;
    private MyAdapterList mAdapter;

    /**
     * 在构造方法里面传入自己的布局以及实例化控件
     * @param context 上下文
     * @param  context 自己的布局
     */
    public DetailsMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        this.mContext = context;
        mRecyclerView = findViewById(R.id.recyclerview);
        mTimeTv = findViewById(R.id.tv_time);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    //每次重绘，会调用此方法刷新数据
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        try {
            Entry en = e;
            LineChart lineChart = (LineChart) getChartView();
            LineData data = lineChart.getLineData();

            mAdapter = new MyAdapterList(data.getDataSets(),e.getX());
            mRecyclerView.setAdapter(mAdapter);
//            if (e.getY() == 0) {
//                mTvChart1.setText("暂无数据");
//            } else {
//                mTvChart1.setText(concat(e.getY(), "支出："));
//            }
//            mTvMonth.setText(String.valueOf((int) e.getX() + 1).concat("月"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }

    //布局的偏移量。就是布局显示在圆点的那个位置
    // -(width / 2) 布局水平居中
    //-(height) 布局显示在圆点上方
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "元";
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        private float x;
        private List<ILineDataSet> dataSet;

        public MyAdapterList() {

        }

        public MyAdapterList(List<ILineDataSet> dataSet, float x) {
             this.x = x;
             this.dataSet = dataSet;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapterList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_marker_view, viewGroup, false);
            return new MyAdapterList.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterList.ViewHolder viewHolder, int position) {
            viewHolder.bind(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return dataSet == null ? 0 : dataSet.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_num)
            public TextView mNumTv;
            @BindView(R.id.tv_type)
            public TextView mTypeTv;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
            public void bind(int position) {
                List<Entry> a = dataSet.get(position).getEntriesForXValue(x);
                ILineDataSet data = dataSet.get(position);
                mNumTv.setText(""+a.get(0).getY());
                mTypeTv.setText(""+data.getLabel()+":");
            }

        }
    }

}
