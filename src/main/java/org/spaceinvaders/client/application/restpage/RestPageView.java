package org.spaceinvaders.client.application.restpage;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import javax.inject.Inject;


public class RestPageView extends ViewImpl implements RestPagePresenter.MyView {
    interface Binder extends UiBinder<Widget, RestPageView> {
    }

    @UiField
    SimplePanel main;

    @Inject
    RestPageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
