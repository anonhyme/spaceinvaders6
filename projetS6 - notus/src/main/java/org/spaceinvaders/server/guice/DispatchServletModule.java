package org.spaceinvaders.server.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;

import com.arcbees.guicyresteasy.GuiceRestEasyFilterDispatcher;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

import org.spaceinvaders.shared.api.ApiPaths;


public class DispatchServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        filter("/*").through(PersistFilter.class);
        filter(ApiPaths.ROOT + "/*").through(GuiceRestEasyFilterDispatcher.class);
        serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
    }
}