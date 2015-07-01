package org.spaceinvaders.client.widgets.cellGwt;

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
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

import org.spaceinvaders.client.widgets.cell.CellPresenter;

import static com.google.gwt.query.client.GQuery.$;


/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ResultCell extends AbstractCell<CellPresenter> {

    private String data;
    private CellPresenter cellPresenter;
    String test = "<div class=\"\" data-container=\"body\" data-trigger=\"hover\" data-toggle=\"popover\" data-placement=\"left\" data-content=\"Vivamus sagittis lacus vel augue laoreet rutrum faucibus.\">\n" +
            "  Popover on left\n" +
            "</div>";
    private final String POPOVER_JS = "$(document).ready(function(){ $(\'[data-toggle=\"popover\"]\').popover();});";

    public ResultCell() {
        super(BrowserEvents.MOUSEOVER);
    }

    @Override
    public void render(Context context, CellPresenter cellPresenter, SafeHtmlBuilder sb) {
        if (cellPresenter == null) {
            return;
        }

        this.cellPresenter = cellPresenter;

//        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(cellPresenter.asWidget().toString());
        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(test);
        GWT.log(":::: Render Cell :::: " + safeHtml);
        sb.append(safeHtml);
    }


    @Override
    public void onBrowserEvent(Context context, Element parent, CellPresenter value, NativeEvent event,
                               ValueUpdater<CellPresenter> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        ScriptInjector.fromString(POPOVER_JS).setWindow(ScriptInjector.TOP_WINDOW).inject();

    }

    private void isBrowserEvent(Element parent, NativeEvent event) {
        // Ignore event that occur outside of the element.
        if (BrowserEvents.MOUSEOVER.equals(event.getType())) {
            EventTarget eventTarget = event.getEventTarget();
            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
                GWT.log(":::: render Column :::: " + this.cellPresenter.asWidget().toString());
            }
        }
    }
}
