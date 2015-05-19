


package org.spaceinvaders.client.application.simplepage;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.place.NameTokens;

public class SimplePagePresenter extends Presenter<SimplePagePresenter.MyView, SimplePagePresenter.MyProxy> {
    interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_SimplePage = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.simplePage)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<SimplePagePresenter> {
    }

    @Inject
    public SimplePagePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);

    }
}
