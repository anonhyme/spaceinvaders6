package org.spaceinvaders.client.application.home;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.ui.graph.cumulativegradelinechartwidget.CumulativeGradeLineChartWidgetModule;

public class HomeModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(HomePagePresenter.class, HomePagePresenter.MyView.class, HomePageView.class,
                HomePagePresenter.MyProxy.class);
        install(new CumulativeGradeLineChartWidgetModule());
    }
}
