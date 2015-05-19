package org.spaceinvaders.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import org.spaceinvaders.client.application.ApplicationModule;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.resources.ResourceLoader;
import org.spaceinvaders.client.rpc.ExampleService;
import org.spaceinvaders.client.rpc.ExampleServiceAsync;

/**
 * See more on setting up the PlaceManager on <a href="// See more on:
 * https://github.com/ArcBees/GWTP/wiki/PlaceManager">DefaultModule's > DefaultPlaceManager</a>
 */
public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {

        // DefaultPlaceManager Places
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.home);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.home);

        install(new DefaultModule());
        install(new ApplicationModule());

        //TODO Install RpcDispatchAsyncModule() to use GWTP rpc dispatcher
        install(new RpcDispatchAsyncModule());

        //REMEMBER bind ressource loader
        bind(ResourceLoader.class).asEagerSingleton();
    }

    //REMEMBER create the RPC service
    @Provides
    @Singleton
    ExampleServiceAsync helloService() {
        return GWT.create(ExampleService.class);
    }

}
