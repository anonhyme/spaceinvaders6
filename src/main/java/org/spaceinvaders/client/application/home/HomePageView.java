package org.spaceinvaders.client.application.home;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;


import javax.inject.Inject;

public class HomePageView extends ViewImpl implements HomePagePresenter.MyView {

    public interface Binder extends UiBinder<Widget, HomePageView> {

    }

//    @UiField
//    SimplePanel mainContent;

//    @UiField
//    SimplePanel bootstrapContent;

    @Inject
    HomePageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }


    // GWTP will call setInSlot when a child presenter asks to be added under
    // this view.
//    @Override
//    public void setInSlot(Object slot, IsWidget content) {
//        if (slot == HomePagePresenter.SLOT_HeaderPresenter) {
//            mainContent.setWidget(content);
//        } else if (slot == HomePagePresenter.SLOT_bootstrapPresenter) {
////            bootstrapContent.setWidget(content);
//        }
//    }
}
