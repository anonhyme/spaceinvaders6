package org.spaceinvaders.client.application.ui.graph.GwtCharts;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

/**
 * Created by Etienne on 2015-06-10.
 */
public abstract class AbstractGWTChart{
    private List<CompetenceEvalResult> chartData;

    abstract public Widget getChart();
    public void setChartData(List<CompetenceEvalResult> data){
        chartData = data;
    }


    public  List<CompetenceEvalResult>  getChartData(){
        return chartData;
    }
    abstract public void loadChart();

}
