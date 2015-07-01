package org.spaceinvaders.server.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import org.spaceinvaders.server.entities.CompetenceEvalResultEntity;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

@Singleton
public class EvaluationDaoImpl implements EvaluationDao {
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public EvaluationDaoImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    @Transactional
    public TreeMap<String, Evaluation> getAll(String cip, int semesterID) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.clear();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetSemesterEvalResults");
        query.setParameter("student_id", cip);
        query.setParameter("session_id", semesterID); // TODO : should be named semester_id
        query.execute();

        List<CompetenceEvalResultEntity> entities = query.getResultList();

        return getEvaluations(entities);
    }

    private TreeMap<String, Evaluation> getEvaluations(List<CompetenceEvalResultEntity> entities) {

        TreeMap<String, Evaluation> evaluations = new TreeMap<>();

        int evalIndex = 0;
        int compIndex = 0;
        for (CompetenceEvalResultEntity entity : entities) {
            String evalLabel = entity.getEvalLabel();

            if (!evaluations.containsKey(entity.getEvalLabel())) {
                evaluations.put(evalLabel, new Evaluation(evalLabel, evalIndex));
                evalIndex++;
            }

            Evaluation eval = evaluations.get(evalLabel);
            eval.addResult(
                    entity.getCompetenceLabel(),
                    new Result(entity.getResultValue(), entity.getAvgResultValue(), entity.getMaxResultValue(), entity.getStandardDev()));
            compIndex++;
        }


        return evaluations;
    }
    //Todo: create SQL query
    @Override
    @Transactional
    public TreeMap<String, Evaluation> getApEvaluations(String cip, int semesterID, int apID)
    {
        TreeMap<String, Evaluation> bleh = new TreeMap<String, Evaluation>();
        return bleh;
    }
}