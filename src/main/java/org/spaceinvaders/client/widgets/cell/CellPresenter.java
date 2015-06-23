
package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.cell.client.HasCell;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class CellPresenter extends PresenterWidget<CellPresenter.MyView> {
    public interface MyView extends View {
        void setData(String data);
    }

    private final String data;

    @Inject
    CellPresenter(EventBus eventBus,
                  MyView myView,
                  @Assisted String data) {
        super(eventBus, myView);
        this.data = data;
        myView.setData(data);
    }

    protected void onBind() {
        super.onBind();
    }


}
