package org.spaceinvaders.client.application.resulttable;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;


public class ResultTableView extends ViewWithUiHandlers<ResultTableUiHandlers> implements ResultTablePresenter.MyView {
    interface Binder extends UiBinder<Widget, ResultTableView> {
    }

    @UiField
    SimplePanel main;

    @Inject
    ResultTableView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ResultTablePresenter.SLOT_ResultTable) {
            main.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
