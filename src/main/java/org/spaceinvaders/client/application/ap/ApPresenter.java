package org.spaceinvaders.client.application.ap;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.CumulativeLineChart;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.EvaluationResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartswidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class ApPresenter extends Presenter<ApPresenter.MyView, ApPresenter.MyProxy> {
    interface MyView extends View {
        void setInSlot(Object slot, IsWidget content);
        void setApName(String name);
        void setStudentProgressBar(float value, String color);
        void setClassProgressBar(float value, String color);
    }

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_APgrid = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_APCumulativeChart = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_APEvaluationsChart = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.APpage)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<ApPresenter> {
    }

    private final ResourceDelegate<EvaluationResource> semesterGradesDelegate;

    @Inject
    public ApPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<EvaluationResource> semesterGradesDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.semesterGradesDelegate = semesterGradesDelegate;
    }

    protected void onBind() {

        getStudentSemesterResultsAndGenerateContenr();

    }

    //TODO Access current semester id once implemented
    private int SESSION_ID = 3;
    private int apId = 1;
    private String apName = "GEN501";
    private Ap mockAp;


    private void getStudentSemesterResultsAndGenerateContenr() {
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
                        generatePageContent(evaluations);
                    }
                }).getApEvaluations(SESSION_ID, apId);
    }

    private void generatePageContent(TreeMap<String, Evaluation> evaluations) {
        //Todo get AP from eventbus or somewhere?

        List<Competence> mockComepetences = Arrays.asList(
                new Competence("GEN501-1", 0),
                new Competence("GEN501-2", 1));
        mockAp = new Ap("GEN501", 0, mockComepetences);

        String[] colors = {"#FF0000", "#00FF00", "#0000FF"};

        final GwtChartWidgetPresenter evaluationChartWidget = gwtChartWidgetPresenterProvider.get();
        evaluationChartWidget.setChart(new EvaluationResultsChart(evaluations,mockAp));
        evaluationChartWidget.setChartColors(colors);

        final GwtChartWidgetPresenter cumulativeChartWidget = gwtChartWidgetPresenterProvider.get();
        cumulativeChartWidget.setChart(new CumulativeLineChart(evaluations,mockAp));
        cumulativeChartWidget.setChartColors(colors);

        setInSlot(SLOT_APEvaluationsChart, evaluationChartWidget);
        setInSlot(SLOT_APCumulativeChart, cumulativeChartWidget);

        final MyView view = getView();
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


    private void setProgressBars(float studentProgress, float classProgress){
        String studentColor = getStudentProgressColor(studentProgress,classProgress);
        String classColor = "#0000CC"; //Blue
        MyView view = getView();
        view.setStudentProgressBar(studentProgress, studentColor);
        view.setClassProgressBar(classProgress,classColor);
    }

    private String getStudentProgressColor(float studentProgress, float classProgress){
        String color;
        float progressRatio = studentProgress/classProgress;
        if(progressRatio > 0.85){
            color = "#FFD700"; //Gold
        }
        else if (progressRatio > 0.5) {
            color = "#008000"; //Green
        }
        else{
            color = "#FF0000"; //Red
        }
        return color;
    }
}
