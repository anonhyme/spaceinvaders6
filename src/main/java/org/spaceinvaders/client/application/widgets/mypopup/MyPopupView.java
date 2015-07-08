
package org.spaceinvaders.client.application.widgets.mypopup;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class MyPopupView extends PopupViewWithUiHandlers<MyPopupUiHandlers> implements MyPopupPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, MyPopupView> {
    }

    @Inject
    MyPopupView(Binder uiBinder, EventBus eventBus) {
        super(eventBus);

        initWidget(uiBinder.createAndBindUi(this));
    }
}
