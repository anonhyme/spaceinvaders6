package org.spaceinvaders.client.resources;

import javax.inject.Inject;

public class ResourceLoader {
    @Inject
    ResourceLoader(AppResources appResources) {
        appResources.topNavBar().ensureInjected();
    }
}