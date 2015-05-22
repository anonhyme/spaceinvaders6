package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import org.spaceinvaders.server.dispatch.UserSessionImpl;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        //bind(UserSessionImpl.class);
    }
}
