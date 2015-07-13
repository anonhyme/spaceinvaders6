package org.spaceinvaders.client.application.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.IsWidget;

import com.gwtplatform.mvp.client.PresenterWidget;

import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

public class RevealPresenterEvent extends GwtEvent<RevealPresenterEvent.RevealPresenterHandler> {
    public interface RevealPresenterHandler extends EventHandler {
        void onRevealPresenter(RevealPresenterEvent event);
    }

    public static Type<RevealPresenterHandler> TYPE = new Type<RevealPresenterHandler>();

    private final IsWidget presenterWidget;

    public RevealPresenterEvent(IsWidget presenterWidget) {
        this.presenterWidget = presenterWidget;
    }

    public static void fire(IsWidget presenterWidget, HasHandlers source) {
        source.fireEvent(new RevealPresenterEvent(presenterWidget));
    }

    public IsWidget getPresenterWidget() {
        return presenterWidget;
    }


    @Override
    public Type<RevealPresenterHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RevealPresenterHandler handler) {
        handler.onRevealPresenter(this);
    }
}