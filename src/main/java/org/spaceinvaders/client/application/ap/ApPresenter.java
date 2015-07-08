package org.spaceinvaders.client.application.ap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.CumulativeLineChart;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.EvaluationResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartswidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static org.spaceinvaders.client.application.util.ColorHelper.*;

public class ApPresenter extends Presenter<ApPresenter.MyView, ApPresenter.MyProxy> {
    interface MyView extends View {
        void setInSlot(Object slot, IsWidget content);

        void setApName(String name);

        void setStudentProgressBar(float value, String color);

        void setClassProgressBar(float value, String color);

        void addCumulativeChart(IsWidget chart);

        void addEvaluationChart(IsWidget chart);

        void addGrid(IsWidget grid);
    }

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @NameToken(NameTokens.APpage)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<ApPresenter> {
    }

    private final ResourceDelegate<EvaluationResource> semesterGradesDelegate;

    private GridPresenter gridPresenter;

    @Inject
    public ApPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<EvaluationResource> semesterGradesDelegate,
            GridPresenter gridPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.semesterGradesDelegate = semesterGradesDelegate;
        this.gridPresenter = gridPresenter;
    }

    protected void onBind() {
        getStudentSemesterResultsAndGenerateContent();
    }

    //TODO Access current semester id once implemented
    private int semesterId = 3;
    private int apId = 1;
    private String apName = "GEN501";
//    Ap mockAp;

    private void getStudentSemesterResultsAndGenerateContent() {
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
                        generatePageContent(evaluations);
                    }
                }).getApEvaluations(semesterId, apId);
    }

    private void generatePageContent(TreeMap<String, Evaluation> evaluations) {
        //Todo get AP from eventbus or somewhere?

        List<Competence> mockCompetences = Arrays.asList(
                new Competence("GEN501-1", 0),
                new Competence("GEN501-2", 1));
        Ap mockAp = new Ap("GEN501", 0, mockCompetences);

        setCharts(evaluations, mockAp);
        setProgressBars(30, 40);

        gridPresenter.updateGrid(0);
        getView().addGrid(gridPresenter);
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
        getView().setApName(apName);

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                evaluationChartWidget.loadChart();
                cumulativeChartWidget.loadChart();
            }
        });
    }

    private void setProgressBars(float studentProgress, float classProgress) {
        String studentColor = getStudentProgressColor(studentProgress, classProgress);
        getView().setStudentProgressBar(studentProgress, studentColor);
        getView().setClassProgressBar(classProgress, BLUE);
    }

    private String getStudentProgressColor(float studentProgress, float classProgress) {
        String color;
        float progressRatio = studentProgress / classProgress;
        if (progressRatio > 0.85) {
            color = GOLD;
        } else if (progressRatio > 0.5) {
            color = GREEN;
        } else {
            color = RED;
        }
        return color;
    }
}
