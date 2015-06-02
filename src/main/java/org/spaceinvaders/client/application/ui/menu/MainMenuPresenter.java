package org.spaceinvaders.client.application.ui.menu;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainMenuPresenter extends
        Presenter<MainMenuPresenter.MyView, MainMenuPresenter.MyProxy> implements MainMenuUiHandlers {
    public interface MyView extends View, HasUiHandlers {}

    @ProxyStandard
    public interface MyProxy extends Proxy<MainMenuPresenter> {
    }

    @Inject
    MainMenuPresenter(final EventBus eventBus, final MyView view,final MyProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
    }

    @Override
    public void disconnect()
    {
        Logger logger = Logger.getLogger("NameOfYourLogger");
        logger.log(Level.SEVERE, "this message should get logged");
        //MainMenuEvent.fire(this,"Disconnect");
    }


}
