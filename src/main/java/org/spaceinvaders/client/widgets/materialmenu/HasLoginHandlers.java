package org.spaceinvaders.client.widgets.materialmenu;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */

import com.google.web.bindery.event.shared.HandlerRegistration;

import org.spaceinvaders.client.events.LoginEventHandler;

public interface HasLoginHandlers {
    HandlerRegistration addUserLoginHandler(LoginEventHandler handler, Object source);
}
