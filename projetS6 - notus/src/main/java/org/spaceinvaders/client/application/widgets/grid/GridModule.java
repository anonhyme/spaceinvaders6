package org.spaceinvaders.client.application.widgets.grid;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GridModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(GridPresenter.class, GridPresenter.MyView.class, GridView.class);
    }
}
