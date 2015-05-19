
package org.spaceinvaders.client.application.bootstrapExample;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BootstrapExampleModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(BootstrapExamplePresenter.class, BootstrapExamplePresenter.MyView.class, BootstrapExampleView.class, BootstrapExamplePresenter.MyProxy.class);
    }
}
