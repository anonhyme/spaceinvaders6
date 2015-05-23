package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.Result;
import org.spaceinvaders.shared.dto.CompetenceEvalResultDto;

import java.util.List;

/**
 * The result of a {@link GetSemesterGradesAction} action.
 */
public class GetSemesterGradesResult implements Result {
    private List<CompetenceEvalResultDto> evaluationResults;

    public GetSemesterGradesResult(final List<CompetenceEvalResultDto> evaluationResults) {
        this.evaluationResults = evaluationResults;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesResult() {
    }

    public List<CompetenceEvalResultDto> getEvaluationResults() {
        return evaluationResults;
    }
}
