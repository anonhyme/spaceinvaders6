package org.spaceinvaders.client.application.restpage;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RestPageModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(RestPagePresenter.class, RestPagePresenter.MyView.class, RestPageView.class, RestPagePresenter.MyProxy.class);
    }
}
