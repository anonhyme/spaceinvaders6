package org.spaceinvaders.client.application.ap;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;

import javax.inject.Inject;

public class ApView extends ViewImpl implements ApPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApView> {
    }

    @UiField
    PageHeader pageTitle;

    @UiField
    SimplePanel gridPanel;

    @UiField
    HTMLPanel cumulativeChartPanel;

    @UiField
    HTMLPanel evaluationChartPanel;


    @UiField
    ProgressBar studentProgressBar;

    @UiField
    ProgressBar classProgressBar;

    @Inject
    ApView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addCumulativeChart(IsWidget chart) {
        cumulativeChartPanel.clear();
        cumulativeChartPanel.add(chart);
    }

    public void addEvaluationChart(IsWidget chart) {
        evaluationChartPanel.clear();
        evaluationChartPanel.add(chart);
    }

    @Override
    public void addGrid(IsWidget grid) {
        gridPanel.clear();
        gridPanel.add(grid);
    }

    public void setApName(String name) {
        pageTitle.setText(name);
    }

    public void setStudentProgressBar(double value, String color) {
        //remove decimal places
        double v = Math.floor(value);

        studentProgressBar.setPercent(v);
        studentProgressBar.getElement().getStyle().setProperty("backgroundColor", color);
        studentProgressBar.getElement().getStyle().setProperty("backgroundImage", "none");

        studentProgressBar.setText("Votre complétion : " + v + "%");
    }

    public void setClassProgressBar(double value, String color) {
        //remove decimal places
        double v = Math.floor(value);

        classProgressBar.setPercent( v);

        classProgressBar.getElement().getStyle().setProperty("backgroundColor", color);
        classProgressBar.getElement().getStyle().setProperty("backgroundImage", "none");

        classProgressBar.setText("Avancement du cours : " + v + "%");
    }
}
