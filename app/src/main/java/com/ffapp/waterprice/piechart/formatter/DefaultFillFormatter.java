package com.ffapp.waterprice.piechart.formatter;

import com.ffapp.waterprice.piechart.data.LineData;
import com.ffapp.waterprice.piechart.interfaces.ILineDataSet;
import com.ffapp.waterprice.piechart.interfaces.LineDataProvider;

/**
 * Default formatter that calculates the position of the filled line.
 *
 * @author Philipp Jahoda
 */
public class DefaultFillFormatter implements IFillFormatter
{

    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {

        float fillMin = 0f;
        float chartMaxY = dataProvider.getYChartMax();
        float chartMinY = dataProvider.getYChartMin();

        LineData data = dataProvider.getLineData();

        if (dataSet.getYMax() > 0 && dataSet.getYMin() < 0) {
            fillMin = 0f;
        } else {

            float max, min;

            if (data.getYMax() > 0)
                max = 0f;
            else
                max = chartMaxY;
            if (data.getYMin() < 0)
                min = 0f;
            else
                min = chartMinY;

            fillMin = dataSet.getYMin() >= 0 ? min : max;
        }

        return fillMin;
    }
}