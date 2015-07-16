package org.spaceinvaders.server.dao;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.spaceinvaders.server.dao.mock.EvaluationDaoMock;
import org.spaceinvaders.server.dao.mock.SemesterInfoDaoMock;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("persistUnit"));

        bind(EvaluationDao.class).to(EvaluationDaoImpl.class);
        bind(SemesterInfoDao.class).to(SemesterInfoDaoImpl.class);
    }
}