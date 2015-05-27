
package org.spaceinvaders.client.application.ui.graph.evaluationresultswidget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class EvaluationResultsWidgetView extends ViewImpl implements EvaluationResultsWidgetPresenter.MyView {
    public interface Binder extends UiBinder<HTMLPanel, EvaluationResultsWidgetView> {
    }

    @UiField
    HTMLPanel panel;

    @Inject
    EvaluationResultsWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    public void setChart(IsWidget content) {
        panel.clear();
        panel.add(content.asWidget());
    }
}
