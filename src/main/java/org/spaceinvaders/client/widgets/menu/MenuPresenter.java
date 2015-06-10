
package org.spaceinvaders.client.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.GQuery;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.events.HasLoginHandlers;
import org.spaceinvaders.client.events.LoginEvent;
import org.spaceinvaders.client.events.LoginEventHandler;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements LoginEventHandler, HasLoginHandlers, MenuUiHandlers {

    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setUserName(String userName);
        void changeTitle(String title);
    }

    private final String userName;
    private EventBus eventBus;

    @Inject
    MenuPresenter(EventBus eventBus,
                  MyView view,
                  @Assisted String userName) {
        super(eventBus, view);
        this.userName = userName;
//        view.setUserName(userName);
        this.eventBus = eventBus;
        getView().setUserName(userName);
    }

    @Override
    public void onLogin(LoginEvent event) {
        GWT.log("onLogin " + event.getUserName());
        GQuery.console.log("HELLOOOOO");
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }

    @Override
    public HandlerRegistration addUserLoginHandler(LoginEventHandler loginEventHandler, Object source) {
        //TODO HandlerRegistration & EventBus
        GWT.log("addUserLoginHandler :::::::::::::::::::");
        HandlerRegistration hr = getEventBus().addHandlerToSource(LoginEvent.TYPE, source, loginEventHandler);
        registerHandler(hr);
        return hr;
    }
}
