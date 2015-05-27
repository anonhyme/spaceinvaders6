
package org.spaceinvaders.client.application.ui.graph.semesteroverviewwidget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SemesterOverviewWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(SemesterOverviewWidgetPresenter.class, SemesterOverviewWidgetPresenter.MyView.class, SemesterOverviewWidgetView.class);
    }
}
