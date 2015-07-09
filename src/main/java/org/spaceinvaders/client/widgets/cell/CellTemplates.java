package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Created with IntelliJ IDEA Project: notus on 7/7/2015
 *
 * @author antoine
 */
public interface CellTemplates extends SafeHtmlTemplates {

    String POPOVER = "<div data-html=\"true\" " +
            "data-container=\"body\" " +
            "data-trigger=\"hover\" " +
            "data-toggle=\"popover\" " +
            "data-placement=\"top\" " +
            "data-content=\"{0} \" > " +
            " {1} " +
            "</div>";

    String INNER_CONTENT =
            "<table cellpadding='5' style='text-align:center'>\n" +
                    "    <tr style='text-align:center'>\n" +
                    "        <td> note :</td>\n" +
                    "        <td>{0}</td>\n" +
                    "    </tr>\n" +
                    "    <tr style='text-align:center'>\n" +
                    "        <td> moy. :</td>\n" +
                    "        <td>{1}</td>\n" +
                    "    </tr>\n" +
                    "    <tr style='text-align:center'>\n" +
                    "        <td> Std dev. :</td>\n" +
                    "        <td>{2}</td>\n" +
                    "    </tr>\n" +
                    "</table>";

    @SafeHtmlTemplates.Template(POPOVER)
    SafeHtml popover(String innerHtml, String data);

    @SafeHtmlTemplates.Template(INNER_CONTENT)
    SafeHtml innerCell(String note, String moy, String stdDev);
}
