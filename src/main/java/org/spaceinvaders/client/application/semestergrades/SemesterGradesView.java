package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

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
}
