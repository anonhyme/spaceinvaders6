package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.dispatch.ExampleActionHandler;
import org.spaceinvaders.shared.dispatch.ExampleDispatch;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(ExampleDispatch.class, ExampleActionHandler.class);
    }
}
