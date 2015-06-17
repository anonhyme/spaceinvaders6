package org.spaceinvaders.client.application.appage;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.html.Text;

import javax.inject.Inject;


public class APpageView extends ViewImpl implements APpagePresenter.MyView {
    interface Binder extends UiBinder<Widget, APpageView> {
    }

    @UiField
    HTMLPanel gridPanel;

    @UiField
    HTMLPanel cumulativeChartPanel;

    @UiField
    HTMLPanel evaluationChartPanel;

    @UiField
    PageHeader pageTitle ;

    @UiField
    Button cumulativeButton;

    @UiField
    Button evaluationButton;



    @Inject
    APpageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        cumulativeButton.addClickHandler(new ClickHandler() {
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
        });
    }


    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == APpagePresenter.SLOT_APgrid) {
            gridPanel.add(content);
        }
        else if  (slot == APpagePresenter.SLOT_APCumulativeChart)
        {
            cumulativeChartPanel.add(content);

        }
        else if  (slot == APpagePresenter.SLOT_APEvaluationsChart)
        {
            evaluationChartPanel.add(content);
        }
        else {
            super.setInSlot(slot, content);
        }
    }

    public void setApName(String name){
        pageTitle.setText(name);

        Text placeholder = new Text();
        placeholder.setText("GRID GOES HERE");
        gridPanel.add(placeholder);
    }


    public void hideEvaluationChart(){
        evaluationChartPanel.getElement().setAttribute("hidden", "true");
    }

    public void showEvaluationChart(){
        evaluationChartPanel.getElement().removeAttribute("hidden");
    }

    public void hideCumulativeChart(){
        cumulativeChartPanel.getElement().setAttribute("hidden", "true");
    }

    public void showCumulativeChart(){
        cumulativeChartPanel.getElement().removeAttribute("hidden");
    }


}
