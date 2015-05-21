
package org.spaceinvaders.client.application.ui.graph.graphWidget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class GraphWidgetView extends ViewImpl implements GraphWidgetPresenter.MyView {
    public interface Binder extends UiBinder<HTMLPanel, GraphWidgetView> {
    }

    @UiField
    HTMLPanel htmlPanel;

    @Inject
    GraphWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }


    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == GraphWidgetPresenter.TYPE_basicChart) {
            htmlPanel.add(content.asWidget());
        }
    }

    public void setChart(IsWidget content) {
        // RootPanel.get().add(content.asWidget());
        htmlPanel.add(content.asWidget());
    }
}
