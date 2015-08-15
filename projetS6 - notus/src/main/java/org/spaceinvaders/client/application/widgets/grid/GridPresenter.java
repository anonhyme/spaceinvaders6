package org.spaceinvaders.client.application.widgets.grid;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.shared.dto.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GridPresenter extends PresenterWidget<GridPresenter.MyView>
        implements GridUiHandlers {

    interface MyView extends View, HasUiHandlers<GridUiHandlers> {
        void updateSemesterTable(List<String> competencesLabels, List<Evaluation> evaluations);
    }

    @Inject
    public GridPresenter(EventBus eventBus,
                         MyView view) {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }

    public void updateGrid(List<String> competencesLabels, TreeMap<String, Evaluation> evaluations) {
        getView().updateSemesterTable(competencesLabels, new ArrayList<>(evaluations.values()));
    }

    @Override
    public GridPresenter getInstance() {
        return this;
    }
}
