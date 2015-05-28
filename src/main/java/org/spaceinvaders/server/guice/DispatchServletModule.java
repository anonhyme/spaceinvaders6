package org.spaceinvaders.server.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;

import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;
import org.spaceinvaders.server.cas.UserSession;
import org.spaceinvaders.server.cas.UserSessionImpl;

public class DispatchServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        filter("/*").through(PersistFilter.class); // necessary to start JPA service
        serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);

        bind(UserSession.class).to(UserSessionImpl.class);
    }
}