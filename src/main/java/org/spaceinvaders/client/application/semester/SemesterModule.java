package org.spaceinvaders.client.application.semester;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SemesterModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterPresenter.class, SemesterPresenter.MyView.class, SemesterView.class,
                SemesterPresenter.MyProxy.class);
    }
}
