package org.spaceinvaders.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SemesterChangedEvent extends GwtEvent<SemesterChangedEvent.SemesterChangedHandler> {


    public interface SemesterChangedHandler extends EventHandler {
        void onSemesterChanged(SemesterChangedEvent event);
    }

    public static Type<SemesterChangedHandler> TYPE = new Type<SemesterChangedHandler>();

    private int semesterID;

    public SemesterChangedEvent(int semesterID) {
        this.semesterID = semesterID;
    }

    public static void fire(int semesterID, HasHandlers source) {
        source.fireEvent(new SemesterChangedEvent(semesterID));
    }

    public int getSemesterID() {
        return semesterID;
    }

    public Type<SemesterChangedHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(SemesterChangedHandler handler) {
        handler.onSemesterChanged(this);
    }
}