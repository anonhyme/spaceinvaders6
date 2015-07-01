package org.spaceinvaders.server.api;

import com.google.inject.AbstractModule;

import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.api.UserInfoResource;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserInfoResource.class).to(UserInfoResourceImpl.class);
        bind(EvaluationResource.class).to(EvaluationResourceImpl.class);
        bind(SemesterInfoResource.class).to(SemesterInfoResourceImpl.class);
    }
}
