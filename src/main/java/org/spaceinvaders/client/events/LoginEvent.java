package org.spaceinvaders.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;


/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */
public class LoginEvent extends GwtEvent<LoginEvent.LoginHandler> {
    public static Type<LoginHandler> TYPE = new Type<LoginHandler>();
    private final String UserName;

    public interface LoginHandler extends EventHandler {
        void onLogin(LoginEvent event);
    }

    public Type<LoginHandler> getAssociatedType() {
        return TYPE;
    }

    public LoginEvent(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public static void fire(String userName, HasHandlers source) {
        GWT.log("fire Event ::: CIP = " + userName);
        source.fireEvent(new LoginEvent(userName));
    }

    protected void dispatch(LoginHandler loginHandler) {
        loginHandler.onLogin(this);
    }
}
