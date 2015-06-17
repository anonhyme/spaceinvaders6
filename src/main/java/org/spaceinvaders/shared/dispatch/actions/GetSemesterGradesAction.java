package org.spaceinvaders.shared.dispatch.actions;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

import org.spaceinvaders.shared.dispatch.results.GetSemesterGradesMapResult;

public class GetSemesterGradesAction extends UnsecuredActionImpl<GetSemesterGradesMapResult> {
    private int semesterID;

    public GetSemesterGradesAction(int semesterID) {
        this.semesterID = semesterID;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesAction() {
    }

    public int getSemesterID() {
        return semesterID;
    }
}
