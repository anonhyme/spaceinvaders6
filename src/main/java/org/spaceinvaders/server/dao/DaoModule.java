package org.spaceinvaders.server.dao;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.dao.CompetenceEvalResultDaoImpl;
import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.server.dao.SemesterInfoDaoImpl;
import org.spaceinvaders.server.dao.mock.CompetenceEvalResultDaoMock;
import org.spaceinvaders.server.dao.mock.SemesterInfoDaoMock;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("persistUnit"));

        bind(CompetenceEvalResultDao.class).to(CompetenceEvalResultDaoMock.class);
        bind(SemesterInfoDao.class).to(SemesterInfoDaoMock.class);
    }
}