package com.ffapp.waterprice.bean;

import android.content.Context;
import android.graphics.Color;
import com.ffapp.waterprice.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.LogUtil;

public class ChartInfoBean extends BasisBean {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static float LEGEND_FRONT_SIZE = 9f;

    /**
     * title : 温度折线图
     * type : line
     * legend : ["T1","T2","T3","T4","T5","T6","T7","T8"]
     * category : ["2018-09-22","2018-09-23","2018-09-24","2018-09-25","2018-09-26","2018-09-27","2018-09-28"]
     * data : [{"name":"T1","value":[0,0,0,0,0,0,0]},{"name":"T2","value":[0,0,0,0,0,0,0]},{"name":"T3","value":[0,0,0,0,0,0,0]},{"name":"T4","value":[0,0,0,0,0,0,0]},{"name":"T5","value":[0,0,0,0,0,0,0]},{"name":"T6","value":[0,0,0,0,0,0,0]},{"name":"T7","value":[0,0,0,0,0,0,0]},{"name":"T8","value":[0,0,0,0,0,0,0]}]
     */

    private String title;
    private String type;
    private List<String> legend;
    private List<String> category;
    private List<DataBean> data;

    private int resLineColor = 0;
    private int resLineGradient = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<DataBean> getData() {
        return data;
    }

    public float getDataMaxValue() {
        if (data == null || data.size() == 0) return 0;
        float max = 0;
        for (DataBean dataintem : data) {
            for (float valueitem : dataintem.getValue()) {
                if (valueitem > max) {
                    max = valueitem;
                }
            }
        }
        return max;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : T1
         * value : [0,0,0,0,0,0,0]
         */

        private String name;
        private List<Float> value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Float> getValue() {
            return value;
        }

        public void setValue(List<Float> value) {
            this.value = value;
        }
    }


    public void setChartLine(Context mContext, LineChart chartLine) {
//        chartLine.setOnChartGestureListener(this);
//        chartLine.setOnChartValueSelectedListener(this);

        //设置描述文本不显示
        chartLine.getDescription().setEnabled(false);
        //设置是否显示表格背景
        chartLine.setDrawGridBackground(false);
        //设置是否可以触摸
        chartLine.setTouchEnabled(true);
        chartLine.setDragDecelerationFrictionCoef(0.9f);
        //设置是否可以拖拽
        chartLine.setDragEnabled(true);
        //设置是否可以缩放
        chartLine.setScaleEnabled(true);
        chartLine.setHighlightPerDragEnabled(true);

        // chartLine.setScaleXEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chartLine.setPinchZoom(false);
        chartLine.setDrawBorders(false);
        chartLine.setDrawMarkers(true);
        // set an alternative background color
        // chartLine.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(chartLine); // For bounds control
//        chartLine.setMarker(mv); // Set the marker to the chart


        XAxis xAxis = chartLine.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 10f);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMaximum(data.get(0).getValue().size());
        xAxis.setAxisMinimum(0);
        final Calendar calendar = Calendar.getInstance();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getCategory()));
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));
        xAxis.setTextSize(7f);
        xAxis.setAxisLineColor(mContext.getResources().getColor(R.color.char_line_color_orange));
        //一个界面显示6个Lable，那么这里要设置11个
        xAxis.setLabelCount(4);

        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        YAxis leftAxis = chartLine.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMaximum(40);
        leftAxis.setAxisMinimum(0);
        float maxvalue = getDataMaxValue();
        leftAxis.setAxisMaximum(maxvalue);
        LogUtil.i("getDataMaxValue--->"+getDataMaxValue());
        if(maxvalue > 1){
            leftAxis.setGranularity(1);
        }else {
            leftAxis.setGranularity(0.1f);
        }

        leftAxis.setDrawLabels(true);
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//            }
//        });
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));

        chartLine.getAxisRight().setEnabled(false);
        //chartLine.getViewPortHandler().setMaximumScaleY(2f);
        //chartLine.getViewPortHandler().setMaximumScaleX(2f);
        // add data
        ArrayList<Integer> listColor = new ArrayList<>();
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_orange));
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_blue));
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_purple));
        listColor.add(mContext.getResources().getColor(R.color.base_text_green));
        listColor.add(mContext.getResources().getColor(R.color.base_text_red));
        listColor.add(mContext.getResources().getColor(R.color.base_text_yellow));
        listColor.add(mContext.getResources().getColor(R.color.base_text_black));
        listColor.add(mContext.getResources().getColor(R.color.base_text_blue));
        listColor.add(mContext.getResources().getColor(R.color.base_text_purple));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        for (int i = 0, l = data.size(); i < l; i++) {
            dataSets.add(getLineDataSet(mContext, legend.get(i), data.get(i), listColor.get(i)));
        }
        // create a data object with the datasets
        LineData lineData = new LineData(dataSets);
        // set data
        chartLine.setData(lineData);

        //自定义的MarkerView对象
//        MyMarkerView mv = new MyMarkerView(mContext, R.layout.home_chart_line_markerview);
//        mv.setChartView(chartLine);
//        chartLine.setMarker(mv);
//        chartLine.setDrawMarkers(false);

//        chartLine.setVisibleXRange(20);
//        chartLine.setVisibleYRange(20f, AxisDependency.LEFT);
//        chartLine.centerViewTo(20, 50, AxisDependency.LEFT);

        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) data.get(0).getValue().size()/(float)getCategory().size()-1 ;
        chartLine.zoom(ratio,1f,0,0);

        chartLine.animateX(2500);
        //chartLine.invalidate();

        // get the legend (only possible after setting data)
        Legend l = chartLine.getLegend();
        l.setEnabled(false);
        // modify the legend ...
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(7f);
        l.setTextSize(7f);
        l.setTextColor(R.color.base_text_grey);
    }

    public void setChartLine(Context mContext, LineChart chartLine,int color) {
//        chartLine.setOnChartGestureListener(this);
//        chartLine.setOnChartValueSelectedListener(this);

        //设置描述文本不显示
        chartLine.getDescription().setEnabled(false);
        //设置是否显示表格背景
        chartLine.setDrawGridBackground(false);
        //设置是否可以触摸
        chartLine.setTouchEnabled(true);
        chartLine.setDragDecelerationFrictionCoef(0.9f);
        //设置是否可以拖拽
        chartLine.setDragEnabled(true);
        //设置是否可以缩放
        chartLine.setScaleEnabled(true);
        chartLine.setHighlightPerDragEnabled(true);

        // chartLine.setScaleXEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chartLine.setPinchZoom(false);
        chartLine.setDrawBorders(false);
        chartLine.setDrawMarkers(true);
        // set an alternative background color
        // chartLine.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(chartLine); // For bounds control
//        chartLine.setMarker(mv); // Set the marker to the chart

//        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(mContext,R.layout.self_marker_view);
////一定要设置这个玩意，不然到点击到最边缘的时候不会自动调整布局
//        detailsMarkerView.setChartView(chartLine);
////        chartLine.setDetailsMarkerView(detailsMarkerView);
//        chartLine.setMarkerView(detailsMarkerView);

        //下面是Y轴虚线
        XAxis xAxis = chartLine.getXAxis();
//        xAxis.enableGridDashedLine(10f, 10f, 10f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMaximum(data.get(0).getValue().size());
        xAxis.setAxisMinimum(0);
        final Calendar calendar = Calendar.getInstance();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getCategory()));
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));
        xAxis.setTextSize(7f);
        xAxis.setAxisLineColor(mContext.getResources().getColor(R.color.char_line_color_orange));
        //一个界面显示6个Lable，那么这里要设置11个
        xAxis.setLabelCount(getCategory().size());

        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        YAxis leftAxis = chartLine.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMaximum(40);
        leftAxis.setAxisMinimum(0);
        float maxvalue = getDataMaxValue();
        leftAxis.setAxisMaximum(maxvalue);
        LogUtil.i("getDataMaxValue--->"+getDataMaxValue());
        if(maxvalue > 1){
            leftAxis.setGranularity(1);
        }else {
            leftAxis.setGranularity(0.1f);
        }

        leftAxis.setDrawLabels(true);
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//            }
//        });
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));

        chartLine.getAxisRight().setEnabled(false);
        //chartLine.getViewPortHandler().setMaximumScaleY(2f);
        //chartLine.getViewPortHandler().setMaximumScaleX(2f);
        // add data
        ArrayList<Integer> listColor = new ArrayList<>();

        listColor.add(mContext.getResources().getColor(R.color.base_text_green));
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_blue));
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_orange));
        listColor.add(mContext.getResources().getColor(R.color.char_line_color_purple));
        listColor.add(mContext.getResources().getColor(R.color.base_text_red));
        listColor.add(mContext.getResources().getColor(R.color.base_text_yellow));
        listColor.add(mContext.getResources().getColor(R.color.base_text_black));
        listColor.add(mContext.getResources().getColor(R.color.base_text_blue));
        listColor.add(mContext.getResources().getColor(R.color.base_text_purple));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        for (int i = 0, l = data.size(); i < l; i++) {
            dataSets.add(getLineDataSet(mContext, legend.get(i), data.get(i), listColor.get(color)));
        }
        // create a data object with the datasets
        LineData lineData = new LineData(dataSets);
        // set data
        chartLine.setData(lineData);

        //自定义的MarkerView对象
//        MyMarkerView mv = new MyMarkerView(mContext, R.layout.home_chart_line_markerview);
//        mv.setChartView(chartLine);
//        chartLine.setMarker(mv);
//        chartLine.setDrawMarkers(false);

//        chartLine.setVisibleXRange(20);
//        chartLine.setVisibleYRange(20f, AxisDependency.LEFT);
//        chartLine.centerViewTo(20, 50, AxisDependency.LEFT);

        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) data.get(0).getValue().size()/(float)getCategory().size()-1 ;
        chartLine.zoom(ratio,1f,0,0);

        chartLine.animateX(2500);
        //chartLine.invalidate();

        // get the legend (only possible after setting data)
        Legend l = chartLine.getLegend();
        l.setEnabled(false);
        // modify the legend ...
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(7f);
        l.setTextSize(7f);
        l.setTextColor(R.color.base_text_grey);
    }

    ILineDataSet getLineDataSet(Context mContext, String title, DataBean dataBean, int color) {
        ArrayList<Entry> values = new ArrayList<Entry>();
//        for (int i = 0; i < 7; i++) {
//            values.add(new Entry(i, new Random().nextInt(40)));
//        }

        for (int i = 0, l = dataBean.getValue().size(); i < l; i++) {
            values.add(new com.github.mikephil.charting.data.Entry(i, dataBean.getValue().get(i)));
        }

        LineDataSet set1;
        set1 = new LineDataSet(values, title);
        set1.setDrawIcons(false);
        set1.setDrawValues(true);//是否绘制标点值

        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f);
//        set1.enableDashedHighlightLine(10f, 5f, 0f);

        set1.setColor(color);
        set1.setCircleColor(color);
//        set1.setCircleColorHole(Color.WHITE);
        set1.setCircleHoleColor(color);    //实心的颜色
        set1.setDrawCircles(true);
        set1.setDrawCircleHole(true);

        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
//            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.char_line_fade_orange);
//            set1.setFillDrawable(drawable);    //瀑布
            set1.setFillColor(Color.WHITE);
        } else {
            set1.setFillColor(Color.WHITE);
        }


        return set1;

    }


    public void setChartBar(Context mContext, BarChart chartBar) {
//        chartBar.setOnChartGestureListener(this);
//        chartBar.setOnChartValueSelectedListener(this);
        chartBar.setDrawGridBackground(false);
        // no description text
        chartBar.getDescription().setEnabled(false);
        // enable touch gestures
        chartBar.setTouchEnabled(true);

        // enable scaling and dragging
        chartBar.setDragEnabled(false);
        chartBar.setScaleEnabled(false);

        // chartBar.setScaleXEnabled(true);
        // chartBar.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
//        chartBar.setPinchZoom(true);
//        chartBar.setDrawBorders(true);
        chartBar.setDrawMarkers(true);
        // set an alternative background color
        // chartBar.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(chartBar); // For bounds control
//        chartBar.setMarker(mv); // Set the marker to the chart


        XAxis xAxis = chartBar.getXAxis();
//        xAxis.enableGridDashedLine(0f, 0f, 0f);
//        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
//        xAxis.setAxisMinimum(0);
//        xAxis.setAxisMaximum(category.size()-1);
        xAxis.setAxisMaximum(category.size());
        final Calendar calendar = Calendar.getInstance();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//                if(value > 100 && value > category.size()){
//                    value = value/100;
//                }
//                LogUtil.i("xAxis---getFormattedValue-->"+value);
                if(value < 0 || value >= category.size()){
                    return "";
                }
//                String timeStr = getCategory().get((int) value);
                String timeStr =category.get((int) value);
//                LogUtil.i("getFormattedValue-->"+timeStr);
                return timeStr;
            }
        };
        xAxis.setValueFormatter((ValueFormatter) formatter);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));
        xAxis.setTextSize(7f);
        xAxis.setAxisLineColor(resLineColor);
        xAxis.setGranularityEnabled(true);
//        xAxis.setLabelCount(category.size());    //强制有多少个刻度
        LogUtil.i(" xAxis.setLabelCount---->"+category.size());
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        YAxis leftAxis = chartBar.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMaximum(40);
        leftAxis.setAxisMinimum(0f);
        float maxvalue = getDataMaxValue();
        leftAxis.setAxisMaximum(maxvalue);
//        leftAxis.setLabelCount((int) maxvalue);    //强制有多少个刻度
        LogUtil.i("getDataMaxValue--->"+getDataMaxValue());
//        if(maxvalue > 1){
//            leftAxis.setGranularity(1);
//        }else {
//            leftAxis.setGranularity(0.1f);
//        }
//        leftAxis.setGranularity(0.01f);
//        leftAxis.setDrawLabels(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setValueFormatter(new IndexAxisValueFormatter(getCategory()));
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setTextColor(mContext.getResources().getColor(R.color.base_text_grey));

        chartBar.getAxisRight().setEnabled(false);
        //chartBar.getViewPortHandler().setMaximumScaleY(2f);
        //chartBar.getViewPortHandler().setMaximumScaleX(2f);
        // add data
        ArrayList<Integer> listColor = new ArrayList<>();
        if(resLineColor != 0){
            listColor.add(resLineColor);
        }else{
            listColor.add(Color.parseColor("#FFFFAE00"));
            listColor.add(Color.parseColor("#FF31E5E1"));
            listColor.add(Color.parseColor("#FF38C0FF"));
            listColor.add(Color.parseColor("#FFDE7AFF"));
            listColor.add(mContext.getResources().getColor(R.color.char_line_color_blue));
            listColor.add(mContext.getResources().getColor(R.color.char_line_color_orange));
            listColor.add(mContext.getResources().getColor(R.color.char_line_color_purple));
            listColor.add(mContext.getResources().getColor(R.color.base_text_green));
            listColor.add(mContext.getResources().getColor(R.color.base_text_red));
            listColor.add(mContext.getResources().getColor(R.color.base_text_yellow));
            listColor.add(mContext.getResources().getColor(R.color.base_text_black));
            listColor.add(mContext.getResources().getColor(R.color.base_text_blue));
            listColor.add(mContext.getResources().getColor(R.color.base_text_purple));
        }


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int i = 0, l = data.size(); i < l; i++) {
            dataSets.add(getBarDataSet(mContext, legend.get(i), data.get(i), listColor.get(i),i));
        }
        // create a data object with the datasets
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.4f);
//        data.setValueFormatter(new IValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                LogUtil.i("getFormattedValue--->"+value +"---->"+entry.getY()+"");
////                return null;);
//                return entry.getY()+"";
//            }
//        });

        // set data
        chartBar.setData(data);

//        //自定义的MarkerView对象
//        MyMarkerView mv = new MyMarkerView(mContext, R.layout.home_chart_line_markerview);
//        mv.setChartView(chartBar);
//        chartBar.setMarker(mv);

//        chartBar.setVisibleXRange(20);
//        chartBar.setVisibleYRange(20f, AxisDependency.LEFT);
//        chartBar.centerViewTo(20, 50, AxisDependency.LEFT);


        // get the legend (only possible after setting data)
        Legend l = chartBar.getLegend();
        l.setEnabled(true);
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setFormSize(LEGEND_FRONT_SIZE);
        l.setTextSize(LEGEND_FRONT_SIZE);
        l.setTextColor(R.color.base_text_grey);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setYOffset(-5f);

        chartBar.animateX(2500);
        chartBar.invalidate();
    }

    IBarDataSet getBarDataSet(Context mContext, String title, DataBean dataBean, int color,int position) {
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
//        for (int i = 0; i < 7; i++) {
//            values.add(new Entry(i, new Random().nextInt(40)));
//        }

        BarEntry entry;
        for (int i = 0, l = dataBean.getValue().size(); i < l; i++) {
            entry = new BarEntry(position, dataBean.getValue().get(i));
            values.add(entry);
//            LogUtil.i("getLineDataSet--->"+entry.getY());
        }

        BarDataSet set1;

        set1 = new BarDataSet(values, title);

        set1.setDrawIcons(false);
        set1.setDrawValues(false);//是否绘制标点值
//        set1.setBarBorderWidth(8);

        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f);
//        set1.enableDashedHighlightLine(10f, 5f, 0f);

        set1.setColor(color);
//        set1.setCircleHoleColor(Color.WHITE);
//        set1.setCircleColorHole(Color.WHITE);

        set1.setValueTextSize(9f);
//        set1.setFormLineWidth(1f);
//        set1.setFormSize(15.f);
        set1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                LogUtil.i("getFormattedValue--->"+value +"---->"+entry.getY()+"");
                return entry.getY()+"";
            }
        });
//        set1.setValueFormatter(new DefaultValueFormatter(3));
//        set1.setValueFormatter(new StackedValueFormatter(true,"",3));

        return set1;

    }
}