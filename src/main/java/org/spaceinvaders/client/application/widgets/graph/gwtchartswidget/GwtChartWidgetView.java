
package org.spaceinvaders.client.application.widgets.graph.gwtchartswidget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Container;

public class GwtChartWidgetView extends ViewImpl implements GwtChartWidgetPresenter.MyView {
    public interface Binder extends UiBinder<Widget, GwtChartWidgetView> {
    }

    @UiField
    HTMLPanel panel;

    @UiField
    Container chartContainer;

    @Inject
    GwtChartWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    public void setChart(Widget chart) {
        panel.clear();
        chartContainer.clear();
        panel.add(chart);
        chartContainer.add(panel);
    }
}
