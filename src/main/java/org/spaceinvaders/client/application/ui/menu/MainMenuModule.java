package org.spaceinvaders.client.application.ui.menu;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MainMenuModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(MainMenuPresenter.class, MainMenuPresenter.MyView.class, MainMenuView.class, MainMenuPresenter.MyProxy.class);
    }
}
