package org.spaceinvaders.client.application.home;

import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.place.NameTokens;

import javax.inject.Inject;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }

    private DispatchAsync dispatchAsync;

    @Inject
    HomePagePresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      DispatchAsync dispatchAsync
    ) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.dispatchAsync = dispatchAsync;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }
}
