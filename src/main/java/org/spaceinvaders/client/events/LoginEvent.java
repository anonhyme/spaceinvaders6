package org.spaceinvaders.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {
    public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

    private final String UserName;

    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    public LoginEvent(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public static void fire(String userName, HasHandlers source) {
        source.fireEvent(new LoginEvent(userName));
    }

    protected void dispatch(LoginEventHandler handler) {
        handler.onLogin(this);
    }
}
