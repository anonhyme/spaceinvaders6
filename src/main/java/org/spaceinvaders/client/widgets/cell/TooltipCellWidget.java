package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipResources;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Popover;
import org.gwtbootstrap3.client.ui.constants.Trigger;
import org.spaceinvaders.client.resources.CustomTooltipResources;

import static com.google.gwt.query.client.GQuery.$;

//import com.arcbees.gquery.tooltip.client.TooltipOptions;
//import org.gwtbootstrap3.client.ui.Tooltip;
//import org.spaceinvaders.client.tooltip.client.Tooltip;
//import org.spaceinvaders.client.tooltip.client.TooltipOptions;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */

//TODO add action delegate
public class TooltipCellWidget extends Composite {
    private String data = "empty";

    private final CustomTooltipResources style;

    interface Binder extends UiBinder<Widget, TooltipCellWidget> {
    }

    @UiField
    Container container;

    @Inject
    public TooltipCellWidget(Binder binder,
                             CustomTooltipResources style) {
        this.style = style;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        Label label = new Label(data);
        this.data = data;

//        TooltipOptions options = new TooltipOptions();
//        options.withResources(getTooltipResources());
//        options.withContent(TooltipCellTemplates.INSTANCE.popover());
//        options.withSelector("tbody tr");
//        Popover popover = new Popover();
//        popover.add(label);
//        popover.setTrigger(Trigger.HOVER);

//        $(label).as(Tooltip.Tooltip).tooltip(options);

        container.add(label);
//        this.getElement().setInnerText(data);
    }

    public CustomTooltipResources getStyle() {
        return style;
    }

    private SafeHtml getTemplate() {
        return TooltipCellTemplates.INSTANCE.html(style.css().tooltip(), style.css().tooltipArrow(), style.css().tooltipInner());
    }

    private TooltipResources getTooltipResources() {
        return new TooltipResources() {
            @Override
            @Source("css/Tooltip.gss")
            public TooltipStyle css() {
                return getStyle().css();
            }
        };
    }
}
