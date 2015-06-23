
package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CellModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
//        bindPresenterWidget(CellPresenter.class, CellPresenter.MyView.class, CellView.class);
        bind(CellPresenter.MyView.class).to(CellView.class);
        install(new GinFactoryModuleBuilder().build(WidgetsFactory.class));
    }
}
