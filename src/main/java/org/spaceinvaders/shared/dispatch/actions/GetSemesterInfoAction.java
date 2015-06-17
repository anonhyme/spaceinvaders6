package org.spaceinvaders.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

/**
 *  @deprecated, replaced by {@link org.spaceinvaders.shared.api.SemesterInfoResource}
 */
@Deprecated
public class GetSemesterInfoAction extends UnsecuredActionImpl<GetSemesterInfoResult> {
    private int semesterID;
//    private String cip;

    public GetSemesterInfoAction(int semesterID) {
//        this.cip = cip;
        this.semesterID = semesterID;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private GetSemesterInfoAction() {
    }

//    public String getCip() {
//        return cip;
//    }

    public int getSemesterID() {
        return semesterID;
    }
}
