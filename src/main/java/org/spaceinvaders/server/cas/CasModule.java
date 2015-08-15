package org.spaceinvaders.server.cas;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created with IntelliJ IDEA Project: notus on 8/14/2015
 *
 * @author antoine
 */

public class CasModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserSession.class).to(UserSessionOffline.class);
    }
}
