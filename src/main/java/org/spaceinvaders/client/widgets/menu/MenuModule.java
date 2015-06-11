package org.spaceinvaders.client.widgets.menu;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/4/2015
 *
 * @author antoine
 */
public class MenuModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(MenuPresenter.MyView.class).to(MenuView.class);
        bindPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class, MenuView.class);
//        install(new GinFactoryModuleBuilder().build(WidgetsFactory.class));
//        bindPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class, MenuView.class);
    }
}
