
package org.spaceinvaders.client.application.widgets.graph.gwtchartswidget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class GwtChartWidgetView extends ViewImpl implements GwtChartWidgetPresenter.MyView {
    public interface Binder extends UiBinder<HTMLPanel, GwtChartWidgetView> {
    }

    @UiField
    HTMLPanel panel;

    @Inject
    GwtChartWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    public void setChart(Widget chart) {
        panel.clear();
        panel.add(chart);
    }
}
