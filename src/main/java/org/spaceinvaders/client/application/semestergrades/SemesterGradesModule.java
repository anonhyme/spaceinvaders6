package org.spaceinvaders.client.application.semestergrades;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.client.widgets.menu.MenuView;

public class SemesterGradesModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterGradesPresenter.class, SemesterGradesPresenter.MyView.class, SemesterGradesView.class,
                SemesterGradesPresenter.MyProxy.class);

        bind(MenuPresenter.MyView.class).to(MenuView.class);

    }
}
