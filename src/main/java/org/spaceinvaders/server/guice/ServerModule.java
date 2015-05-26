package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dispatch.SendToServerHandler;
import org.spaceinvaders.shared.dispatch.SendToServerAction;
import org.spaceinvaders.shared.dispatch.SendToServerResult;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bind(UserSessionImpl.class);
        bindHandler(SendToServerAction.class, SendToServerHandler.class);
    }
}
