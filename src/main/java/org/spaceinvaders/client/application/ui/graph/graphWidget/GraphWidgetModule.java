
package org.spaceinvaders.client.application.ui.graph.graphWidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GraphWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(GraphWidgetPresenter.class, GraphWidgetPresenter.MyView.class, GraphWidgetView.class);
    }
}
