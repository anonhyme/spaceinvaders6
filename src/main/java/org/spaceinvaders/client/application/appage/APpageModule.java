package org.spaceinvaders.client.application.appage;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class APpageModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(APpagePresenter.class, APpagePresenter.MyView.class, APpageView.class, APpagePresenter.MyProxy.class);
    }
}
