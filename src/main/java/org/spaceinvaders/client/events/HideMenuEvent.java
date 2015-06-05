package org.spaceinvaders.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/4/2015
 *
 * @author antoine
 */
public class HideMenuEvent extends GwtEvent<HideMenuEvent.HideMenuHandler> {
    public static Type<HideMenuHandler> TYPE = new Type<HideMenuHandler>();

    public interface HideMenuHandler extends EventHandler {
        void onHideMenu(HideMenuEvent event);
    }

    String userName;

    public HideMenuEvent(String userName) {
        this.userName = userName;
    }

    public static void fire(String userName, HasHandlers source) {
        GWT.log("fire Event Menu Hide::: ");
        source.fireEvent(new HideMenuEvent(userName));
    }

    public Type<HideMenuHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(HideMenuHandler handler) {
        handler.onHideMenu(this);
    }

}
