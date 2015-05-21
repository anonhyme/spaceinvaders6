
package org.spaceinvaders.client.application.ui.graph.graphWidget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.ui.graph.*;

public class GraphWidgetPresenter extends PresenterWidget<GraphWidgetPresenter.MyView> {
    public interface MyView extends View {
        void setChart(IsWidget content);
    }

    public enum ChartType {CumulativeGradeLineChart, Area, GroupBar, StackedBar, Gauge, Pie}

    ;

    public static final Object TYPE_basicChart = new Object();

    private AbstractChart chart;

    @Inject
    GraphWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
    }

    @Override
    protected void onBind() {
        super.onBind();
        //view.setInSlot(TYPE_basicChart,chart);
    }

    public void setGraphType(ChartType type) {
        switch (type) {
            case CumulativeGradeLineChart:
                this.chart = new CumulativeGradeLineChart();
                break;
            case Area:
                this.chart = new AreaChart();
                break;
            case GroupBar:
                this.chart = new GroupBarChart();
                break;
            case StackedBar:
                this.chart = new StackedBarChart();
                break;
            case Gauge:
                this.chart = new GaugeChart();
                break;
            case Pie:
                this.chart = new PieChart();
                break;
        }
    }

    public void setChartData(){
        this.chart.setData();
    }

    public void showChart(){
        MyView view = getView();
        view.setChart(chart);

    }

    public void resizeChart(int width, int height){
        this.chart.resize(400,400);
    }
}
