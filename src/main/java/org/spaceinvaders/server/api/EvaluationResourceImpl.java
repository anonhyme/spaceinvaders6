package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dao.EvaluationDao;
import org.spaceinvaders.shared.api.EvaluationResource;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EvaluationResourceImpl implements EvaluationResource {
    private UserSessionImpl userSession;
    private EvaluationDao evaluationDao;

    @Inject
    EvaluationResourceImpl(
            EvaluationDao evaluationDao,
            UserSessionImpl userSession) {
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