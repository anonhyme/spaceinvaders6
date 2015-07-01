package org.spaceinvaders.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import org.spaceinvaders.client.application.grid.GridModule;
import org.spaceinvaders.client.application.menu.MenuPresenter;
import org.spaceinvaders.client.application.menu.MenuView;
import org.spaceinvaders.client.application.semester.SemesterModule;
import org.spaceinvaders.client.widgets.cell.CellModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        install(new SemesterModule());
        install(new GridModule());

        bind(MenuPresenter.MyView.class).to(MenuView.class);
        install(new CellModule());
    }
}
