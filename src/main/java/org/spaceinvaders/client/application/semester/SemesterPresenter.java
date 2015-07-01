package org.spaceinvaders.client.application.semester;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.grid.GridPresenter;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.cell.WidgetsFactory;
import org.spaceinvaders.client.widgets.cellGwt.events.CellHoverEvent;
import org.spaceinvaders.client.widgets.cellGwt.events.CellHoverEventHandler;

import javax.inject.Inject;

public class SemesterPresenter extends Presenter<SemesterPresenter.MyView, SemesterPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler, CellHoverEventHandler {


    public interface MyView extends View {
        void addGrid(IsWidget gridWidget);

    }

    private final WidgetsFactory widgetsFactory;

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterPresenter> {
    }

    private GridPresenter gridPresenter;

    private

    @Inject
    SemesterPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      WidgetsFactory widgetsFactory,
                      GridPresenter gridPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.widgetsFactory = widgetsFactory;
        this.gridPresenter = gridPresenter;
    }

    @Override
    protected void onBind() {
        super.onBind();
        GWT.log(widgetsFactory.createCell("hello").asWidget().toString());

        registerHandler();
        showGrid();
    }

    private void registerHandler() {
        addRegisteredHandler(SemesterChangedEvent.TYPE, this);
        addRegisteredHandler(CellHoverEvent.TYPE, this);
    }

    private void showGrid() {
        gridPresenter.updateGrid(0);
        getView().addGrid(gridPresenter);

    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        gridPresenter.updateGrid(event.getSemesterID());
    }

    @Override
    public void onCellHover(CellHoverEvent event) {
        GWT.log(event.getData());
    }
}