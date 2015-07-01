package org.spaceinvaders.client.widgets.cellGwt;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.spaceinvaders.client.widgets.cell.CellPresenter;

import java.util.List;


/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class ResultCellStr extends AbstractCell<List<String>> {

    private String data;
    private CellPresenter cellPresenter;

    private final String POPOVER_JS = "$(document).ready(function(){ $(\'[data-toggle=\"popover\"]\').popover();});";
    public static final String EMPTY = "<div>{0}</div>";
    private final static String POPOVER = "<div data-html=\"true\" " +
            "data-container=\"body\" " +
            "data-trigger=\"hover\" " +
            "data-toggle=\"popover\" " +
            "data-placement=\"top\" " +
            "data-content=\"{0} \" > " +
            " {1} " +
            "</div>";

    private final static String INNER_CONTENT =
            "<table cellpadding='5' style='text-align:left'>\n" +
                    "    <tr>\n" +
                    "        <td> moy. :</td>\n" +
                    "        <td>{0}</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td> ecart type. :</td>\n" +
                    "        <td>{1}</td>\n" +
                    "    </tr>\n" +
                    "</table>";

    interface Templates extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template(EMPTY)
        SafeHtml emptyCell(String data);

        @SafeHtmlTemplates.Template(POPOVER)
        SafeHtml popover(String innerHtml, String data);

        @SafeHtmlTemplates.Template(INNER_CONTENT)
        SafeHtml innerCell(String moy, String ecartType);
    }

    private static Templates templates = GWT.create(Templates.class);

    public ResultCellStr() {
        super(BrowserEvents.MOUSEOVER);
    }

    @Override
    public void render(Context context, List<String> data, SafeHtmlBuilder sb) {

        if (data == null) {
            return;
        }

        if (data.size() == 0) {
            sb.append(templates.emptyCell(data.get(0)));
            return;
        }

        SafeHtml innerHtml = templates.innerCell(data.get(1), data.get(2));
        SafeHtml safeHtml = templates.popover(innerHtml.asString(), data.get(0));
        GWT.log(":::: Render Cell :::: " + safeHtml);
        sb.append(safeHtml);
    }


    @Override
    public void onBrowserEvent(Context context, Element parent, List<String> value, NativeEvent event,
                               ValueUpdater<List<String>> valueUpdater) {
        GWT.log("::::: Browser Event :::::" + event.getType());
        ScriptInjector.fromString(POPOVER_JS).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

}
