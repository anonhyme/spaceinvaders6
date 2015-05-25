package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.dispatch.GetSemesterGradesHandler;
import org.spaceinvaders.server.dispatch.UserSessionImpl;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bind(UserSessionImpl.class);
        bindHandler(GetSemesterGradesAction.class, GetSemesterGradesHandler.class);
    }
}
