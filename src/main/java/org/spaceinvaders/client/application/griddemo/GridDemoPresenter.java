


package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
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
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;

public class GridDemoPresenter extends Presenter<GridDemoPresenter.MyView, GridDemoPresenter.MyProxy> implements GridDemoUiHandlers {

    interface MyView extends View, HasUiHandlers<GridDemoUiHandlers> {

        void initSemesterTable(GetSemesterInfoResult result);

        void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult);

//        void showModalInfo(String info);
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_GridDemo = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridDemoPresenter> {
    }

    //    private SemesterServiceAsync exampleService;
    private DispatchAsync dispatcher;

    @Inject
    public GridDemoPresenter(
            EventBus eventBus,
            MyView view,
            DispatchAsync dispatchAsync,
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatchAsync;
        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
        fetchSemesterInfo();
        GWT.log("onBind ");
    }

    @Override
    public void fetchSemesterInfo() {
        GWT.log("Async call fetchSemesterData");

        this.dispatcher.execute(new GetSemesterInfoAction("bedh2102", 3), new AsyncCallback<GetSemesterInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetSemesterInfoResult result) {
                getView().initSemesterTable(result);
            }
        });

        this.dispatcher.execute(new GetSemesterGradesAction("bedh2102", 3), new AsyncCallback<GetSemesterGradesResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetSemesterGradesResult result) {
                getView().initSemesterGradesResult(result);
            }
        });
    }
}
