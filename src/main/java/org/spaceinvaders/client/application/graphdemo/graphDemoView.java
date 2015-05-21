package org.spaceinvaders.client.application.graphdemo;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewImpl;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetPresenter;

import javax.inject.Inject;


public class GraphDemoView extends ViewImpl implements GraphDemoPresenter.MyView {
    interface Binder extends UiBinder<Widget, GraphDemoView> {
    }

    @UiField
    HTMLPanel main;

    @UiField
    Column col1;

    @UiField
    Column col2;

    @UiField
    Column col3;

    @UiField
    Column col4;

    @UiField
    Column col5;

    @UiField
    Column col6;

    @UiField
    ProgressBar studentProgress;

    @UiField
    ProgressBar classProgress;


    @Inject
    GraphDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        main.getElement().setAttribute("style", "overflow:scroll;");
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
    }

    public void setCol1(GraphWidgetPresenter presenter) {
        col1.add(presenter);
    }

    public void setCol2(GraphWidgetPresenter presenter) {
        col2.add(presenter);
    }

    public void setCol3(GraphWidgetPresenter presenter) {
        col3.add(presenter);
    }

    public void setCol4(GraphWidgetPresenter presenter) {
        col4.add(presenter);
    }

    public void setCol5(GraphWidgetPresenter presenter) {
        col5.add(presenter);
    }

    public void setCol6(GraphWidgetPresenter presenter) {
        col6.add(presenter);
    }

   public void setStudentProgress(double progress){
        studentProgress.setPercent(progress);
       studentProgress.setText("Complétion étuudiante : " + progress + "%");
    }
    public void setClassProgress(double progress){
        classProgress.setPercent(progress);
        classProgress.setText("Avancement du cours : " + progress + "%");
    }

}
