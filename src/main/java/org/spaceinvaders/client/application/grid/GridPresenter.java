package org.spaceinvaders.client.application.grid;

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
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class GridPresenter extends Presenter<GridPresenter.MyView, GridPresenter.MyProxy> implements GridUiHandlers {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void initSemesterTable(SemesterInfo result);

        void initSemesterGradesMapResult(List<Evaluation> evaluations);
    }

    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

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

    protected void onBind() {
        super.onBind();
        this.fetchSemesterMapData();
    }

    private void fetchSemesterMapData() {
        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<TreeMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(TreeMap<String, Evaluation> evaluations) {
                        getView().initSemesterGradesMapResult(new ArrayList<Evaluation>(evaluations.values()));
                        fetchSemesterInfo();
                    }
                }).getAllEvaluations(3);
    }

    private void fetchSemesterInfo() {
        semesterInfoDelegate
                .withCallback(new AbstractAsyncCallback<SemesterInfo>() {
                    @Override
                    public void onSuccess(SemesterInfo result) {
                        getView().initSemesterTable(result);
                    }
                }).get(3);
    }
}
