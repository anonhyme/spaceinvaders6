package org.spaceinvaders.server.guice;

//import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.google.inject.AbstractModule;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.api.ApiModule;
import org.spaceinvaders.server.dao.DaoModule;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        install(new DaoModule());
        install(new ApiModule());
    }
}
