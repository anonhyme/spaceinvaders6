
package org.spaceinvaders.client.application.ui.graph.gwtchartwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.ui.graph.ApInfo;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.AbstractGWTChart;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GwtChartWidgetPresenter extends PresenterWidget<GwtChartWidgetPresenter.MyView> {
    public interface MyView extends View {
        public void setChart(Widget chart);
    }

    private AbstractGWTChart chart;

    @Inject
    GwtChartWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);

    }

    public void loadChart(){
        getView().setChart(chart.getChart());
        chart.loadChart();
    }
    public void setChart(AbstractGWTChart chart){
        this.chart= chart;
    }
    public void setChartColors(String [] colors){
        chart.setColors(colors);
    }

}
