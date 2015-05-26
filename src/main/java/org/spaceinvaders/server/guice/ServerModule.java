package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import org.spaceinvaders.server.dispatch.GetSemesterGradesHandler;
import org.spaceinvaders.server.dispatch.GetSemesterInfoHandler;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetSemesterGradesAction.class, GetSemesterGradesHandler.class);
        bindHandler(GetSemesterInfoAction.class, GetSemesterInfoHandler.class);
    }
}
