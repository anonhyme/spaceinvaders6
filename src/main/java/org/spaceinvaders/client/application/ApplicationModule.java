package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.graphdemo.GraphDemoModule;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetModule;
import org.spaceinvaders.client.application.home.HomeModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);


        install(new GraphDemoModule());
        install(new GraphWidgetModule());
 install(new HomeModule());
    }
}
