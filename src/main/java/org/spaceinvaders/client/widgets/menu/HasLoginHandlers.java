package org.spaceinvaders.client.widgets.menu;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */

import com.google.web.bindery.event.shared.HandlerRegistration;

import org.spaceinvaders.client.events.HideMenuEvent;
import org.spaceinvaders.client.events.LoginEvent;

public interface HasLoginHandlers {
    HandlerRegistration addUserLoginHandler(LoginEvent.LoginHandler loginHandler, Object source);
    HandlerRegistration addHideMenuHandler(HideMenuEvent.HideMenuHandler hideMenuHandler, Object source);
}
