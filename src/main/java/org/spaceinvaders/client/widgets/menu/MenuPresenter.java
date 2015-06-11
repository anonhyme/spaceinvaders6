
package org.spaceinvaders.client.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.events.HasLoginHandlers;
import org.spaceinvaders.client.events.LoginEvent;
import org.spaceinvaders.client.events.LoginEventHandler;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;

@Singleton
public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements LoginEventHandler, HasLoginHandlers, MenuUiHandlers {

    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setUserName(String userName);
    }

    private EventBus eventBus;
    private DispatchAsync dispatchAsync;
    private String userName;

    @Inject
    MenuPresenter(EventBus eventBus,
                  MyView view, DispatchAsync dispatchAsync) {
        super(eventBus, view);
        this.eventBus = eventBus;
        this.dispatchAsync = dispatchAsync;
        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();
        fetchUserInfo();
    }

    @Override
    public void onLogin(LoginEvent event) {
        getView().setUserName(event.getUserName());
    }


    @Override
    public HandlerRegistration addUserLoginHandler(LoginEventHandler loginEventHandler, Object source) {
        //TODO HandlerRegistration & EventBus
        //TODO Check for an alternative handlerRegistration
        GWT.log("UserLoginHandler added");
        HandlerRegistration hr = getEventBus().addHandlerToSource(LoginEvent.TYPE, source, loginEventHandler);
        registerHandler(hr);
        return hr;
    }

    private void fetchUserInfo() {
        dispatchAsync.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Something went wrong when retrieving user info..." + caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {
//                setUserName(result.getUserInfo().getCip());
                getView().setUserName(result.getUserInfo().getCip());
            }
        });
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void disconnect() {
        Window.alert("The Presenter says Hi !");
        Window.alert("But it's stupid to make this alert in the presenter !");
    }
}
