package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import org.spaceinvaders.client.widgets.commons.WidgetsFactory;
import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuPresenter;
import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuView;

public class SemesterGradesModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SemesterGradesPresenter.class, SemesterGradesPresenter.MyView.class, SemesterGradesView.class,
                SemesterGradesPresenter.MyProxy.class);

//        bind(MaterialMenuPresenter.MyView.class).to(MaterialMenuView.class);
//        install(new GinFactoryModuleBuilder().build(WidgetsFactory.class));
    }
}
