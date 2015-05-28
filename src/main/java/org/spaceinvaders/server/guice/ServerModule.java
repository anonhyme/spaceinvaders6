package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dispatch.GetUserInfoHandler;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bind(UserSessionImpl.class);
        bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
    }
}
