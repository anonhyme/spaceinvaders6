package org.spaceinvaders.client.application.graphdemo;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GraphDemoModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(GraphDemoPresenter.class, GraphDemoPresenter.MyView.class, GraphDemoView.class, GraphDemoPresenter.MyProxy.class);
    }
}
