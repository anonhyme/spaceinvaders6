package org.spaceinvaders.client.widgets.cellGwt.templates;

import com.arcbees.gquery.tooltip.client.TooltipResources;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.gwtbootstrap3.client.ui.Container;
import org.spaceinvaders.client.resources.CustomTooltipResources;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */

//TODO add action delegate
public class TooltipCellWidget extends Composite {
    @UiField
    Container container;
    private final CustomTooltipResources style;
    private String data = "empty";

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
        container.add(label);

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

    interface Binder extends UiBinder<Widget, TooltipCellWidget> {
    }
}
