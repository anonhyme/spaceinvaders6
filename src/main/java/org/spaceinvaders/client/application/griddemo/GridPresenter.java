package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;


public class GridPresenter extends Presenter<GridPresenter.MyView, GridPresenter.MyProxy> implements GridUiHandlers {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void initSemesterTable(GetSemesterInfoResult result);

        void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult);
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_GridDemo = new Type<RevealContentHandler<?>>();

    public static final Object SLOT_WIDGET_ELEMENT = new Object();

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridPresenter> {
    }

    @Inject
    MenuPresenter menuPresenter;

    private DispatchAsync dispatcher;

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view,
                         DispatchAsync dispatchAsync,
                         MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatchAsync;

        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
        this.fetchSemesterData();
    }

    private void fetchSemesterData() {
        dispatcher.execute(new GetSemesterGradesAction(3), new AsyncCallback<GetSemesterGradesResult>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(GetSemesterGradesResult result) {
                getView().initSemesterGradesResult(result);
                fetchSemesterInfo();
            }
        });
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
