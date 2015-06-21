



package org.spaceinvaders.client.application.appage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.CumulativeLineChart;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.EvaluationResultsChart;
import org.spaceinvaders.client.application.ui.graph.gwtchartwidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.place.NameTokens;

import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.UserInfo;

import java.util.List;
import java.util.SortedMap;

public class APpagePresenter extends Presenter<APpagePresenter.MyView, APpagePresenter.MyProxy> {
    interface MyView extends View {

        void setInSlot(Object slot, IsWidget content);
        void setApName(String name);
        void hideCumulativeChart();
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
    public interface MyProxy extends ProxyPlace<APpagePresenter> {
    }


    private DispatchAsync dispatcher;

    private final ResourceDelegate<UserInfoResource> userInfoDelegate;
    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

    @Inject
    public APpagePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            DispatchAsync dispatchAsync, ResourceDelegate<UserInfoResource> userInfoDelegate, ResourceDelegate<SemesterGradesResource> semesterGradesDelegate, ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view, proxy, RevealType.Root);


        this.dispatcher = dispatchAsync;

        this.userInfoDelegate = userInfoDelegate;
        this.semesterGradesDelegate = semesterGradesDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;
    }

    protected void onBind(){

        getStudentSemesterResultsAndGenerateContenr();
       // getView().hideCumulativeChart();

    }

    //TODO Access current semester id once implemented
    private int SESSION_ID = 3;
    private String AP_ID = "GEN501";

    private void getStudentSemesterResultsAndGenerateContenr(){
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<List<CompetenceEvalResult>>() {
                    @Override
                    public void onSuccess(List<CompetenceEvalResult> result) {

                        generatePageContent(result);
                    }
                }).getAllCompetenceEvalResults(SESSION_ID);

    }

    private void generatePageContent(List<CompetenceEvalResult> results){

        String [] colors = {"#00FF00", "#FF0000", "#0000FF"};

        final GwtChartWidgetPresenter evaluationChartWidget = gwtChartWidgetPresenterProvider.get();
        evaluationChartWidget.setChart(new EvaluationResultsChart(results, AP_ID));
        evaluationChartWidget.setChartColors(colors);

        final GwtChartWidgetPresenter cumulativeChartWidget = gwtChartWidgetPresenterProvider.get();
        cumulativeChartWidget.setChart(new CumulativeLineChart(results, AP_ID));
        cumulativeChartWidget.setChartColors(colors);

        setInSlot(this.SLOT_APEvaluationsChart, evaluationChartWidget);
        setInSlot(this.SLOT_APCumulativeChart, cumulativeChartWidget);

        final MyView view = getView();

        getView().setApName(AP_ID);


        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                evaluationChartWidget.loadChart();
                cumulativeChartWidget.loadChart();
            }
        });
        setProgressBars(50f, 60f);
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
