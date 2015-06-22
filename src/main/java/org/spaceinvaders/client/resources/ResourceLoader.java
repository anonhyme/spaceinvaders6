package org.spaceinvaders.client.resources;

import javax.inject.Inject;

public class ResourceLoader {
    @Inject
    ResourceLoader(AppResources appResources, CustomTooltipResources tooltipResources) {
        appResources.topNavBar().ensureInjected();
        tooltipResources.css().ensureInjected();
    }
}