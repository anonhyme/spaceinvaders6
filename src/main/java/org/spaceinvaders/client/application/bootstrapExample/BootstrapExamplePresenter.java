
package org.spaceinvaders.client.application.bootstrapExample;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.place.NameTokens;

public class BootstrapExamplePresenter extends Presenter<BootstrapExamplePresenter.MyView, BootstrapExamplePresenter.MyProxy> {
    public interface MyView extends View {
        void setNavigationHistory(String navigationHistory);
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_BootstrapExample = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.bootstrapExamplePage)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<BootstrapExamplePresenter> {
    }

    String navigationHistory = "";


    @Inject
    BootstrapExamplePresenter(EventBus eventBus,
                              MyView view,
                              MyProxy myProxy) {
        super(eventBus, view, myProxy, RevealType.Root);
//        getView().setUiHandlers(this);
    }

//    @Override
//    protected void revealInParent() {
//        RevealRootLayoutContentEvent.fire(this, this);
//    }

    @ProxyEvent
    public void onNavigation(NavigationEvent event) {
        if (navigationHistory.length() > 0) {
            navigationHistory += ", ";
        }
        navigationHistory += event.getRequest().getNameToken();
        getView().setNavigationHistory(navigationHistory);
    }


}
