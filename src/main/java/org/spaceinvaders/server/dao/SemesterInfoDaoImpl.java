package org.spaceinvaders.server.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.spaceinvaders.server.entities.CompetenceEntity;
import org.spaceinvaders.server.entities.EvaluationEntity;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

public class SemesterInfoDaoImpl implements SemesterInfoDao {
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public SemesterInfoDaoImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    @Transactional
    public List<EvaluationEntity> getSemesterEvals(String cip, int semesterID) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.clear();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetSemesterEvals");
        query.setParameter("student_id", cip);
        query.setParameter("session_id", semesterID);
        query.execute();

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<CompetenceEntity> getSemesterCompetences(String cip, int semesterID) {
        EntityManager entityManager = entityManagerProvider.get();

        entityManager.clear();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetSemesterCompetences");
        query.setParameter("student_id", cip);
        query.setParameter("session_id", semesterID);
        query.execute();

        return query.getResultList();
    }

    public SemesterInfo getSemesterInfo(String cip, int semesterID) {
        List<Evaluation> evals = EvaluationEntitiesToDtos((getSemesterEvals(cip, semesterID)));
        List<Competence> competences = CompetenceEntitiesToDtos(getSemesterCompetences(cip, semesterID));

        SemesterInfo semesterInfo = new SemesterInfo();
        semesterInfo.setCompetences(competences);
        semesterInfo.setEvals(evals);

        return semesterInfo;
    }

    @Override
    public List<SemesterInfo> getSemesterInfoList(String cip) {
        return null;
    }

    public List<Evaluation> EvaluationEntitiesToDtos(List<EvaluationEntity> entities) {
        List<Evaluation> dtos = new ArrayList<>();
        for (EvaluationEntity entity : entities) {
            dtos.add(EvaluationEntityToDto(entity));
        }
        return dtos;
    }

    public List<Competence> CompetenceEntitiesToDtos(List<CompetenceEntity> entities) {
        List<Competence> dtos = new ArrayList<>();
        for (CompetenceEntity entity : entities) {
            dtos.add(CompetenceEntityToDto(entity));
        }
        return dtos;
    }

    public Competence CompetenceEntityToDto(CompetenceEntity entity) {
        Competence dto = new Competence();
        dto.setApLabel(entity.getApLabel());
        dto.setCompetenceLabel(entity.getCompetenceLabel());
        return dto;
    }

    public Evaluation EvaluationEntityToDto(EvaluationEntity entity) {
        Evaluation dto = new Evaluation();
        dto.setEvaluationLabel(entity.getEvaluationLabel());
        return dto;
    }
}
