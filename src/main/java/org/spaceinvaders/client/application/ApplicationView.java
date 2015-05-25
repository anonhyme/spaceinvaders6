package org.spaceinvaders.client.application;

import javax.inject.Inject;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    Element loadingMessage;

    @UiField
    SimplePanel mainContentPanel;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.SLOT_SetMainContent) {
            mainContentPanel.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    @Override
    public void showLoading(boolean visibile) {
        loadingMessage.getStyle().setVisibility(visibile ? Style.Visibility.VISIBLE : Style.Visibility.HIDDEN);
    }
}
