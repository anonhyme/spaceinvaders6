package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.home.HomeModule;
import org.spaceinvaders.client.application.ui.menu.MainMenuModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new HomeModule());


        install(new MainMenuModule());
    }
}
