package org.spaceinvaders.client.application.ap;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApPresenter.class, ApPresenter.MyView.class, ApView.class, ApPresenter.MyProxy.class);
    }
}
