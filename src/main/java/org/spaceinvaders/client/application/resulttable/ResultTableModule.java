package org.spaceinvaders.client.application.resulttable;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ResultTableModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ResultTablePresenter.class, ResultTablePresenter.MyView.class, ResultTableView.class, ResultTablePresenter.MyProxy.class);
    }
}
