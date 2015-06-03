
package org.spaceinvaders.client.widgets.grid;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    public interface Binder extends UiBinder<HTMLPanel, GridView> {
    }

    @UiField
    HTMLPanel panel;

    @Inject
    GridView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }
}
