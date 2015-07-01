package org.spaceinvaders.client.application.grid;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.application.grid.events.SemesterGradesReceivedEvent;
import org.spaceinvaders.client.application.grid.events.SemesterInfoReceivedEvent;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.widgets.cell.WidgetsFactory;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class GridPresenter extends PresenterWidget<GridPresenter.MyView>
        implements GridUiHandlers,
        SemesterInfoReceivedEvent.SemesterInfoReceivedEventHandler,
        SemesterGradesReceivedEvent.SemesterGradesReceivedHandler {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void updateSemesterTable(SemesterInfo semesterInfo, List<Evaluation> evaluations);

        void addCellPresenter(WidgetsFactory widgetsFactory);
    }

    private final WidgetsFactory widgetsFactory;

    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;
    private SemesterInfo semesterInfo;
    private List<Evaluation> evaluations;

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view,
                         WidgetsFactory widgetsFactory,
                         ResourceDelegate<SemesterGradesResource> semesterGradesDelegate,
                         ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view);
        this.widgetsFactory = widgetsFactory;
        this.semesterGradesDelegate = semesterGradesDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;
        getView().setUiHandlers(this);
        view.addCellPresenter(widgetsFactory);
    }

    public void updateGrid(int semesterID) {
        fetchSemesterInfo(semesterID);
    }

    protected void onBind() {
        super.onBind();
        registerHandlers();
    }

    private void registerHandlers() {
        addRegisteredHandler(SemesterInfoReceivedEvent.TYPE, this);
        addRegisteredHandler(SemesterGradesReceivedEvent.TYPE, this);
    }

    @Override
    public void onSemesterInfoReceived(SemesterInfoReceivedEvent event) {
        this.semesterInfo = event.getSemesterInfo();
        fetchSemesterGrades();
    }

    @Override
    public void onSemesterGradesReceived(SemesterGradesReceivedEvent event) {
        this.evaluations = new ArrayList<>(event.getEvaluations().values());
        getView().updateSemesterTable(semesterInfo, evaluations);
    }

    private void fetchSemesterInfo(int semesterID) {
        semesterInfoDelegate
                .withCallback(new AbstractAsyncCallback<SemesterInfo>() {
                    @Override
                    public void onSuccess(SemesterInfo semesterInfo) {
                        SemesterInfoReceivedEvent.fire(semesterInfo, getThis());
                    }
                }).get(semesterID);
    }

    private void fetchSemesterGrades() {
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
                        SemesterGradesReceivedEvent.fire(evaluations, getThis());
                    }
                }).getAllEvaluations(3);
    }
    private GridPresenter getThis(){
        return this;
    }

//    private native boolean isjQueryLoaded() /*-{
//        console.log("hello");
//        return (typeof $wnd['jQuery'] !== 'undefined');
//    }-*/;
}
