



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
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

public class APpagePresenter extends Presenter<APpagePresenter.MyView, APpagePresenter.MyProxy> {
    interface MyView extends View {

        void setInSlot(Object slot, IsWidget content);
        void setApName(String name);
        void hideCumulativeChart();
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

    @Inject
    public APpagePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            DispatchAsync dispatchAsync) {
        super(eventBus, view, proxy, RevealType.Root);


        this.dispatcher = dispatchAsync;

    }

    protected void onBind(){

        loadCipAndSessionData();
        getView().hideCumulativeChart();

    }

    private void loadCipAndSessionData(){
        this.dispatcher.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {

                getStudentSemesterResults(result.getUserInfo().getCip());
            }
        });
    }

    //TODO Access current semester id once implemented
    private int SESSION_ID = 3;
    private String AP_ID = "GEN501";

    private void getStudentSemesterResults(String cip){

        // TODO : Remove that and put it where we'll really use it
        this.dispatcher.execute(new GetSemesterGradesAction(cip, SESSION_ID), new AsyncCallback<GetSemesterGradesResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetSemesterGradesResult result) {
                generatePageContent(result.getEvaluationResults());
                GWT.log(result.getEvaluationResults().get(0).getCourseLabel());
                GWT.log(result.getEvaluationResults().get(0).getCompetenceLabel());
                GWT.log(result.getEvaluationResults().get(0).getEvalLabel());
            }
        });
    }

    private void generatePageContent(List<CompetenceEvalResult> results){

        String [] colors = {"#FF0000", "#00FF00", "#0000FF"};

        final GwtChartWidgetPresenter evaluationChartWidget = gwtChartWidgetPresenterProvider.get();
        evaluationChartWidget.setChart(new EvaluationResultsChart(AP_ID));
        evaluationChartWidget.setChartData(results);
        evaluationChartWidget.setChartColors(colors);

        final GwtChartWidgetPresenter cumulativeChartWidget = gwtChartWidgetPresenterProvider.get();
        cumulativeChartWidget.setChart(new CumulativeLineChart(AP_ID));
        cumulativeChartWidget.setChartData(results);
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
    }

}
