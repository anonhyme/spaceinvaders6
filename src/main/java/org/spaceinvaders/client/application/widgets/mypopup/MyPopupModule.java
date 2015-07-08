package org.spaceinvaders.client.application.widgets.mypopup;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MyPopupModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(MyPopupPresenter.class, MyPopupPresenter.MyView.class, MyPopupView.class);
    }
}
