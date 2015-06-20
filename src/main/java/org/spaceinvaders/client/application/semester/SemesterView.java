package org.spaceinvaders.client.application.semester;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.mvp.client.ViewImpl;

import org.spaceinvaders.client.application.widgets.graph.GwtCharts.SemesterResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartwidget.GwtChartWidgetPresenter;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

import javax.inject.Inject;

public class SemesterView extends ViewImpl implements SemesterPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SemesterView> {
    }

    @UiField
    SimplePanel gridPanel;

    @UiField
    SimplePanel semesterChartPanel;

    @Inject
    SemesterView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addGrid(IsWidget gridWidget) {
        gridPanel.clear();
        gridPanel.add(gridWidget);
    }

    public void updateSemesterChart(final GwtChartWidgetPresenter semesterChart, List<CompetenceEvalResult> results) {
        semesterChartPanel.clear();

        semesterChart.setChart(new SemesterResultsChart(results));
        semesterChartPanel.add(semesterChart);

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                semesterChart.loadChart();
            }
        });
    }
}
