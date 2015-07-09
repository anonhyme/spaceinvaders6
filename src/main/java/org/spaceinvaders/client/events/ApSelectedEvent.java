package org.spaceinvaders.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ApSelectedEvent extends GwtEvent<ApSelectedEvent.Handler> {
    public interface Handler extends EventHandler {
        void onApSelected(ApSelectedEvent event);
    }

    public static Type<Handler> TYPE = new Type<>();

    private String ap;
    private String sessionId;

    public ApSelectedEvent(String ap) {
        this.ap = ap;
    }

    public String getAp() {
        return ap;
    }

    public static void fire(String ap, HasHandlers source) {
        source.fireEvent(new ApSelectedEvent(ap));
    }

    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(Handler handler) {
        handler.onApSelected(this);
    }
}