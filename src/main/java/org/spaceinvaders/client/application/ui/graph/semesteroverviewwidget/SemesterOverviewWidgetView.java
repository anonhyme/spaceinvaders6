
package org.spaceinvaders.client.application.ui.graph.semesteroverviewwidget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SemesterOverviewWidgetView extends ViewImpl implements SemesterOverviewWidgetPresenter.MyView {
    public interface Binder extends UiBinder<HTMLPanel, SemesterOverviewWidgetView> {
    }

    @UiField
    HTMLPanel panel;

    @Inject
    SemesterOverviewWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }


    public void setChart(IsWidget content) {
        panel.clear();
        panel.add(content.asWidget());
    }
}
