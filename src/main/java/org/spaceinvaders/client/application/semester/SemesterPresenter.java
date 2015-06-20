package org.spaceinvaders.client.application.semester;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.application.widgets.graph.gwtchartwidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

import javax.inject.Inject;

public class SemesterPresenter extends Presenter<SemesterPresenter.MyView, SemesterPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler {
    public interface MyView extends View {
        void addGrid(IsWidget gridWidget);

        void updateSemesterChart(GwtChartWidgetPresenter chartWidget, List<CompetenceEvalResult> results);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterPresenter> {
    }

    private GridPresenter gridPresenter;
    private final ResourceDelegate<SemesterGradesResource> semesterGradeDelegate;

    @Inject
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

    @Inject
    SemesterPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      RestDispatch restDispatch,
                      GridPresenter gridPresenter, ResourceDelegate<SemesterGradesResource> semesterGradeDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.gridPresenter = gridPresenter;
        this.semesterGradeDelegate = semesterGradeDelegate;
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
    }

    private void showGrid() {
        gridPresenter.updateGrid(0);
        getView().addGrid(gridPresenter);
    }

    private void showGraph() {
        // todo :  HERE SHOULD GET SEMESTER RESULTS AND NOT AP RESULTS
        semesterGradeDelegate.withCallback(new AbstractAsyncCallback<List<CompetenceEvalResult>>() {
            @Override
            public void onSuccess(List<CompetenceEvalResult> results) {
                GWT.log(results.toString());
                GwtChartWidgetPresenter semesterChartPresenter = gwtChartWidgetPresenterProvider.get();
                getView().updateSemesterChart(semesterChartPresenter, results);
            }
        }).getAllApResults(3, 1);
    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        GWT.log(SemesterPresenter.class.toString() + ": this is how you know if the semester changed");
        gridPresenter.updateGrid(event.getSemesterID());
    }
}