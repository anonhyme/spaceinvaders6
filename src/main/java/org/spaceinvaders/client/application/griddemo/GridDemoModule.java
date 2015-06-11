package org.spaceinvaders.client.application.griddemo;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GridDemoModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(GridPresenter.class, GridPresenter.MyView.class, GridView.class, GridPresenter.MyProxy.class);
    }
}
