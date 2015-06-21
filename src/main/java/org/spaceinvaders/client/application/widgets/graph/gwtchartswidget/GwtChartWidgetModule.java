
package org.spaceinvaders.client.application.widgets.graph.gwtchartswidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GwtChartWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(GwtChartWidgetPresenter.class, GwtChartWidgetPresenter.MyView.class, GwtChartWidgetView.class);
    }
}
