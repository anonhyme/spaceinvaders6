package org.spaceinvaders.client.widgets.cellGwt;


import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public interface TooltipCellTemplates extends SafeHtmlTemplates {
    String TEMPLATE_TOOLTIP =
            "<table cellpadding='10' style='text-align:left'>\n" +
            "    <tr>\n" +
            "        <td> moy. :</td>\n" +
            "        <td>20%</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td> ecart type. :</td>\n" +
            "        <td>2</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td> moy. :</td>\n" +
            "        <td>20%</td>\n" +
            "    </tr>\n" +
            "</table>";
    String CELL = "<div row-id=0>HEEEEEEEEEEEEEEEEEE</div>";
    TooltipCellTemplates INSTANCE = GWT.create(TooltipCellTemplates.class);

    @Template("<div class=\"{0}\">" +
                "<div class=\"{1}\"></div>" +
                "<div class=\"{2}\"></div>" +
            "</div>")
    SafeHtml html(String tooltipClass, String tooltipArrowClass, String tooltipInnerClass);

    @Template(TEMPLATE_TOOLTIP)
    SafeHtml popover();

    @Template(CELL)
    SafeHtml myCell();

}
