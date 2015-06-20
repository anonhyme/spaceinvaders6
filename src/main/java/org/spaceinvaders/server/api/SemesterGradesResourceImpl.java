package org.spaceinvaders.server.api;

import com.google.inject.Inject;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.List;
import java.util.TreeMap;

public class SemesterGradesResourceImpl implements SemesterGradesResource {
    private UserSessionImpl userSession;
    private CompetenceEvalResultDao competenceEvalResultDao;

    @Inject
    SemesterGradesResourceImpl(
            CompetenceEvalResultDao competenceEvalResultDao,
            UserSessionImpl userSession) {
        this.competenceEvalResultDao = competenceEvalResultDao;
        this.userSession = userSession;
    }

    @Override
    public TreeMap<String, Evaluation> getAllEvaluations(int semesterID) {
        List<CompetenceEvalResult> results = competenceEvalResultDao.getSemesterResults(userSession.getUserId(), semesterID);
        return Evaluation.getEvaluations(results);
    }

    @Override
    public List<CompetenceEvalResult> getAllCompetenceEvalResults(int semesterID) {
        return competenceEvalResultDao.getSemesterResults(userSession.getUserId(), semesterID);
    }

    @Override
    public List<CompetenceEvalResult> getAllApResults(int semesterID, int apID) {
        return competenceEvalResultDao.getAPResults(userSession.getUserId(), semesterID, apID);
    }
}
