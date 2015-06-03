package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.griddemo.GridDemoModule;
import org.spaceinvaders.client.application.semestergrades.SemesterGradesModule;
import org.spaceinvaders.client.widgets.grid.GridModule;


public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new GridDemoModule());
        install(new SemesterGradesModule());
        install(new GridModule());
    }
}
