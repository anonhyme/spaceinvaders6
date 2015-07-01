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

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ResultPopoverCell extends AbstractCell<Popover> {

    private String data;
    private CellPresenter cellPresenter;
    Popover popover;

    public ResultPopoverCell() {
        super(BrowserEvents.CLICK);
    }

    @Override
    public void render(Context context, Popover popover, SafeHtmlBuilder sb) {
        if (popover == null) {
            return;
        }
//        this.cellPresenter = cellPresenter;
//        SimplePanel simplePanel = new SimplePanel();
//        popover = new Popover();
//        popover.add(cellPresenter.asWidget());
//        popover.setTitle("hehehe");
//        popover.setContent("da fuk");
//        simplePanel.setWidget(popover);
        this.popover = popover;


//        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(cellPresenter.asWidget().getElement().toString());
//        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(popover.asWidget().getElement().toString());
        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(popover.asWidget().getElement().toString());
        GWT.log(":::: Render Cell :::: " + safeHtml);
        sb.append(safeHtml);
    }


    @Override
    public void onBrowserEvent(Context context, Element parent, Popover value, NativeEvent event,
                               ValueUpdater<Popover> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        if (BrowserEvents.CLICK.equals(event.getType())) {
            // Ignore event that occur outside of the element.
            EventTarget eventTarget = event.getEventTarget();
            GWT.log(":::::: onBrowserEvent :::: " + Element.as(eventTarget).toString());
            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//                AlphaMikeFoxtrot(value.getData());

                GWT.log(":::: render Column :::: " + this.popover.asWidget().toString());
            }
        }
    }

    private void AlphaMikeFoxtrot(String data) {

//        CellHoverEvent.fire(data, cellPresenter);
    }
}
