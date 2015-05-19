package org.spaceinvaders.client.application.examplerpc;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ExampleRpcModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ExampleRpcPresenter.class, ExampleRpcPresenter.MyView.class, ExampleRpcView.class, ExampleRpcPresenter.MyProxy.class);
    }
}
