package org.spaceinvaders.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provides;

import com.gwtplatform.dispatch.rest.client.RestApplicationPath;
import com.gwtplatform.dispatch.rest.client.gin.RestDispatchAsyncModule;
import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import org.spaceinvaders.client.application.ApplicationModule;
//import org.spaceinvaders.client.application.dispatch.rest.AppRestDispatchHooks;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.resources.ResourceLoader;
import org.spaceinvaders.shared.api.ApiPaths;


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

        // Rest dispatch
        install(new RestDispatchAsyncModule.Builder()
//                .dispatchHooks(AppRestDispatchHooks.class)
                .build());

        // DefaultPlaceManager Places
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.restPage);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.semesterGrades);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.semesterGrades);

        bind(ResourceLoader.class).asEagerSingleton();
    }

    @Provides
    @RestApplicationPath
    String applicationPath() {
        String baseUrl = GWT.getHostPageBaseURL();
        if (baseUrl.endsWith("/"))
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);

        return baseUrl + ApiPaths.ROOT;
    }
}