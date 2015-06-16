package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class GetSemesterGradesAction extends UnsecuredActionImpl<GetSemesterGradesResult> {
    private int semesterID;
//    private String cip;

    public GetSemesterGradesAction(int semesterID) {
//        this.cip = cip;
        this.semesterID = semesterID;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterGradesAction() {
    }

//    public String getCip() {
//        return cip;
//    }

    public int getSemesterID() {
        return semesterID;
    }
}
