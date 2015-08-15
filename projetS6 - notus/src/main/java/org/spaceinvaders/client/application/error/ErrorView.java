package org.spaceinvaders.client.application.error;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import javax.inject.Inject;


public class ErrorView extends ViewImpl implements ErrorPresenter.MyView {
    interface Binder extends UiBinder<Widget, ErrorView> {
    }

    @UiField
    HTMLPanel main;

    @Inject
    ErrorView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ErrorPresenter.SLOT_Error) {
            main.add(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
