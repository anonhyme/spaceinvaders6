package org.spaceinvaders.server.api;

import com.google.inject.AbstractModule;
import org.spaceinvaders.shared.api.UserInfoResource;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserInfoResource.class).to(UserInfoResourceImpl.class);
    }
}
