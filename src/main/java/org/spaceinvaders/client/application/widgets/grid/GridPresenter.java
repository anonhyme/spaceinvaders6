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

    protected void onBind() {
        super.onBind();
    }

    @Override
    public GridPresenter getInstance() {
        return this;
    }

//    @Override
//    public void onApSelected(ApSelectedEvent event) {
//        String apLabel = event.getAp();
//        Ap ap = semesterInfo.findAp(apLabel);
//
//        // Filter results for this AP
//        TreeMap<String, Evaluation> apEvals = new TreeMap<>();
//        for (String competenceLabel : evaluations.keySet()) {
//            Evaluation eval = evaluations.get(competenceLabel);
//            Evaluation apEval = eval.getApResults(ap);
//
//            if (apEval.getResults().size() != 0) {
//                apEvals.put(eval.getLabel(), apEval);
//            }
//        }
//
//        apPresenter.update(ap, apEvals);
//
//        PlaceRequest placeRequest = new PlaceRequest.Builder().
//                nameToken(NameTokens.APpage).
//                build();
//        placeManager.revealPlace(placeRequest);
//    }
}
