package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSession;
import org.spaceinvaders.server.dao.EvaluationDao;
import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

public class EvaluationResourceImpl implements EvaluationResource {
    private UserSession userSession;
    private EvaluationDao evaluationDao;

    @Inject
    EvaluationResourceImpl(
            EvaluationDao evaluationDao,
            UserSession userSession) {
        this.evaluationDao = evaluationDao;
        this.userSession = userSession;
    }

    @Override
    public TreeMap<String, Evaluation> getAllEvaluations(int semesterID) {
        return evaluationDao.getAll(userSession.getUserId(), semesterID);
    }

    @Override
    public TreeMap<String, Evaluation> getApEvaluations(int semesterID, int apID) {
        return evaluationDao.getApEvaluations(userSession.getUserId(), semesterID, apID);
    }
}
