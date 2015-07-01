package org.spaceinvaders.client.widgets.cellGwt.templates;


import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public interface TooltipCellTemplates extends SafeHtmlTemplates {
    String POPOVER = "<div class=\"\" " +
            "data-container=\"body\" " +
            "data-trigger=\"hover\" " +
            "data-toggle=\"popover\" " +
            "data-placement=\"\" " +
            "data-content=\"Vivamus sagittis lacus vel augue laoreet rutrum faucibus.\">\n" +
            "  {0}\n" +
            "</div>";

    TooltipCellTemplates INSTANCE = GWT.create(TooltipCellTemplates.class);

    @Template("<div class=\"{0}\">" +
                "<div class=\"{1}\"></div>" +
                "<div class=\"{2}\"></div>" +
            "</div>")
    SafeHtml html(String tooltipClass, String tooltipArrowClass, String tooltipInnerClass);

    @Template(POPOVER)
    SafeHtml popover(String data);
}
