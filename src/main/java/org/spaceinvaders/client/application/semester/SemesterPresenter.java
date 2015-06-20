package org.spaceinvaders.client.application.semester;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

import com.arcbees.gsss.grid.client.GridResources;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.grid.GridPresenter;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;

import javax.inject.Inject;

public class SemesterPresenter extends Presenter<SemesterPresenter.MyView, SemesterPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler {
    public interface MyView extends View {
        void addGrid(IsWidget gridWidget);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterPresenter> {
    }

    private GridPresenter gridPresenter;

    @Inject
    SemesterPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      RestDispatch restDispatch,
                      GridPresenter gridPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.gridPresenter = gridPresenter;
    }

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler();
        showGrid();
    }

    private void registerHandler() {
        addRegisteredHandler(SemesterChangedEvent.TYPE, this);
    }

    private void showGrid() {
        gridPresenter.updateGrid(0);
        getView().addGrid(gridPresenter);
    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        GWT.log(SemesterPresenter.class.toString() + ": this is how you know if the semester changed");
    }
}