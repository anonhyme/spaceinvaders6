package org.spaceinvaders.client.application.semester;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.PageHeader;

import javax.inject.Inject;

public class SemesterView extends ViewImpl implements SemesterPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SemesterView> {
    }

    @UiField
    SimplePanel gridPanel;

    @UiField
    HTMLPanel semesterChartPanel;

    @UiField
    PageHeader pageTitle;

    @Inject
    SemesterView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addGrid(IsWidget gridWidget) {
        gridPanel.clear();
        gridPanel.add(gridWidget);
    }

    public void updateSemesterChart(IsWidget semesterChart) {
        semesterChartPanel.clear();
        semesterChartPanel.add(semesterChart);
    }

    public void showNoGradesMessage() {
        clearPannels();
        semesterChartPanel.add(new Label("Aucun résultat pour cette session."));
    }

    public void clearPannels(){
        gridPanel.clear();
        semesterChartPanel.clear();
    }

    @Override
    public void updateTitle(String semesterId) {
        pageTitle.setSubText("Note pour la Session " + semesterId);
    }
}