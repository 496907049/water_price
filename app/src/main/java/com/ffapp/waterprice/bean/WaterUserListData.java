package com.ffapp.waterprice.bean;

import android.content.Context;
import android.graphics.Color;

import com.alibaba.fastjson.annotation.JSONField;
import com.ffapp.waterprice.piechart.PieChart;
import com.ffapp.waterprice.piechart.data.PieDataSet;
import com.ffapp.waterprice.piechart.data.PieEntry;
import com.ffapp.waterprice.util.chart.PieChartEntity;

import java.util.ArrayList;
import java.util.List;

public class WaterUserListData extends BasisBean {
    @JSONField(name = "list")
    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setPicChart(Context mContext, PieChart pieChart){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (DataBean data : getData()){
            entries.add(new PieEntry(data.getValue(), data.getName()));
        }

        int[] colors = {Color.parseColor("#faa74c"),
                Color.parseColor("#58D4C5"),
                Color.parseColor("#36a3eb"),
                Color.parseColor("#cc435f"),
                Color.parseColor("#f1ea56")};


        PieChartEntity pieChartEntity = new PieChartEntity(pieChart, entries, new String[]{"", "", ""}, colors, 12f, Color.GRAY, PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieChartEntity.setHoleEnabled(Color.TRANSPARENT, 40f, Color.TRANSPARENT, 40f);
        pieChartEntity.setLegendEnabled(false);
        pieChartEntity.setPercentValues(true);



//
//        PieDataSet dataSet = new PieDataSet(strings, "");
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(mContext.getResources().getColor(R.color.red));
//        colors.add(mContext.getResources().getColor(R.color.base_blue));
//        colors.add(mContext.getResources().getColor(R.color.green));
//        colors.add(mContext.getResources().getColor(R.color.base_bg_yellow));
//        colors.add(mContext.getResources().getColor(R.color.base_color_purple));
//        dataSet.setColors(colors);
//
//        PieData pieData = new PieData(dataSet);
//        pieData.setDrawValues(true);
//
//        pieChart.setData(pieData);
//        Description description = new Description();
//        description.setText("");
//        pieChart.setDescription(description);    //设置图表右下角文字
//        pieChart.setUsePercentValues(true);     //启用此功能，将已百分比的形式出现
//        pieChart.invalidate();
    }


    public static class DataBean {
        private String name;

        private float value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }




}
