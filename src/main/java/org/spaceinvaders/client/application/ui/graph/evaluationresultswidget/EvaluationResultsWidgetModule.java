
package org.spaceinvaders.client.application.ui.graph.evaluationresultswidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.ui.graph.semesteroverviewwidget.SemesterOverviewWidgetModule;

public class EvaluationResultsWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(EvaluationResultsWidgetPresenter.class, EvaluationResultsWidgetPresenter.MyView.class, EvaluationResultsWidgetView.class);
        install(new SemesterOverviewWidgetModule());
    }
}
