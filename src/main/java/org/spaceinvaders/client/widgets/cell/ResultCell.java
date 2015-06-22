package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ResultCell extends AbstractCell<String> {

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        // Value can be null, so do a null check..
        if (value == null) {
            return;
        }

//        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(value.getElement().getFirstChild().toString());

        SafeHtml safeHtml = TooltipCellTemplates.INSTANCE.myCell();
        GWT.log("render safeHtml " + safeHtml);
        sb.append(safeHtml);
    }

}
