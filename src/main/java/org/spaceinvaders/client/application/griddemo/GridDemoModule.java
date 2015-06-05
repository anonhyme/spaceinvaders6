package org.spaceinvaders.client.application.griddemo;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GridDemoModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(GridDemoPresenter.class, GridDemoPresenter.MyView.class, GridDemoView.class, GridDemoPresenter.MyProxy.class);
    }
}
