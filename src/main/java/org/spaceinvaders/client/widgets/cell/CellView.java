
package org.spaceinvaders.client.widgets.cell;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import org.gwtbootstrap3.client.ui.Button;

public class CellView extends ViewImpl implements CellPresenter.MyView {

    public interface Binder extends UiBinder<Widget, CellView> {
    }

    @UiField
    HTMLPanel panel;

    @UiField
    SimplePanel panelButton;

    @Inject
    CellView(Binder binder) {
        Button button = new Button("hello");
        initWidget(binder.createAndBindUi(this));
        panelButton.add(button);

//        panel.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                // Handle the click
//            }
//        });

    }

    @Override
    public void setData(String data) {
        panel.getElement().setInnerHTML(data);
    }

//    @UiHandler("panel")
//    void onClick(ClickEvent event) {
//        GWT.log("hello");
//    }

}
