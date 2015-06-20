package org.spaceinvaders.client.application.semester;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import org.spaceinvaders.client.application.menu.MenuPresenter;
import org.spaceinvaders.client.application.menu.MenuView;

public class SemesterModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterPresenter.class, SemesterPresenter.MyView.class, SemesterView.class,
                SemesterPresenter.MyProxy.class);

        bind(MenuPresenter.MyView.class).to(MenuView.class);

    }
}