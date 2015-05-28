package org.spaceinvaders.client.application.ui.menu;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class MainMenuEvent extends GwtEvent<MainMenuEvent.MainMenuHandler> {

    private static Type<MainMenuHandler> TYPE = new Type<MainMenuHandler>();

    interface MainMenuHandler extends EventHandler {
        void onMainMenuEvent(MainMenuEvent event);
    }

    public interface MainMenuHandlers extends HasHandlers {
        HandlerRegistration addMainMenuHandler(MainMenuHandler handler);
    }

    private String menuEvent;

    public MainMenuEvent(String menuEvent) {
        this.menuEvent = menuEvent;
    }

    static Type<MainMenuHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final MainMenuHandler handler) {
        handler.onMainMenuEvent(this);
    }

    @Override
    public Type<MainMenuHandler> getAssociatedType() {
        return TYPE;
    }

    public String getMenuEvent() { return menuEvent;}
    public void setMenuEvent(String menuEvent) {this.menuEvent = menuEvent;}


    public static void fire(HasHandlers source,  String data) {
        MainMenuEvent eventInstance = new MainMenuEvent(data);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, MainMenuEvent eventInstance) {
        source.fireEvent(eventInstance);
    }
}
