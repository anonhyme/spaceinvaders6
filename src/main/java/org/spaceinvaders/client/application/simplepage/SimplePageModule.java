package org.spaceinvaders.client.application.simplepage;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SimplePageModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SimplePagePresenter.class, SimplePagePresenter.MyView.class, SimplePageView.class, SimplePagePresenter.MyProxy.class);
    }
}
