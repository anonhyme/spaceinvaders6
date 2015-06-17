package org.spaceinvaders.shared.dispatch.results;

import com.gwtplatform.dispatch.rpc.shared.Result;

import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dto.SemesterInfo;

/**
 * The result of a {@link GetSemesterGradesAction} action.
 */
public class GetSemesterInfoResult implements Result {
    private SemesterInfo semesterInfo;

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    public GetSemesterInfoResult() {
    }

    public GetSemesterInfoResult(SemesterInfo semesterInfo) {
        this.semesterInfo = semesterInfo;
    }

    public SemesterInfo getSemesterInfo() {
        return semesterInfo;
    }

    public void setSemesterInfo(SemesterInfo semesterInfo) {
        this.semesterInfo = semesterInfo;
    }
}