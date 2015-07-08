
package org.spaceinvaders.client.application.widgets.mypopup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class MyPopupPresenter extends PresenterWidget<MyPopupPresenter.MyView> implements MyPopupUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<MyPopupUiHandlers> {
    }

    @Inject
    MyPopupPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

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

}
