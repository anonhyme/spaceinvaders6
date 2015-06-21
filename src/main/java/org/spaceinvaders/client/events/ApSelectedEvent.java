package org.spaceinvaders.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import org.spaceinvaders.shared.dto.Ap;

public class ApSelectedEvent extends GwtEvent<ApSelectedEvent.ApSelectedEventHandler> {
    public interface ApSelectedEventHandler extends EventHandler {
        void onApSelected(ApSelectedEvent event);
    }

    public static Type<ApSelectedEventHandler> TYPE = new Type<ApSelectedEventHandler>();

    private Ap ap;

    public ApSelectedEvent(Ap ap) {
        this.ap = ap;
    }

    public Ap getAp() {
        return ap;
    }

    public static void fire(Ap ap, HasHandlers source) {
        source.fireEvent(new ApSelectedEvent(ap));
    }

    public Type<ApSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(ApSelectedEventHandler handler) {
        handler.onApSelected(this);
    }
}