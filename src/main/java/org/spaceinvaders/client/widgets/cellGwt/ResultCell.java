package org.spaceinvaders.client.widgets.cellGwt;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.spaceinvaders.client.widgets.cell.CellPresenter;
import org.spaceinvaders.client.widgets.cell.WidgetsFactory;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ResultCell extends AbstractCell<String> {

    private final WidgetsFactory widgetsFactory;

    public ResultCell(WidgetsFactory widgetsFactory) {
        this.widgetsFactory = widgetsFactory;
    }

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {

        if (value == null) {
            return;
        }
        CellPresenter cellPresenter = widgetsFactory.createCell(value);

        SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(cellPresenter.asWidget().getElement().toString());

        GWT.log("render safeHtml " + safeHtml);
        sb.append(safeHtml);
    }

}
