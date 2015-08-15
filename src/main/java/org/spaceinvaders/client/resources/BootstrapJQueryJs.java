package org.spaceinvaders.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * Created with IntelliJ IDEA
 * Project: notus
 * on 6/22/2015
 * @author antoine
 */
public interface BootstrapJQueryJs extends ClientBundle {

    BootstrapJQueryJs INSTANCE = GWT.create(BootstrapJQueryJs.class);

    @Source("js/jquery-1.11.2.min.cache.js")
    TextResource jQuery();

    @Source("js/bootstrap-3.3.2.min.cache.js")
    TextResource bootstrap();
}
