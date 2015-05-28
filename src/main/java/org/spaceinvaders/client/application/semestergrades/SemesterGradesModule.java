package org.spaceinvaders.client.application.semestergrades;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SemesterGradesModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterGradesPresenter.class, SemesterGradesPresenter.MyView.class, SemesterGradesView.class,
                SemesterGradesPresenter.MyProxy.class);
    }
}
