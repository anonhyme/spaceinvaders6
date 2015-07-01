package org.spaceinvaders.client.widgets.cellGwt.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Created with IntelliJ IDEA Project: notus on 6/27/2015
 *
 * @author antoine
 */
public class CellHoverEvent extends GwtEvent<CellHoverEventHandler> {

    private String data;

    public static Type<CellHoverEventHandler> TYPE = new Type<CellHoverEventHandler>();

    public CellHoverEvent(String data){
        this.data = data;
    }

    public Type<CellHoverEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(String data, HasHandlers source) {
        source.fireEvent(new CellHoverEvent(data));
    }

    protected void dispatch(CellHoverEventHandler handler) {
        handler.onCellHover(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
