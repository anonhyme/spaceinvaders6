package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.Result;

import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

/**
 *  @deprecated, replaced by {@link org.spaceinvaders.shared.api.SemesterGradesResource}
 */
@Deprecated
public class GetSemesterGradesResult implements Result {
    private List<CompetenceEvalResult> evaluationResults;

    public GetSemesterGradesResult(final List<CompetenceEvalResult> evaluationResults) {
        this.evaluationResults = evaluationResults;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesResult() {
    }

    public List<CompetenceEvalResult> getEvaluationResults() {
        return evaluationResults;
    }
}
