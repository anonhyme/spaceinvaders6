//package org.spaceinvaders.server.entities;
//
//import com.google.inject.AbstractModule;
//import com.google.inject.Inject;
//import com.google.inject.persist.jpa.JpaPersistModule;
//
///**
// * Created with IntelliJ IDEA Project: projetS6 on 5/22/2015
// *
// * @author antoine
// */
//public class DaoModule extends AbstractModule {
//    final String persistenceUnit;
//
//    @Inject
//    public DaoModule(String persistenceUnit) {
//        this.persistenceUnit = persistenceUnit;
//    }
//
//    @Override
//    protected void configure() {
//        install(new JpaPersistModule(persistenceUnit));
//        bind(TestDaoEntity.class).to(TestDaoEntityImpl.class);
//    }
//}
