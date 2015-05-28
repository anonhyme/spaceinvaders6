package org.spaceinvaders.server.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.spaceinvaders.server.entities.*;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CompetenceEvalResultDaoImpl implements CompetenceEvalResultDao {
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public CompetenceEvalResultDaoImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    @Transactional
    public List<CompetenceEvalResultEntity> getSemesterResultsEntities(String cip, int semesterID) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.clear();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetSemesterEvalResults");
        query.setParameter("student_id", cip);
        query.setParameter("session_id", semesterID); // TODO : should be named semester_id
        query.execute();

        return query.getResultList();
    }

    public List<CompetenceEvalResult> getSemesterResults(String cip, int semesterID) {
        List<CompetenceEvalResultEntity> results = getSemesterResultsEntities(cip, semesterID);
        return entitiesToDtos(results);
    }

    public List<CompetenceEvalResult> entitiesToDtos(List<CompetenceEvalResultEntity> entities) {
        List<CompetenceEvalResult> dtos = new ArrayList<>();
        for (CompetenceEvalResultEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    // TODO : I think that all this Dto business is ugly (needs to be tested if it stays this way)
    public CompetenceEvalResult entityToDto(CompetenceEvalResultEntity entity) {
        CompetenceEvalResult dto = new CompetenceEvalResult();
        dto.setEvalLabel(entity.getEvalLabel());
        dto.setCourseLabel(entity.getCourseLabel());
        dto.setCompetenceLabel(entity.getCompetenceLabel());
        dto.setResultValue(entity.getResultValue());
        dto.setAvgResultValue(entity.getAvgResultValue());
        dto.setMaxResultValue(entity.getMaxResultValue());
        dto.setStandardDev(entity.getStandardDev());
        return dto;
    }
}