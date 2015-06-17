package org.spaceinvaders.server.dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.results.GetSemesterGradesMapResult;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class GetSemesterGradesHandler implements ActionHandler<GetSemesterGradesAction, GetSemesterGradesMapResult> {
    private CompetenceEvalResultDao competenceEvalResultDao;
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    public UserSessionImpl userSession;

    @Inject
    GetSemesterGradesHandler(
            ServletContext servletContext,
            Provider<HttpServletRequest> requestProvider,
            CompetenceEvalResultDao competenceEvalResultDao) {
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
        this.competenceEvalResultDao = competenceEvalResultDao;
    }

    @Override
    public GetSemesterGradesMapResult execute(GetSemesterGradesAction action, ExecutionContext context)
            throws ActionException {
        int semesterID = action.getSemesterID();
//        String cip = action.getCip();

        List<CompetenceEvalResult> results = competenceEvalResultDao.getSemesterResults(userSession.getUserId(), semesterID);

        Map<String, Evaluation> evals = Evaluation.getEvaluations(results);

        List<Evaluation> listEvaluation = new ArrayList<Evaluation>(evals.values());

        return new GetSemesterGradesMapResult(listEvaluation);
    }

    @Override
    public Class<GetSemesterGradesAction> getActionType() {
        return GetSemesterGradesAction.class;
    }

    @Override
    public void undo(GetSemesterGradesAction action, GetSemesterGradesMapResult result, ExecutionContext context)
            throws ActionException {
        // Not undoable
    }
}
