package com.ffapp.waterprice.piechart.interfaces;


import com.ffapp.waterprice.piechart.components.YAxis;
import com.ffapp.waterprice.piechart.data.BarLineScatterCandleBubbleData;
import com.ffapp.waterprice.piechart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
