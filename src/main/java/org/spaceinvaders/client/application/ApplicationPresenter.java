package org.spaceinvaders.client.application;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.LockInteractionEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;

import javax.inject.Inject;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {

    public interface MyView extends View {
        void showLoading(boolean visibile);
    }

    private final DispatchAsync dispatcher;


    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_SetMainContent = new Type<>();

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy,
                         DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
    }

    @ProxyEvent
    public void onLockInteraction(LockInteractionEvent event) {
        getView().showLoading(event.shouldLock());
    }

    @Override
    protected void onBind() {
        super.onBind();
        dispatcher.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {
//                Window.alert("cip = " + result.getUserInfo().getCip());
            }
        });
    }
}
