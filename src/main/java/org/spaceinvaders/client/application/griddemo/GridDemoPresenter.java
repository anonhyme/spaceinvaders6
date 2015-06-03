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
import org.spaceinvaders.client.widgets.commons.WidgetsFactory;
import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuPresenter;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;

public class GridDemoPresenter extends Presenter<GridDemoPresenter.MyView, GridDemoPresenter.MyProxy> implements GridDemoUiHandlers {

    interface MyView extends View, HasUiHandlers<GridDemoUiHandlers> {
        void initSemesterTable(GetSemesterInfoResult result);

        void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult);
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_GridDemo = new Type<RevealContentHandler<?>>();

    public static final Object SLOT_WIDGET_ELEMENT = new Object();

    @NameToken(NameTokens.gridDemo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<GridDemoPresenter> {
    }

    private DispatchAsync dispatcher;

    private final WidgetsFactory widgetsFactory;

    private String userName = "";

    @Inject
    public GridDemoPresenter(
            EventBus eventBus,
            MyView view,
            DispatchAsync dispatchAsync,
            MyProxy proxy,
            WidgetsFactory widgetsFactory) {
        super(eventBus, view, proxy, RevealType.Root);

        this.dispatcher = dispatchAsync;
        this.widgetsFactory = widgetsFactory;

        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
        fetchSemesterInfo();
    }

    @Override
    public void fetchSemesterInfo() {
        GWT.log("Async call fetchSemesterData");

        dispatcher.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {
                GWT.log(result.getUserInfo().getCip());
                setUserName(result.getUserInfo().getCip());
            }
        });

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

    private void setUserName(String userName) {
        GWT.log("setUserName ::::::::: " + userName);
        MaterialMenuPresenter materialMenuPresenter = widgetsFactory.createTopMenu(userName);
        addToSlot(SLOT_WIDGET_ELEMENT, materialMenuPresenter);
    }

    private String getUserName(){
        return this.userName;
    }
}
