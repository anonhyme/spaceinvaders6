


package org.spaceinvaders.client.application.examplerpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.rpc.ExampleServiceAsync;
import org.spaceinvaders.shared.model.ExampleRPC;

public class ExampleRpcPresenter extends Presenter<ExampleRpcPresenter.MyView, ExampleRpcPresenter.MyProxy> implements ExampleRpcUiHandlers {
    interface MyView extends View, HasUiHandlers<ExampleRpcUiHandlers> {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_ExampleRpc = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.exampleRpc)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<ExampleRpcPresenter> {
    }

    ExampleServiceAsync exampleService;

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }
    @Inject
    public ExampleRpcPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ExampleServiceAsync exampleService) {
        super(eventBus, view, proxy, RevealType.Root);

        this.exampleService = exampleService;
        getView().setUiHandlers(this);
    }

    protected void onBind() {
        super.onBind();
    }

    protected void onHide() {
        super.onHide();
    }

    protected void onUnbind() {
        super.onUnbind();
    }

    protected void onReset() {
        super.onReset();
    }

    @Override
    public void sendRequestToServer() {
        exampleService.sayHello(new AsyncCallback<ExampleRPC>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(ExampleRPC result) {
                GWT.log("got [" + result.getResponse() + "]");

            }
        });
    }
}
