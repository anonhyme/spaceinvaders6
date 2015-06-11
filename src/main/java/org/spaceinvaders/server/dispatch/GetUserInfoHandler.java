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

import org.spaceinvaders.shared.dispatch.UserInfo;
import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;

public class GetUserInfoHandler implements ActionHandler<GetUserInfoAction, GetUserInfoResult> {
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    public UserSessionImpl userSession;

    @Inject
    GetUserInfoHandler(
            ServletContext servletContext,
            Provider<HttpServletRequest> requestProvider) {
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
    }

    @Override
    public GetUserInfoResult execute(GetUserInfoAction action, ExecutionContext context)
            throws ActionException {
        UserInfo userInfo = userSession.getUserInfo();
        return new GetUserInfoResult(userInfo);
    }

    @Override
    public Class<GetUserInfoAction> getActionType() {
        return GetUserInfoAction.class;
    }

    @Override
    public void undo(GetUserInfoAction action, GetUserInfoResult result, ExecutionContext context)
            throws ActionException {
        // Not undoable
    }
}
