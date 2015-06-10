package org.spaceinvaders.client.gin;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import org.spaceinvaders.client.application.ApplicationModule;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.resources.ResourceLoader;
import org.spaceinvaders.client.widgets.commons.WidgetsFactory;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.client.widgets.menu.MenuView;


/**
 * See more on setting up the PlaceManager on <a href="// See more on:
 * https://github.com/ArcBees/GWTP/wiki/PlaceManager">DefaultModule's > DefaultPlaceManager</a>
 */
public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule());
        install(new RpcDispatchAsyncModule());
        install(new ApplicationModule());


        // DefaultPlaceManager Places
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.gridDemo);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.semesterGrades);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.semesterGrades);

        bind(ResourceLoader.class).asEagerSingleton();

//        bind(MenuPresenter.MyView.class).to(MenuView.class);
////        bindPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class, MaterialMenuView.class);
//        install(new GinFactoryModuleBuilder().build(WidgetsFactory.class));


    }
}
