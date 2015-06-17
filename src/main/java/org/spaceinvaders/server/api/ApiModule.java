package org.spaceinvaders.server.api;

import com.google.inject.AbstractModule;

import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.api.UserInfoResource;

import javax.inject.Singleton;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JacksonProvider.class).in(Singleton.class);

        bind(UserInfoResource.class).to(UserInfoResourceImpl.class);
        bind(SemesterGradesResource.class).to(SemesterGradesResourceImpl.class);
        bind(SemesterInfoResource.class).to(SemesterInfoResourceImpl.class);
    }
}
