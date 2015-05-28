package org.spaceinvaders.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

<<<<<<< HEAD
=======
import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dispatch.GetUserInfoHandler;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
>>>>>>> a013be334dcf464a0c25ebe011e0647591658d95
import org.spaceinvaders.server.dispatch.GetSemesterGradesHandler;
import org.spaceinvaders.server.dispatch.GetSemesterInfoHandler;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetSemesterGradesAction.class, GetSemesterGradesHandler.class);
        bindHandler(GetUserInfoAction.class, GetUserInfoHandler.class);
        bindHandler(GetSemesterInfoAction.class, GetSemesterInfoHandler.class);
    }
}
