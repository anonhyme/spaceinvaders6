package org.spaceinvaders.client.events;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */

import com.google.web.bindery.event.shared.HandlerRegistration;

public interface HasLoginHandlers {
    HandlerRegistration addUserLoginHandler(LoginEventHandler loginEventHandler, Object source);
}
