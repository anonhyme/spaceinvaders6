package org.spaceinvaders.client.application.widgets.grid;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.application.widgets.grid.events.EvaluationReceivedEvent;

import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.widgets.grid.events.SemesterInfoReceivedEvent;
import org.spaceinvaders.client.events.ApSelectedEvent;
import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GridPresenter extends PresenterWidget<GridPresenter.MyView>
        implements GridUiHandlers {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void updateSemesterTable(List<String> competencesLabels, List<Evaluation> evaluations);
    }

//    private final ResourceDelegate<EvaluationResource> evaluationDelegate;
//    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view) {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }

    public void updateGrid(List<String> competencesLabels, TreeMap<String, Evaluation> evaluations) {
        getView().updateSemesterTable(competencesLabels, new ArrayList<>(evaluations.values()));
    }

//    protected void onBind() {
//        super.onBind();
//        registerHandlers();
//    }

//    private void registerHandlers() {
//        addRegisteredHandler(SemesterInfoReceivedEvent.TYPE, this);
//        addRegisteredHandler(EvaluationReceivedEvent.TYPE, this);
//    }
//
//    @Override
//    public void onSemesterInfoReceived(SemesterInfoReceivedEvent event) {
//        this.semesterInfo = event.getSemesterInfo();
//        fetchSemesterGrades();
//    }
//
//    @Override
//    public void onSemesterGradesReceived(EvaluationReceivedEvent event) {
//        getView().updateSemesterTable(semesterInfo, new ArrayList<>(event.getEvaluations().values()));
//    }
//
//    private void fetchSemesterInfo(int semesterID) {
//        semesterInfoDelegate
//                .withCallback(new AbstractAsyncCallback<SemesterInfo>() {
//                    @Override
//                    public void onSuccess(SemesterInfo semesterInfo) {
//                        SemesterInfoReceivedEvent.fire(semesterInfo, getInstance());
//                    }
//                }).get(semesterID);
//    }
//
//    private void fetchSemesterGrades() {
//        evaluationDelegate
//                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
//                    @Override
//                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
//                        EvaluationReceivedEvent.fire(evaluations, getInstance());
//                    }
//                }).getAllEvaluations(semesterInfo.getId());
//    }

    @Override
    public GridPresenter getInstance() {
        return this;
    }
}
