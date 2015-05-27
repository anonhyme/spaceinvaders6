package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.graphdemo.GraphDemoModule;
import org.spaceinvaders.client.application.home.SemesterGradesModule;
import org.spaceinvaders.client.application.ui.graph.cumulativegradelinechartwidget.CumulativeGradeLineChartWidgetModule;
import org.spaceinvaders.client.application.ui.graph.evaluationresultswidget.EvaluationResultsWidgetModule;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class,
                ApplicationPresenter.MyView.class,
                ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new SemesterGradesModule());
        install(new GraphDemoModule());
        install(new GraphWidgetModule());
        install(new CumulativeGradeLineChartWidgetModule());
        install(new EvaluationResultsWidgetModule());
    }
}
