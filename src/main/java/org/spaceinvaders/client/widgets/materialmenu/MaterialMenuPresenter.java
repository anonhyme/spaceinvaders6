
package org.spaceinvaders.client.widgets.materialmenu;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.events.LoginEvent;
import org.spaceinvaders.client.events.LoginEventHandler;

public class MaterialMenuPresenter extends PresenterWidget<MaterialMenuPresenter.MyView> implements LoginEventHandler, HasLoginHandlers, MaterialMenuUiHandlers {

    public interface MyView extends View, HasUiHandlers<MaterialMenuUiHandlers> {
        void setUserName(String userName);
    }

    private final String userName;

    @Inject
    MaterialMenuPresenter(EventBus eventBus,
                          MyView view,
                          @Assisted String userName) {
        super(eventBus, view);
        this.userName = userName;
        view.setUserName(userName);
    }

    @Override
    public void onLogin(LoginEvent event) {
        GWT.log("onLogin " + event.getUserName());
//        getView().setUserName(event.getUserName());
    }

    @Override
    public HandlerRegistration addUserLoginHandler(LoginEventHandler handler, Object source) {
        //TODO HandlerRegistration & EventBus
        HandlerRegistration hr = getEventBus().addHandlerToSource(LoginEvent.TYPE, source, handler);
        registerHandler(hr);
        return hr;
    }
}
