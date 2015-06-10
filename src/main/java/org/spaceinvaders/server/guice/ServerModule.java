package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.dispatch.GetSemesterGradesHandler;
import org.spaceinvaders.server.dispatch.GetSemesterInfoHandler;
import org.spaceinvaders.server.dispatch.GetUserInfoHandler;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetSemesterGradesAction.class, GetSemesterGradesHandler.class);
        bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
        bindHandler(GetSemesterInfoAction.class, GetSemesterInfoHandler.class);
    }
}
