package org.spaceinvaders.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.spaceinvaders.shared.model.EvaluationGrid;
import org.spaceinvaders.shared.model.SemesterInfo;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

public interface SemesterServiceAsync {
    void fetchSemesterInfo(AsyncCallback<SemesterInfo> async);
}
