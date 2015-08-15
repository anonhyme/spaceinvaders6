
package org.spaceinvaders.client.application.widgets.graph.gwtchartswidget;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.application.widgets.graph.gwtcharts.AbstractGWTChart;

public class GwtChartWidgetPresenter extends PresenterWidget<GwtChartWidgetPresenter.MyView> {
    public interface MyView extends View {
        void setChart(Widget chart);
    }

    private AbstractGWTChart chart;

    @Inject
    GwtChartWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
    }

    public void loadChart() {
        getView().setChart(chart.getChart());
        chart.loadChart();
    }

    public void setChart(AbstractGWTChart chart) {
        this.chart = chart;
    }

    public void setChartColors(String[] colors) {
        chart.setColors(colors);
    }

}
