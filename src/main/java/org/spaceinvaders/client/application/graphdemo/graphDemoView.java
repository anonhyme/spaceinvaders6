package org.spaceinvaders.client.application.graphdemo;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewImpl;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetPresenter;

import javax.inject.Inject;


public class GraphDemoView extends ViewImpl implements GraphDemoPresenter.MyView {
    interface Binder extends UiBinder<Widget, GraphDemoView> {
    }

    @UiField
    HTMLPanel main;


    @Inject
    GraphDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == GraphDemoPresenter.SLOT_graphDemo) {
            main.add(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    public void setGraph(GraphWidgetPresenter presenter) {
        main.setHeight("100");
        main.setWidth("100");
        main.add(presenter);
    }

}
