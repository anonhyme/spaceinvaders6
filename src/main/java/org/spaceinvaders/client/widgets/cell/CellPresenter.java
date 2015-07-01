package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.widgets.cellGwt.events.CellHoverEvent;
import org.spaceinvaders.client.widgets.cellGwt.events.CellHoverEventHandler;

public class CellPresenter extends PresenterWidget<CellPresenter.MyView> implements HasCellHoverHandlers, CellUiHandlers {


    public interface MyView extends View, HasUiHandlers<CellUiHandlers> {
        void setViewData(String data);
    }

    private final String data;
    private String tmp;

    @Inject
    CellPresenter(EventBus eventBus,
                  MyView myView,
                  @Assisted String data) {
        super(eventBus, myView);
        this.data = data;
        GWT.log("CellPresenter :::: " + data);
        myView.setViewData(data);
    }

    protected void onBind() {
        super.onBind();
    }

    public String getData() {

        return data;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    private ClickHandler setClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                GWT.log("hellooo !");
            }
        };
    }

    @Override
    public HandlerRegistration addCellHoverHandler(CellHoverEventHandler handler, Object source) {
        HandlerRegistration hr = getEventBus().addHandlerToSource(CellHoverEvent.TYPE, source, handler);
        registerHandler(hr);
        return hr;
    }


    @Override
    public void addThatEvent() {

    }
}
