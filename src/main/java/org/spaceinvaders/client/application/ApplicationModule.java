package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.appage.APpageModule;
import org.spaceinvaders.client.application.graphdemo.GraphDemoModule;
import org.spaceinvaders.client.application.grid.GridModule;
import org.spaceinvaders.client.application.semester.SemesterModule;
import org.spaceinvaders.client.application.ui.graph.gwtchartwidget.GwtChartWidgetModule;


public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new SemesterModule());
        install(new GridModule());
        install(new GraphDemoModule());
        install(new GwtChartWidgetModule());
        install(new APpageModule());
    }
}
