
package org.spaceinvaders.client.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.events.HideMenuEvent;
import org.spaceinvaders.client.events.LoginEvent;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements LoginEvent.LoginHandler, HideMenuEvent.HideMenuHandler ,HasLoginHandlers, MenuUiHandlers {

    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setUserName(String userName);
        void changeTitle(String title);
    }

    private final String userName;

    @Inject
    MenuPresenter(EventBus eventBus,
                  MyView view,
                  @Assisted String userName) {
        super(eventBus, view);
        this.userName = userName;
        view.setUserName(userName);
    }

    @Override
    public void onLogin(LoginEvent event) {
        GWT.log("onLogin " + event.getUserName());
    }


    @Override
    public HandlerRegistration addUserLoginHandler(LoginEvent.LoginHandler loginHandler, Object source) {
        //TODO HandlerRegistration & EventBus
        GWT.log("addUserLoginHandler :::::::::::::::::::");
        HandlerRegistration hr = getEventBus().addHandlerToSource(LoginEvent.TYPE, source, loginHandler);
        registerHandler(hr);
        return hr;
    }

    @Override
    public void onHideMenu(HideMenuEvent event) {
        GWT.log("onHideMenu ::::::::::::");
        getView().changeTitle("SpaceInvaders");
    }

    @Override
    public HandlerRegistration addHideMenuHandler(HideMenuEvent.HideMenuHandler hideMenuHandler, Object source) {
        GWT.log("addHideMenuHandler :::::::::::::::::::");
        HandlerRegistration hr = getEventBus().addHandlerToSource(HideMenuEvent.TYPE, source, hideMenuHandler);
        registerHandler(hr);
        return hr;
    }


}
