package org.spaceinvaders.client.application.ap;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.gwtbootstrap3.client.ui.html.Text;

import javax.inject.Inject;

public class ApView extends ViewImpl implements ApPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApView> {
    }

    @UiField
    HTMLPanel gridPanel;

    @UiField
    HTMLPanel cumulativeChartPanel;

    @UiField
    HTMLPanel evaluationChartPanel;

    @UiField
    PageHeader pageTitle;

   /* @UiField
    Button cumulativeButton;

    @UiField
    Button evaluationButton;*/

    @UiField
    ProgressBar studentProgressBar;

    @UiField
    ProgressBar classProgressBar;


    @Inject
    ApView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

      /*  cumulativeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideEvaluationChart();
                showCumulativeChart();

            }
        });

        evaluationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideCumulativeChart();
                showEvaluationChart();
            }
        });*/
    }


    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApPresenter.SLOT_APgrid) {
            gridPanel.add(content);
        } else if (slot == ApPresenter.SLOT_APCumulativeChart) {
            cumulativeChartPanel.add(content);

        } else if (slot == ApPresenter.SLOT_APEvaluationsChart) {
            evaluationChartPanel.add(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    public void setApName(String name) {
        pageTitle.setText(name);

        Text placeholder = new Text();
        placeholder.setText("GRID GOES HERE");
        gridPanel.add(placeholder);
    }


    public void hideEvaluationChart() {
        evaluationChartPanel.getElement().setAttribute("hidden", "true");
    }

    public void showEvaluationChart() {
        evaluationChartPanel.getElement().removeAttribute("hidden");
    }

    public void hideCumulativeChart() {
        cumulativeChartPanel.getElement().setAttribute("hidden", "true");
    }

    public void showCumulativeChart() {
        cumulativeChartPanel.getElement().removeAttribute("hidden");
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
