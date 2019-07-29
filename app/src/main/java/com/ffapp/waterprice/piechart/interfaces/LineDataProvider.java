package com.ffapp.waterprice.piechart.interfaces;


import com.ffapp.waterprice.piechart.components.YAxis;
import com.ffapp.waterprice.piechart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
