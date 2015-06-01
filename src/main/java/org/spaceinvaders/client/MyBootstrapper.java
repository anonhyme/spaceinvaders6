package org.spaceinvaders.client;

import com.google.inject.Inject;

import jdk.nashorn.internal.runtime.linker.Bootstrap;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/1/2015
 *
 * @author antoine
 */
import com.arcbees.gwtpolymer.base.GwtPolymer;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class MyBootstrapper implements Bootstrapper {
    private final PlaceManager placeManager;

    @Inject
    MyBootstrapper(
            PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    @Override
    public void onBootstrap() {
        GwtPolymer.init(new GwtPolymer.LoadCallback() {
            @Override
            public void onInjectDone() {
                placeManager.revealCurrentPlace();
            }
        });
    }
}
