package org.spaceinvaders.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Created with IntelliJ IDEA Project: notus on 6/27/2015
 *
 * @author antoine
 */
public class CellClickApEvent extends GwtEvent<CellClickApEvent.CellClickApEventHandler> {

    public interface CellClickApEventHandler extends EventHandler {
        void onColumnClick(CellClickApEvent event);
    }

    private String ap;

    public static Type<CellClickApEventHandler> TYPE = new Type<>();

    public CellClickApEvent(String ap) {
        this.ap = ap;
    }

    public Type<CellClickApEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(String data, HasHandlers source) {
        GWT.log(":::: EVENT FIRE :::: ApSelectedEvent  " + source.toString());
        source.fireEvent(new CellClickApEvent(data));
    }

    protected void dispatch(CellClickApEventHandler handler) {
        handler.onColumnClick(this);
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public static Type<CellClickApEventHandler> getTYPE() {
        return TYPE;
    }

    public static void setTYPE(Type<CellClickApEventHandler> TYPE) {
        CellClickApEvent.TYPE = TYPE;
    }
}
