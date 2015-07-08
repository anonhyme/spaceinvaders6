package org.spaceinvaders.client.widgets.cell;

import com.google.web.bindery.event.shared.HandlerRegistration;
import org.spaceinvaders.client.events.ApSelectedEvent;

public interface HasApSelectedEvent {
    HandlerRegistration addComputerHackedHandler(ApSelectedEvent.Handler handler, Object source);
}
