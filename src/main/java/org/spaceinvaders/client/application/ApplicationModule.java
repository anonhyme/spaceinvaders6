package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.grid.GridModule;
import org.spaceinvaders.client.application.restpage.RestPageModule;
import org.spaceinvaders.client.application.semestergrades.SemesterGradesModule;
import org.spaceinvaders.client.application.ui.graph.gwtchartwidget.GwtChartWidgetModule;


public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new GridModule());
        install(new SemesterGradesModule());
        install(new RestPageModule());
    }
}
