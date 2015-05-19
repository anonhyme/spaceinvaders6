
package org.spaceinvaders.client.application.bootstrapExample;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.ViewImpl;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Div;
import org.spaceinvaders.client.application.ui.menu.MainMenu;

public class BootstrapExampleView extends ViewImpl implements BootstrapExamplePresenter.MyView {
    @UiField
    MainMenu mainMenu;

    public interface Binder extends UiBinder<Widget, BootstrapExampleView> {
    }


    @Inject
    BootstrapExampleView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setNavigationHistory(String navigationHistory) {
//        this.navigationHistory.setText(navigationHistory);
    }
}
