package org.spaceinvaders.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.spaceinvaders.server.dao.SemesterResultsDao;
import org.spaceinvaders.server.dao.SemesterResultsDaoImpl;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("persistUnit"));

        bind(SemesterResultsDao.class).to(SemesterResultsDaoImpl.class);
    }
}