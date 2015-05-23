package org.spaceinvaders.server.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.spaceinvaders.server.entities.*;
import org.spaceinvaders.shared.dto.EvaluationResultsDto;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SemesterResultsDaoImpl implements SemesterResultsDao {
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public SemesterResultsDaoImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    @Transactional
    public List<EvaluationResultsEntity> getSemesterResultsEntities(String cip, int semesterID) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.clear();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetSemesterResults");
        query.setParameter("student_id", cip);
        query.setParameter("session_id", semesterID);
        query.execute();

        return query.getResultList();
    }

    public List<EvaluationResultsDto> getSemesterResults(String cip, int semesterID) {
        List<EvaluationResultsEntity> results = getSemesterResultsEntities(cip, semesterID);
        return entitiesToDtos(results);
    }

    public List<EvaluationResultsDto> entitiesToDtos(List<EvaluationResultsEntity> entities) {
        List<EvaluationResultsDto> dtos = new ArrayList<>();

        for (EvaluationResultsEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }

        return dtos;
    }

    // TODO : I think that all this Dto business is ugly (needs to be tested if it stays this way)
    public EvaluationResultsDto entityToDto(EvaluationResultsEntity entity) {
        EvaluationResultsDto dto = new EvaluationResultsDto();
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