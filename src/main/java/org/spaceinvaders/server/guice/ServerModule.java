package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

import org.spaceinvaders.server.api.ApiModule;
import org.spaceinvaders.server.dao.DaoModule;
import org.spaceinvaders.server.dispatch.GetSemesterGradesHandler;
import org.spaceinvaders.server.dispatch.GetSemesterInfoHandler;
import org.spaceinvaders.server.dispatch.GetUserInfoHandler;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.actions.GetUserInfoAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        install(new DaoModule());
        install(new ApiModule());

        bindHandler(GetSemesterGradesAction.class, GetSemesterGradesHandler.class);
        bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
        bindHandler(GetSemesterInfoAction.class, GetSemesterInfoHandler.class);
    }
}
