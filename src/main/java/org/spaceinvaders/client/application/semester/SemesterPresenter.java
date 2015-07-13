package org.spaceinvaders.client.application.semester;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.ap.ApPresenter;
import org.spaceinvaders.client.application.events.RevealPresenterEvent;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.SemesterResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartswidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.events.ApSelectedEvent;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;

import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;
import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.spaceinvaders.client.application.util.ColorHelper.GREEN;
import static org.spaceinvaders.client.application.util.ColorHelper.LIGHT_BLUE;
import static org.spaceinvaders.client.application.util.ColorHelper.RED;

public class SemesterPresenter extends Presenter<SemesterPresenter.MyView, SemesterPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler, ApSelectedEvent.Handler {
    public interface MyView extends View {
        void addGrid(IsWidget gridWidget);

        void updateSemesterChart(IsWidget chartWidget);

        void updateTitle(String title);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterPresenter> {
    }

    private GridPresenter gridPresenter;
    private final ResourceDelegate<EvaluationResource> evaluationDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;
    private SemesterInfo semesterInfo;
    private PlaceManager placeManager;
    private TreeMap<String, Evaluation> evaluations;

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @Inject
    ApPresenter apPresenter;

    @Inject
    SemesterPresenter(EventBus eventBus,
                      PlaceManager placeManager,
                      MyView view,
                      MyProxy proxy,
                      GridPresenter gridPresenter,
                      ResourceDelegate<EvaluationResource> semesterGradeDelegate,
                      ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.placeManager = placeManager;
        this.gridPresenter = gridPresenter;
        this.evaluationDelegate = semesterGradeDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;
    }

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler();
//        fetchSemesterInfo(3); //TODO REPLACE THIS WITH THE RIGHT SEMESTER ID
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        fetchSemesterInfo(3); //TODO REPLACE THIS WITH THE RIGHT SEMESTER ID
    }

    private void registerHandler() {
        addRegisteredHandler(SemesterChangedEvent.TYPE, this);
        addRegisteredHandler(ApSelectedEvent.TYPE, this);
    }

    private void fetchSemesterInfo(final int semesterID) {
        semesterInfoDelegate.withCallback(new AbstractAsyncCallback<SemesterInfo>() {
            @Override
            public void onSuccess(SemesterInfo results) {
                semesterInfo = results;
                getView().updateTitle(Integer.toString(semesterID));
                getEvaluations();
            }
        }).get(semesterID);
    }

    private void showGrid() {
        gridPresenter.updateGrid(semesterInfo.getCompetencesLabels(), evaluations);
        getView().addGrid(gridPresenter);
    }

    private void getEvaluations() {
        evaluationDelegate.withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
            @Override
            public void onSuccess(TreeMap<String, Evaluation> results) {
                evaluations = results;
                showGrid();
                showCharts();
            }
        }).getAllEvaluations(semesterInfo.getId());
    }

    private void showCharts() {
        String[] colors = {RED, GREEN, LIGHT_BLUE};

        SemesterResultsChart semesterResultsChart = new SemesterResultsChart(semesterInfo, new ArrayList<>(evaluations.values()));
        semesterResultsChart.setSizeFromWindowSize(Window.getClientWidth(), Window.getClientHeight());

        final GwtChartWidgetPresenter semesterChartPresenter = gwtChartWidgetPresenterProvider.get();
        semesterChartPresenter.setChart(semesterResultsChart);
        semesterChartPresenter.setChartColors(colors);

        getView().updateSemesterChart(semesterChartPresenter);

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                semesterChartPresenter.loadChart();
            }
        });
    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        fetchSemesterInfo(event.getSemesterID());
    }

    @Override
    public void onApSelected(ApSelectedEvent event) {
        String apLabel = event.getAp();
        Ap ap = semesterInfo.findAp(apLabel);

        // Filter results for this AP
        TreeMap<String, Evaluation> apEvals = new TreeMap<>();
        for (String competenceLabel : evaluations.keySet()) {
            Evaluation eval = evaluations.get(competenceLabel);
            Evaluation apEval = eval.getApResults(ap);

            if (apEval.getResults().size() != 0) {
                apEvals.put(eval.getLabel(), apEval);
            }
        }

        apPresenter.update(ap, apEvals);

        PlaceRequest placeRequest = new PlaceRequest.Builder().
                nameToken(NameTokens.APpage).
                build();
        placeManager.revealPlace(placeRequest);
    }
}