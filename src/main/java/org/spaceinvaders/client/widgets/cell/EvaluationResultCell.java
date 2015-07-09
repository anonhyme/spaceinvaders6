package org.spaceinvaders.client.widgets.cell;


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import org.spaceinvaders.client.application.widgets.grid.GridPresenter;
import org.spaceinvaders.client.events.ApSelectedEvent;

import java.util.HashMap;


/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class EvaluationResultCell extends AbstractCell<HashMap<EvaluationResultType, String>> {
    @Inject
    private EventBus eventBus;

    private final GridPresenter gridPresenter;

    private final String POPOVER_JS = "$(document).ready(function(){ $(\'[data-toggle=\"popover\"]\').popover();});";

    private static CellTemplates cellTemplates = GWT.create(CellTemplates.class);

    public EvaluationResultCell(GridPresenter gridPresenter) {
        super(BrowserEvents.MOUSEOVER, BrowserEvents.CLICK);
        this.gridPresenter = gridPresenter;
    }

    @Override
    public void render(Context context, HashMap<EvaluationResultType, String> data, SafeHtmlBuilder sb) {

        if (data.isEmpty()) {
            return;
        }
        String resultFraction = data.get(EvaluationResultType.RESULT) + "/" + data.get(EvaluationResultType.MAX_EVALUATION);
        SafeHtml innerHtml = cellTemplates.innerCell(resultFraction, data.get(EvaluationResultType.AVERAGE), data.get(EvaluationResultType.STD_DEV));
        SafeHtml safeHtml = cellTemplates.popover(innerHtml.asString(), data.get(EvaluationResultType.RESULT));
        sb.append(safeHtml);
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, HashMap<EvaluationResultType, String> value, NativeEvent event,
                               ValueUpdater<HashMap<EvaluationResultType, String>> valueUpdater) {
        ScriptInjector.fromString(POPOVER_JS).setWindow(ScriptInjector.TOP_WINDOW).inject();
        try {
            if (BrowserEvents.MOUSEOVER.equals(event.getType())) {
                // Ignore event that occur outside of the element.
                EventTarget eventTarget = event.getEventTarget();
                if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
                    GWT.log("::::: Browser Event ::::: " + event.getType());
                }
            }

            if (BrowserEvents.CLICK.equals(event.getType())) {
                if (parent.getFirstChildElement().isOrHasChild(Element.as(event.getEventTarget()))) {
                    GWT.log("::::: Browser Event ::::: " + event.getType());
                    showAp(value.get(EvaluationResultType.AP));
                }
            }
        } catch (Exception e) {

        }
    }

    private void showAp(String ap) {
        GWT.log(":::: Fire ap loading ::::");
        ApSelectedEvent.fire(ap, gridPresenter);
    }
}
