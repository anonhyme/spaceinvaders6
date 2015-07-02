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

import java.util.HashMap;
import java.util.List;


/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public class EvaluationResultCell extends AbstractCell<HashMap<EvaluationResultType, String>> {

    private final String POPOVER_JS = "$(document).ready(function(){ $(\'[data-toggle=\"popover\"]\').popover();});";

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
                    "        <td> Std dev. :</td>\n" +
                    "        <td>{1}</td>\n" +
                    "    </tr>\n" +
                    "</table>";

    interface Templates extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template(POPOVER)
        SafeHtml popover(String innerHtml, String data);

        @SafeHtmlTemplates.Template(INNER_CONTENT)
        SafeHtml innerCell(String moy, String stdDev);
    }

    private static Templates templates = GWT.create(Templates.class);

    public EvaluationResultCell() {
        super(BrowserEvents.MOUSEOVER);
    }

    @Override
    public void render(Context context, HashMap<EvaluationResultType, String> data, SafeHtmlBuilder sb) {

        if (data.isEmpty()) {
            GWT.log("No data .... ");
            return;
        }
        SafeHtml innerHtml = templates.innerCell(data.get(EvaluationResultType.AVERAGE), data.get(EvaluationResultType.STD_DEV));
        SafeHtml safeHtml = templates.popover(innerHtml.asString(), data.get(EvaluationResultType.RESULT));
        sb.append(safeHtml);
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, HashMap<EvaluationResultType, String> value, NativeEvent event,
                               ValueUpdater<HashMap<EvaluationResultType, String>> valueUpdater) {
        GWT.log("::::: Browser Event :::::" + event.getType());
        ScriptInjector.fromString(POPOVER_JS).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }
}
