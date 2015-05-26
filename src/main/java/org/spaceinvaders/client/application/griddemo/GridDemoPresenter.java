


package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.rpc.SemesterServiceAsync;
import org.spaceinvaders.shared.model.EvaluationGrid;
import org.spaceinvaders.shared.model.SemesterInfo;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GridDemoPresenter extends Presenter<GridDemoPresenter.MyView, GridDemoPresenter.MyProxy> implements GridDemoUiHandlers {
    interface MyView extends View, HasUiHandlers<GridDemoUiHandlers> {
        void updateSemesterTable(List<EvaluationGrid> result);
        void initSemesterTable(EvaluationGrid result);
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_GridDemo = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridDemoPresenter> {
    }

    private SemesterServiceAsync exampleService;

    @Inject
    public GridDemoPresenter(
            EventBus eventBus,
            MyView view,
            SemesterServiceAsync exampleService,
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        this.exampleService = exampleService;
        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
        fetchSemesterInfo();
        GWT.log("onBind ");
    }

    /**
     * Retrieve basic semester info Courses name Count
     */

    @Override
    public void fetchSemesterInfo() {
        GWT.log("Async call fetchSemesterData");
        exampleService.fetchSemesterInfo(new AsyncCallback<EvaluationGrid>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(EvaluationGrid result) {
                getView().initSemesterTable(result);
            }
        });
    }

//    @Override
//    public void initDataGrid() {
////        getView().initSemesterTable();
//    }
}
