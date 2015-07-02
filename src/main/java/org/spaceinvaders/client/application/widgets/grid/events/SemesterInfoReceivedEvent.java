package org.spaceinvaders.client.application.widgets.grid.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import org.spaceinvaders.shared.dto.SemesterInfo;

public class SemesterInfoReceivedEvent extends GwtEvent<SemesterInfoReceivedEvent.SemesterInfoReceivedEventHandler> {

    public interface SemesterInfoReceivedEventHandler extends EventHandler {
        void onSemesterInfoReceived(SemesterInfoReceivedEvent event);
    }

    public static Type<SemesterInfoReceivedEventHandler> TYPE = new Type<SemesterInfoReceivedEventHandler>();

    private SemesterInfo semesterInfo;

    public SemesterInfoReceivedEvent(SemesterInfo semesterInfo) {
        this.semesterInfo = semesterInfo;
    }

    public static void fire(SemesterInfo semesterInfo, HasHandlers source) {
        source.fireEvent(new SemesterInfoReceivedEvent(semesterInfo));
    }

    public SemesterInfo getSemesterInfo() {
        return semesterInfo;
    }

    public Type<SemesterInfoReceivedEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(SemesterInfoReceivedEventHandler handler) {
        handler.onSemesterInfoReceived(this);
    }
}
