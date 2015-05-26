
package org.spaceinvaders.client.application.ui.graph.cumulativegradelinechartwidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CumulativeGradeLineChartWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(CumulativeGradeLineChartWidgetPresenter.class, CumulativeGradeLineChartWidgetPresenter.MyView.class, CumulativeGradeLineChartWidgetView.class);
    }
}
