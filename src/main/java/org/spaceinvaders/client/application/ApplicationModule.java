package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import org.spaceinvaders.client.application.ap.ApModule;
import org.spaceinvaders.client.application.error.ErrorModule;
import org.spaceinvaders.client.application.graphdemo.GraphDemoModule;
import org.spaceinvaders.client.application.widgets.grid.GridModule;
import org.spaceinvaders.client.application.semester.SemesterModule;
import org.spaceinvaders.client.application.widgets.graph.gwtchartwidget.GwtChartWidgetModule;


public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new SemesterModule());
        install(new GridModule());
        install(new GraphDemoModule());
        install(new GwtChartWidgetModule());
        install(new ApModule());
        install(new ErrorModule());
    }
}
