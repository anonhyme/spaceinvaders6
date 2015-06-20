package org.spaceinvaders.client.application.semester;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;

import javax.inject.Inject;

public class SemesterView extends ViewImpl implements SemesterPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SemesterView> {
    }

    @UiField
    SimplePanel gridPanel;

    @Inject
    SemesterView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addGrid(IsWidget gridWidget) {
        gridPanel.add(gridWidget);
    }
}
