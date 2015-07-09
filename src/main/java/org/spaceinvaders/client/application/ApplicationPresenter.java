package org.spaceinvaders.client.application;


import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.LockInteractionEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import org.spaceinvaders.client.application.events.RevealPresenterEvent;
import org.spaceinvaders.client.application.widgets.menu.MenuPresenter;
import org.spaceinvaders.client.resources.BootstrapJQueryJs;

import javax.inject.Inject;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
        implements RevealPresenterEvent.RevealPresenterHandler{

    public interface MyView extends View {
        void showLoading(boolean visibile);

        void addMenu(IsWidget menu);
    }

    @Inject
    BootstrapJQueryJs bootstrapJQueryJs;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_SetMainContent = new Type<>();

    public final MenuPresenter menuPresenter;

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy, MenuPresenter menuPresenter) {
        super(eventBus, view, proxy, RevealType.Root);
        this.menuPresenter = menuPresenter;

        ScriptInjector.fromString(BootstrapJQueryJs.INSTANCE.toString())
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();
        getView().addMenu(menuPresenter);
    }

    @ProxyEvent
    public void onLockInteraction(LockInteractionEvent event) {
        getView().showLoading(event.shouldLock());
    }

    @Override
    protected void onBind() {
        super.onBind();
    }

    @Override
    public void onRevealPresenter(RevealPresenterEvent event) {
        getView().setInSlot(SLOT_SetMainContent, event.getPresenterWidget());
    }
}
