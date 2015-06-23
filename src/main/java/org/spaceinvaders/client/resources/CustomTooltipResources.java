package org.spaceinvaders.client.resources;

import com.arcbees.gquery.tooltip.client.TooltipResources;
import com.google.gwt.resources.client.ClientBundle;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/21/2015
 *
 * @author antoine
 */
public interface CustomTooltipResources extends ClientBundle, TooltipResources{

    interface CustomTooltipStyle extends TooltipResources.TooltipStyle {
        @ClassName("tooltip")
        String tooltip();

        String in();

        String top();

        String left();

        String right();

        String bottom();

        @ClassName("tooltip-inner")
        String tooltipInner();

        @ClassName("tooltip-arrow")
        String tooltipArrow();
    }

    @Source("css/Tooltip.gss")
    CustomTooltipStyle css();
}
