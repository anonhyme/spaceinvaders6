package org.spaceinvaders.client.widgets.cell;

import com.google.web.bindery.event.shared.HandlerRegistration;

import org.spaceinvaders.client.widgets.cellGwt.events.CellHoverEventHandler;

/**
 * Created with IntelliJ IDEA Project: notus on 6/27/2015
 *
 * @author antoine
 */
public interface HasCellHoverHandlers {
    public HandlerRegistration addCellHoverHandler(CellHoverEventHandler handler, Object source);
}
