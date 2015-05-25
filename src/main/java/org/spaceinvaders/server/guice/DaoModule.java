package org.spaceinvaders.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.dao.CompetenceEvalResultDaoImpl;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("persistUnit"));

        bind(CompetenceEvalResultDao.class).to(CompetenceEvalResultDaoImpl.class);
    }
}