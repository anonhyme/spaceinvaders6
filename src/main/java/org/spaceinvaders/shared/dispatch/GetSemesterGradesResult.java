package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.Result;
import org.spaceinvaders.shared.dto.EvaluationResultsDto;

import java.util.List;

/**
 * The result of a {@link GetSemesterGradesAction} action.
 */
public class GetSemesterGradesResult implements Result {
    private List<EvaluationResultsDto> evaluationResults;

    public GetSemesterGradesResult(final List<EvaluationResultsDto> evaluationResults) {
        this.evaluationResults = evaluationResults;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesResult() {
    }

    public List<EvaluationResultsDto> getEvaluationResults() {
        return evaluationResults;
    }
}
