package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.appage.APpageModule;
import org.spaceinvaders.client.application.graphdemo.GraphDemoModule;
import org.spaceinvaders.client.application.griddemo.GridDemoModule;
import org.spaceinvaders.client.application.semestergrades.SemesterGradesModule;
import org.spaceinvaders.client.application.ui.graph.gwtchartwidget.GwtChartWidgetModule;


public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class,
                ApplicationPresenter.MyView.class,
                ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new SemesterGradesModule());
        install(new GraphDemoModule());
        install(new GwtChartWidgetModule());
        install(new GridDemoModule());
        install(new APpageModule());
    }
}
