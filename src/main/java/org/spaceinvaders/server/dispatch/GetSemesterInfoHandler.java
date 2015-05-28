/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.spaceinvaders.server.dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;
import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class GetSemesterInfoHandler implements ActionHandler<GetSemesterInfoAction, GetSemesterInfoResult> {
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    SemesterInfoDao semesterInfoDao;

    @Inject
    GetSemesterInfoHandler(
            ServletContext servletContext,
            Provider<HttpServletRequest> requestProvider,
            CompetenceEvalResultDao competenceEvalResultDao) {
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
    }

    @Override
    public GetSemesterInfoResult execute(GetSemesterInfoAction action, ExecutionContext context)
            throws ActionException {
        int semesterID = action.getSemesterID();
        String cip = action.getCip();

        SemesterInfo semesterInfo = semesterInfoDao.getSemesterInfo(cip, semesterID);

        return new GetSemesterInfoResult(semesterInfo);
    }

    @Override
    public Class<GetSemesterInfoAction> getActionType() {
        return GetSemesterInfoAction.class;
    }

    @Override
    public void undo(GetSemesterInfoAction action, GetSemesterInfoResult result, ExecutionContext context)
            throws ActionException {
        // Not undoable
    }
}
