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
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class GetSemesterGradesHandler implements ActionHandler<GetSemesterGradesAction, GetSemesterGradesResult> {
    private CompetenceEvalResultDao competenceEvalResultDao;
    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    GetSemesterGradesHandler(
            ServletContext servletContext,
            Provider<HttpServletRequest> requestProvider,
            CompetenceEvalResultDao competenceEvalResultDao) {
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
        this.competenceEvalResultDao = competenceEvalResultDao;
    }

    @Override
    public GetSemesterGradesResult execute(GetSemesterGradesAction action, ExecutionContext context)
            throws ActionException {
        int semesterID = action.getSemesterID();
        String cip = action.getCip();

        List<CompetenceEvalResult> results = competenceEvalResultDao.getSemesterResults(cip, semesterID);

        Map<String, Evaluation> evals = Evaluation.getEvaluations(results);

        return new GetSemesterGradesResult(results);
    }

    @Override
    public Class<GetSemesterGradesAction> getActionType() {
        return GetSemesterGradesAction.class;
    }

    @Override
    public void undo(GetSemesterGradesAction action, GetSemesterGradesResult result, ExecutionContext context)
            throws ActionException {
        // Not undoable
    }
}
