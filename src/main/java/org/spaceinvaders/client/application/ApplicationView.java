package org.spaceinvaders.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Popover;
import org.spaceinvaders.client.resources.BootstrapJQueryJs;

import javax.inject.Inject;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    Element loadingMessage;

    @UiField
    HTMLPanel menuPanel;

    @UiField
    SimplePanel mainContentPanel;



    @Inject
    BootstrapJQueryJs js;

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

    @Override
    public void addMenu(IsWidget menu) {
        menuPanel.add(menu);
    }


}
