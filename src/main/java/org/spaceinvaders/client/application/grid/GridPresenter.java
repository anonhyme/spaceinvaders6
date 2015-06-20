package org.spaceinvaders.client.application.grid;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.grid.events.SemesterGradesReceivedEvent;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.application.grid.events.SemesterInfoReceivedEvent;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class GridPresenter extends Presenter<GridPresenter.MyView, GridPresenter.MyProxy>
        implements GridUiHandlers,
        SemesterInfoReceivedEvent.SemesterInfoReceivedEventHandler,
        SemesterGradesReceivedEvent.SemesterGradesReceivedHandler {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void updateSemesterTable(SemesterInfo semesterInfo, List<Evaluation> evaluations);
    }

    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;
    private SemesterInfo semesterInfo;
    private List<Evaluation> evaluations;

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridPresenter> {
    }

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy,
                         ResourceDelegate<SemesterGradesResource> semesterGradesDelegate,
                         ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.semesterGradesDelegate = semesterGradesDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;

        getView().setUiHandlers(this);
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
        final GridPresenter _this = this;
        semesterInfoDelegate
                .withCallback(new AbstractAsyncCallback<SemesterInfo>() {
                    @Override
                    public void onSuccess(SemesterInfo semesterInfo) {
                        SemesterInfoReceivedEvent.fire(semesterInfo, _this);
                    }
                }).get(semesterID);
    }

    private void fetchSemesterGrades() {
        final GridPresenter _this = this;
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
                        SemesterGradesReceivedEvent.fire(evaluations, _this);
                    }
                }).getAllEvaluations(3);
    }
}
