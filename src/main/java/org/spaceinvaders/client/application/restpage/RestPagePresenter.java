



package org.spaceinvaders.client.application.restpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dispatch.UserInfo;

public class RestPagePresenter extends Presenter<RestPagePresenter.MyView, RestPagePresenter.MyProxy> {
    interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_RestPage = new Type<RevealContentHandler<?>>();

    private final ResourceDelegate<UserInfoResource> userInfoDelegate;

    @NameToken(NameTokens.lolololo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<RestPagePresenter> {
    }

    @Inject
    public RestPagePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<UserInfoResource> userInfoDelegate) {
        super(eventBus, view, proxy, RevealType.Root);

        this.userInfoDelegate = userInfoDelegate;
    }

    @Override
    protected void onBind() {
        super.onBind();

        GWT.log("hello");

        userInfoDelegate
                .withCallback(new AbstractAsyncCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
                        GWT.log("Rest cip = " + result.getCip());
                    }
                }).get();
    }
}







