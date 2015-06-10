package org.spaceinvaders.client.widgets.menu;

import com.google.gwt.inject.rebind.adapter.GinModuleAdapter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import com.gwtplatform.mvp.client.AutobindDisable;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaceinvaders.client.widgets.commons.WidgetsFactory;

import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/5/2015
 *
 * @author antoine
 */
@RunWith(JukitoRunner.class)
public class MenuPresenterTest {
//    @Inject


    public static class Module extends JukitoModule {
        protected void configureTest() {
            install(new FactoryModuleBuilder().build(WidgetsFactory.class));

        }
    }

//    @Inject
//    WidgetsFactory widgetsFactory;


    @Test
    public void onReveal_anytime_setsTitleIntoView(WidgetsFactory widgetsFactory) {
        MenuPresenter menuPresenter = widgetsFactory.createTopMenu("Antoine");
        //given
        String title = "GWTP Samples - Unit Testing";

        //when
        menuPresenter.onReveal();

        //then
        verify(menuPresenter.getView()).setUserName("Antoine");


    }
}
