package org.spaceinvaders.server.dispatch;

/**
 * Created by AlexandraMaude on 2015-05-26.
 */
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.shared.dispatch.SendToServerAction;
import org.spaceinvaders.shared.dispatch.SendToServerResult;

public class SendToServerHandler implements ActionHandler<SendToServerAction, SendToServerResult> {
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    public UserSessionImpl userSession;

    @Inject
    SendToServerHandler(
            ServletContext servletContext,
            Provider<HttpServletRequest> requestProvider) {
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
    }

    @Override
    public SendToServerResult execute(SendToServerAction action, ExecutionContext context)
            throws ActionException {
        // Get cip
        String cip = userSession.getUserId();

        return new SendToServerResult(cip);
    }

    @Override
    public Class<SendToServerAction> getActionType() {
        return SendToServerAction.class;
    }

    @Override
    public void undo(SendToServerAction action, SendToServerResult result, ExecutionContext context)
            throws ActionException {
        // Not undoable
    }
}
