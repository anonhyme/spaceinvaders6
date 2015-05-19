package org.spaceinvaders.client.application.simplepage;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Button;

import javax.inject.Inject;


public class SimplePageView extends ViewImpl implements SimplePagePresenter.MyView {
    interface Binder extends UiBinder<Widget, SimplePageView> {
    }

    @UiField
    Label navigationHistory;

    @UiField
    Button testButton;

    @Inject
    SimplePageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

//    @Override
//    public void setInSlot(Object slot, IsWidget content) {
//        if (slot == SimplePagePresenter.SLOT_SimplePage) {
//            main.setWidget(content);
//        } else {
//            super.setInSlot(slot, content);
//        }
//    }
}
