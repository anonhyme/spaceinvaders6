package org.spaceinvaders.shared.dispatch.results;

import com.gwtplatform.dispatch.rpc.shared.Result;

import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.List;

/**
 * The result of a {@link GetSemesterGradesAction} action.
 */
public class GetSemesterGradesMapResult implements Result {
    private List<Evaluation> evaluations;

    public GetSemesterGradesMapResult(final List<Evaluation> evaluationResults) {
        this.evaluations = evaluationResults;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesMapResult() {
    }

    public List<Evaluation> getEvaluationMapResult() {
        return evaluations;
    }

}
