package org.spaceinvaders.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import org.spaceinvaders.client.application.ApplicationModule;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.resources.ResourceLoader;

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
    }

//    //REMEMBER create the RPC service
//    @Provides
//    @Singleton
//    SemesterServiceAsync helloService() {
//        return GWT.create(SemesterService.class);
//    }

}
