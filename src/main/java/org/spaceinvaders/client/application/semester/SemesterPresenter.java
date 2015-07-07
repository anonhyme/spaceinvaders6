package org.spaceinvaders.client.application.semester;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.widgets.graph.gwtcharts.SemesterResultsChart;
import org.spaceinvaders.client.application.widgets.graph.gwtchartswidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.events.CellClickApEvent;

import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.inject.Inject;

import static org.spaceinvaders.client.application.util.ColorHelper.GREEN;
import static org.spaceinvaders.client.application.util.ColorHelper.LIGHT_BLUE;
import static org.spaceinvaders.client.application.util.ColorHelper.RED;

public class SemesterPresenter extends Presenter<SemesterPresenter.MyView, SemesterPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler, CellClickApEvent.CellClickApEventHandler {

    public interface MyView extends View {
        void addGrid(IsWidget gridWidget);

        void updateSemesterChart(GwtChartWidgetPresenter chartWidget);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterPresenter> {
    }

    private GridPresenter gridPresenter;
    private final ResourceDelegate<EvaluationResource> evaluationDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @Inject
    SemesterPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      GridPresenter gridPresenter,
                      ResourceDelegate<EvaluationResource> semesterGradeDelegate,
                      ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.gridPresenter = gridPresenter;
        this.evaluationDelegate = semesterGradeDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;
    }

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler();
        showGrid();
        showGraph();
    }

    private void registerHandler() {
        addRegisteredHandler(SemesterChangedEvent.TYPE, this);
        addRegisteredHandler(CellClickApEvent.TYPE, this);
    }

    private void showGrid() {
        gridPresenter.updateGrid(0);
        getView().addGrid(gridPresenter);

    }

    private SemesterInfo semesterInfo;

    private void showGraph() {
        semesterInfoDelegate.withCallback(new AbstractAsyncCallback<SemesterInfo>() {
            @Override
            public void onSuccess(SemesterInfo results) {
                semesterInfo = results;
                getEvaluations();
            }
        }).get(3);
    }

    private void getEvaluations() {
        evaluationDelegate.withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
            @Override
            public void onSuccess(TreeMap<String, Evaluation> results) {
                GWT.log(results.toString());
                final GwtChartWidgetPresenter semesterChartPresenter = gwtChartWidgetPresenterProvider.get();
                String[] colors = {RED, GREEN, LIGHT_BLUE};
                semesterChartPresenter.setChart(new SemesterResultsChart(semesterInfo, new ArrayList<>(results.values())));
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
        }).getAllEvaluations(3);
    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        gridPresenter.updateGrid(event.getSemesterID());
    }

    @Override
    public void onColumnClick(CellClickApEvent event) {
        //TODO Create ap page and reveal it
        GWT.debugger();
    }

}