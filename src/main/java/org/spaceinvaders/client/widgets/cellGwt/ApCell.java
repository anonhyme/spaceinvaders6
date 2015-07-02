package org.spaceinvaders.client.widgets.cellGwt;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

import org.gwtbootstrap3.client.ui.Popover;
import org.spaceinvaders.client.widgets.cell.CellPresenter;
import org.spaceinvaders.client.widgets.cell.WidgetsFactory;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ApCell extends AbstractCell<String> {

    private String data;
    private CellPresenter cellPresenter;

    public ApCell(WidgetsFactory widgetsFactory) {
        super(BrowserEvents.CLICK);
    }

    @Override
    public void render(Context context, String data, SafeHtmlBuilder sb) {
        if (cellPresenter == null) {
            return;
        }

        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(data.toString());

        sb.append(safeHtml);
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
                               ValueUpdater<String> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        if (BrowserEvents.CLICK.equals(event.getType())) {
            // Ignore event that occur outside of the element.
            EventTarget eventTarget = event.getEventTarget();

            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//                AlphaMikeFoxtrot(value.getData());
            }
        }
    }

    private void AlphaMikeFoxtrot(String data) {

//        CellHoverEvent.fire(data, cellPresenter);
    }
}
