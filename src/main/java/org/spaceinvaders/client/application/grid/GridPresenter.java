package org.spaceinvaders.client.application.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.client.DefaultRestResponseDeserializer;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dispatch.UserInfo;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.results.GetSemesterGradesMapResult;
import org.spaceinvaders.shared.dispatch.results.GetSemesterInfoResult;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class GridPresenter extends Presenter<GridPresenter.MyView, GridPresenter.MyProxy> implements GridUiHandlers {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void initSemesterTable(GetSemesterInfoResult result);

        void initSemesterGradesMapResult(List<Evaluation> evaluations);
    }


    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_GridDemo = new Type<RevealContentHandler<?>>();

    public static final Object SLOT_WIDGET_ELEMENT = new Object();

    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridPresenter> {
    }

    @Inject
    MenuPresenter menuPresenter;

    @Inject
    RestDispatch restDispatch;

    private DispatchAsync dispatcher;

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view,
                         DispatchAsync dispatchAsync,
                         MyProxy proxy,
                         ResourceDelegate<SemesterGradesResource> semesterGradesDelegate,
                         SemesterGradesResource semesterGradesResource) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatchAsync;
        this.semesterGradesDelegate = semesterGradesDelegate;

        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
        this.fetchSemesterMapData();
    }

    private void fetchSemesterMapData() {
//        dispatcher.execute(new GetSemesterGradesAction(3), new AsyncCallback<GetSemesterGradesMapResult>() {
//            @Override
//            public void onFailure(Throwable caught) {
//            }
//
//            @Override
//            public void onSuccess(GetSemesterGradesMapResult result) {
//                getView().initSemesterGradesMapResult(result.getEvaluationMapResult());
//                fetchSemesterInfo();
//            }
//        });

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
        dispatcher.execute(new GetSemesterInfoAction(3), new AsyncCallback<GetSemesterInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(GetSemesterInfoResult result) {
                getView().initSemesterTable(result);
            }
        });

    }


    @Override
    protected void onReveal() {
        super.onReveal();
        addToSlot(SLOT_WIDGET_ELEMENT, menuPresenter);
    }
}
