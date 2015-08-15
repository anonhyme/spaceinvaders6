package org.spaceinvaders.client.application.ap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.CumulativeLineChart;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.EvaluationResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartswidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.ArrayList;
import java.util.TreeMap;

import static org.spaceinvaders.client.application.util.ColorHelper.BLUE;
import static org.spaceinvaders.client.application.util.ColorHelper.GOLD;
import static org.spaceinvaders.client.application.util.ColorHelper.GREEN;
import static org.spaceinvaders.client.application.util.ColorHelper.GREEN_FLASH;
import static org.spaceinvaders.client.application.util.ColorHelper.LIGHT_BLUE;
import static org.spaceinvaders.client.application.util.ColorHelper.RED;

public class ApPresenter extends Presenter<ApPresenter.MyView, ApPresenter.MyProxy> {
    interface MyView extends View {
        void setInSlot(Object slot, IsWidget content);

        void setApName(String name);

        void setStudentProgressBar(double value, String color);

        void setClassProgressBar(double value, String color);

        void addCumulativeChart(IsWidget chart);

        void addEvaluationChart(IsWidget chart);

        void addGrid(IsWidget grid);

        void showErrorMessage();
    }

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @NameToken(NameTokens.APpage)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<ApPresenter> {
    }

    private GridPresenter gridPresenter;
    private Ap ap;

    @Inject
    public ApPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            GridPresenter gridPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.gridPresenter = gridPresenter;
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        if (ap == null) {
            getView().showErrorMessage();
        }
    }

    private void setCharts(TreeMap<String, Evaluation> evaluations, Ap ap) {
        String[] colors = {RED, GREEN_FLASH, LIGHT_BLUE};

        EvaluationResultsChart evaluationResultsChart = new EvaluationResultsChart(evaluations, ap);
        evaluationResultsChart.setSizeFromWindowSize(Window.getClientWidth(), Window.getClientHeight());
        final GwtChartWidgetPresenter evaluationChartWidget = gwtChartWidgetPresenterProvider.get();
        evaluationChartWidget.setChart(evaluationResultsChart);
        evaluationChartWidget.setChartColors(colors);

        CumulativeLineChart cumulativeLineChart = new CumulativeLineChart(evaluations, ap);
        cumulativeLineChart.setSizeFromWindowSize(Window.getClientWidth(), Window.getClientHeight());
        final GwtChartWidgetPresenter cumulativeChartWidget = gwtChartWidgetPresenterProvider.get();
        cumulativeChartWidget.setChart(cumulativeLineChart);
        cumulativeChartWidget.setChartColors(colors);

        getView().addEvaluationChart(evaluationChartWidget);
        getView().addCumulativeChart(cumulativeChartWidget);
        getView().setApName(ap.getName());

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                evaluationChartWidget.loadChart();
                cumulativeChartWidget.loadChart();
            }
        });

    }


    private void getProgressSums(TreeMap<String, Evaluation> evaluations, Ap ap) {
        ArrayList<Evaluation> data = new ArrayList<>(evaluations.values());
        double currentStudentTotal = 0.0;
        double currentMaxTotal = 0.0;
        double currentProgressionTotal = 0.0;

        for (int i = 0; i < data.size(); i++)
        {
            Result r = data.get(i).getApResult(ap);
            currentMaxTotal += r.getMaxTotal();
            if (r.getIsValid()) {
                currentStudentTotal += r.getStudentTotal();
                currentProgressionTotal += r.getMaxTotal();
            }
        }

        setProgressBars(currentStudentTotal, currentProgressionTotal, currentMaxTotal);
    }

    private void setProgressBars(double studentProgress, double classProgress, double maxProgress) {
        String studentColor = getStudentProgressColor(studentProgress, classProgress);
        getView().setStudentProgressBar(studentProgress/maxProgress *100, studentColor);
        getView().setClassProgressBar(classProgress/maxProgress *100, BLUE);
    }

    private String getStudentProgressColor(double studentProgress, double classProgress) {
        String color;
        double progressRatio = studentProgress / classProgress;
        if (progressRatio > 0.85) {
            color = GOLD;
        } else if (progressRatio > 0.5) {
            color = GREEN;
        } else {
            color = RED;
        }
        return color;
    }

    public void update(Ap ap, TreeMap<String, Evaluation> apEvals) {
        this.ap = ap;
        gridPresenter.updateGrid(ap.getCompetencesStrings(), apEvals);
        getView().addGrid(gridPresenter);
        setCharts(apEvals, ap);
        getProgressSums(apEvals, ap);
    }
}
