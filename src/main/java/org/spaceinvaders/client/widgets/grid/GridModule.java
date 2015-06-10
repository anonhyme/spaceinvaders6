
package org.spaceinvaders.client.widgets.grid;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import org.spaceinvaders.client.widgets.commons.WidgetsFactory;
import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuPresenter;
import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuView;

public class GridModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(GridPresenter.class, GridPresenter.MyView.class, GridView.class);
        bind(MaterialMenuPresenter.MyView.class).to(MaterialMenuView.class);
        install(new GinFactoryModuleBuilder().build(WidgetsFactory.class));
    }
}
