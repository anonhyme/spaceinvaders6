package org.spaceinvaders.client.widgets.cell;

import com.google.common.eventbus.EventBus;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.HasHandlerContainer;

import org.apache.tools.ant.taskdefs.condition.Not;
import org.spaceinvaders.client.application.semester.SemesterPresenter;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ApCell extends AbstractCell<String> {

    private String data;

    private final static String AP_CELL = "<div>{0}</div>";


    interface Templates extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template(AP_CELL)
        SafeHtml apCell(String data);
    }

    private static Templates templates = GWT.create(Templates.class);

    public ApCell() {
        super(BrowserEvents.CLICK);

    }


    @Override
    public void render(Context context, String data, SafeHtmlBuilder sb) {
        if (data == null) {
            return;
        }
        GWT.log("::::: Ap cell created ::::: ");
        SafeHtml safeHtml = templates.apCell(data);
        sb.append(safeHtml);
    }

//    @Override
//    public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
//                               ValueUpdater<String> valueUpdater) {
//        GWT.log("::::: Browser Event ::::: " + event.getType());
//        if (BrowserEvents.CLICK.equals(event.getType())) {
//            // Ignore event that occur outside of the element.
//            EventTarget eventTarget = event.getEventTarget();
//
//            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//                showAp(value);
//            }
//        }
//    }

    private void showAp(String data) {
        return;
    }
}
