package org.spaceinvaders.client.application.widgets.grid.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

/**
 * Created by hugbed on 15-06-19.
 */
public class EvaluationReceivedEvent extends GwtEvent<EvaluationReceivedEvent.SemesterGradesReceivedHandler> {

    public interface SemesterGradesReceivedHandler extends EventHandler {
        void onSemesterGradesReceived(EvaluationReceivedEvent event);
    }

    public static Type<SemesterGradesReceivedHandler> TYPE = new Type<SemesterGradesReceivedHandler>();

    private final TreeMap<String, Evaluation> evaluations;

    public EvaluationReceivedEvent(TreeMap<String, Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public TreeMap<String, Evaluation> getEvaluations() {
        return evaluations;
    }

    public static void fire(TreeMap<String, Evaluation> evaluations, HasHandlers source) {
        source.fireEvent(new EvaluationReceivedEvent(evaluations));
    }

    @Override
    public Type<SemesterGradesReceivedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SemesterGradesReceivedHandler handler) {
        handler.onSemesterGradesReceived(this);
    }
}