
package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellWidget;
import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Popover;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;
import org.spaceinvaders.client.widgets.cellGwt.ResultCell;


public class CellView extends ViewWithUiHandlers<CellUiHandlers> implements CellPresenter.MyView {

//    @UiField
//    Popover popover;

    public interface Binder extends UiBinder<Widget, CellView> {
    }

//    @UiField
//    SimplePanel panel;

    @Inject
    CellView(Binder binder) {
        initWidget(binder.createAndBindUi(this));

    }

    @Override
    public void setViewData(String data) {
//        HTMLPanel htmlPanel = new HTMLPanel("hello");
//        htmlPanel.getElement().setInnerHTML(data);
//        panel.add(htmlPanel);
//        addDataPopover(data);
    }

//    private void addDataPopover(String data) {
//        Popover popover = new Popover();
//        popover.add(new HTML("fdsa"));
//        popover.setTitle("hehehe");
//        popover.setContent("da fuk");
//        panel.setWidget(popover);
//    }
}
