
package org.spaceinvaders.client.application.ui.graph.gwtchartwidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GwtChartWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(GwtChartWidgetPresenter.class, GwtChartWidgetPresenter.MyView.class, GwtChartWidgetView.class);
    }
}
