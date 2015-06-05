package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import org.spaceinvaders.client.application.griddemo.GridDemoPresenter;

import javax.inject.Inject;

public class SemesterGradesView extends ViewImpl implements SemesterGradesPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SemesterGradesView> {
    }

    @UiField
    HTMLPanel menuPanel;

    @Inject
    SemesterGradesView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addToSlot(Object slot, IsWidget content) {
        super.addToSlot(slot, content);

        if (slot == SemesterGradesPresenter.SLOT_MENU_WIDGET) {
            menuPanel.add(content);
        }
    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
        super.removeFromSlot(slot, content);

        if (slot == SemesterGradesPresenter.SLOT_MENU_WIDGET) {
            menuPanel.remove(content);
        }
    }


}
