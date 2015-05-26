
package org.spaceinvaders.client.application.ui.graph.cumulativegradelinechartwidget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.ui.graph.CumulativeGradeLineChart;

import java.util.ArrayList;

public class CumulativeGradeLineChartWidgetPresenter extends PresenterWidget<CumulativeGradeLineChartWidgetPresenter.MyView> {
    public interface MyView extends View {
        void setChart(IsWidget chart);
    }
    CumulativeGradeLineChart chart;
    ArrayList<CumulativeGradeLineChart.Data> chartData;

            @Inject
    CumulativeGradeLineChartWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
        chart = new CumulativeGradeLineChart();
    }

    public void setData(){
        chartData = new ArrayList<>();
        //chartData
    }


    public void resizeChart(int width, int height){
        this.chart.resize(400,400);
    }


}
