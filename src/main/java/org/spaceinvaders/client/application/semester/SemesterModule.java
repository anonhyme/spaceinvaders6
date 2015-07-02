package org.spaceinvaders.client.application.semester;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import org.spaceinvaders.client.application.widgets.menu.MenuPresenter;
import org.spaceinvaders.client.application.widgets.menu.MenuView;
public class SemesterModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterPresenter.class, SemesterPresenter.MyView.class, SemesterView.class,
                SemesterPresenter.MyProxy.class);
    }
}
