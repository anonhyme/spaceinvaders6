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
import org.spaceinvaders.client.rpc.DataProviderServiceAsync;

import javax.inject.Inject;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }

//    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_HeaderPresenter = new GwtEvent.Type<>();
//    public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_bootstrapPresenter = new GwtEvent.Type<>();


//    BootstrapExamplePresenter bootstrapPresenter;


    private DataProviderServiceAsync helloService;
    private DispatchAsync dispatchAsync;


    @Inject
    HomePagePresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      DataProviderServiceAsync helloService,
                      DispatchAsync dispatchAsync
                      ) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.helloService = helloService;
        this.dispatchAsync = dispatchAsync;

//        this.bootstrapPresenter = bootstrapPresenter;

    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

//    @Override
//    protected void onBind() {
//        super.onBind();
//        setInSlot(SLOT_HeaderPresenter, headerPresenter);
////        setInSlot(SLOT_bootstrapPresenter, bootstrapPresenter);
//
////        helloService.sayHello(new AsyncCallback<HelloRPC>() {
////            @Override
////            public void onFailure(Throwable caught) {
////
////            }
////
////            @Override
////            public void onSuccess(HelloRPC result) {
////
////                GWT.log("got [" + result.getResponse() + "]");
////
////            }
////        });
//    }
}
