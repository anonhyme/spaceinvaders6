package org.spaceinvaders.client.application.examplerpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Button;
import org.spaceinvaders.client.application.ui.grid.Grid;

import javax.inject.Inject;


public class ExampleRpcView extends ViewWithUiHandlers<ExampleRpcUiHandlers> implements ExampleRpcPresenter.MyView {
    interface Binder extends UiBinder<Widget, ExampleRpcView> {
    }

    //    @UiField
//    SimplePanel main;
    @UiField
    Button sendButton;

    @Inject
    ExampleRpcView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }


    @UiHandler("sendButton")
    void onClick(ClickEvent event) {
        getUiHandlers().sendRequestToServer();
    }

    //Method called after the view is attached to the DOM.
    @Override
    protected void onAttach() {
        super.onAttach();
        sendButton.setText("Button");
    }
}
