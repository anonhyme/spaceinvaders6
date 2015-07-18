package org.spaceinvaders.client.application.ap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;

import javax.inject.Inject;
import java.text.DecimalFormat;

public class ApView extends ViewImpl implements ApPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApView> {
    }

    @UiField
    SimplePanel gridPanel;

    @UiField
    HTMLPanel cumulativeChartPanel;

    @UiField
    HTMLPanel evaluationChartPanel;

    @UiField
    HTMLPanel progressBarPanel;

    @UiField
    PageHeader pageTitle;

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

    public void showErrorMessage() {
        clearPannels();
        gridPanel.add(new Label("Aucun AP sélectionné. Veuillez sélectionner un AP dans la grille de session."));
    }

    public void clearPannels() {
        cumulativeChartPanel.clear();
        evaluationChartPanel.clear();
        gridPanel.clear();
        progressBarPanel.clear();
    }

}
