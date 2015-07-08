package org.spaceinvaders.client.application.ap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.gwtbootstrap3.client.ui.html.Text;
import org.spaceinvaders.shared.dto.Competence;

import javax.inject.Inject;
import java.util.List;


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
        cumulativeChartPanel.add(chart);
    }

    public void addEvaluationChart(IsWidget chart) {
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

    public void setStudentProgressBar(float value, String color) {
        studentProgressBar.setPercent(value);

        studentProgressBar.getElement().getStyle().setProperty("backgroundColor", color);
        studentProgressBar.getElement().getStyle().setProperty("backgroundImage", "none");

        studentProgressBar.setText("Votre complétion : " + value + "%");
    }

    public void setClassProgressBar(float value, String color) {
        classProgressBar.setPercent(value);

        classProgressBar.getElement().getStyle().setProperty("backgroundColor", color);
        classProgressBar.getElement().getStyle().setProperty("backgroundImage", "none");

        classProgressBar.setText("Avancement du cours : " + value + "%");
    }
}
