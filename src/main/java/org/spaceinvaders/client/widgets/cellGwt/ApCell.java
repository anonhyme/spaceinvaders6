package org.spaceinvaders.client.widgets.cellGwt;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
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
public class ApCell extends AbstractCell<CellPresenter> {

    private String data;
    private CellPresenter cellPresenter;

    public ApCell(WidgetsFactory widgetsFactory) {
        super(BrowserEvents.CLICK);
    }

    @Override
    public void render(Context context, CellPresenter cellPresenter, SafeHtmlBuilder sb) {
        if (cellPresenter == null) {
            return;
        }

        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(cellPresenter.asWidget().getElement().toString());
        GWT.log(":::: Render Cell :::: " + safeHtml);
        sb.append(safeHtml);
    }

//    @Override
//    public void onBrowserEvent(Context context, Element parent, CellPresenter value, NativeEvent event,
//                               ValueUpdater<CellPresenter> valueUpdater) {
//        super.onBrowserEvent(context, parent, value, event, valueUpdater);
//
//        if (BrowserEvents.CLICK.equals(event.getType())) {
//            // Ignore event that occur outside of the element.
//            EventTarget eventTarget = event.getEventTarget();
//            GWT.log(":::::: onBrowserEvent :::: " + Element.as(eventTarget).toString());
//            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
////                AlphaMikeFoxtrot(value.getData());
//
//                GWT.log(":::: render Column :::: " + this.cellPresenter.asWidget().toString());
//            }
//        }
//    }

    private void AlphaMikeFoxtrot(String data) {

//        CellHoverEvent.fire(data, cellPresenter);
    }
}
