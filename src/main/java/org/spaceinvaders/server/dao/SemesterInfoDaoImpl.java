package org.spaceinvaders.server.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.spaceinvaders.server.entities.CompetenceEntity;
import org.spaceinvaders.server.entities.EvaluationEntity;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;
import java.util.TreeMap;
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
        List<EvaluationEntity> evals = (getSemesterEvals(cip, semesterID));
        List<CompetenceEntity> competences = getSemesterCompetences(cip, semesterID);

        int i = 0;
        Map<String, Evaluation> evalsMap = new TreeMap<>();
        for (EvaluationEntity eval : evals) {
            String evalLabel = eval.getEvaluationLabel();

            if (!evalsMap.containsKey(evalLabel)) {
                evalsMap.put(evalLabel, new Evaluation(evalLabel, i++));
            }
        }

        i = 0;
        Map<String, Ap> apMap = new TreeMap<>();
        for (CompetenceEntity competence : competences) {
            String apLabel = competence.getApLabel();

            if (!apMap.containsKey(apLabel)) {
                apMap.put(apLabel, new Ap(apLabel, i++, new ArrayList<Competence>()));
            }
        }

        i = 0;
        for (CompetenceEntity competence : competences) {
            Ap ap = apMap.get(competence.getApLabel());
            String competenceLabel = competence.getCompetenceLabel();

            if (!ap.getCompetencesStrings().contains(competence.getCompetenceLabel())) {
                ap.getCompetences().add(new Competence(competenceLabel, i++));
            }
        }

        SemesterInfo semesterInfo = new SemesterInfo();
        semesterInfo.setAps(new ArrayList<Ap>(apMap.values()));
        semesterInfo.setEvals(new ArrayList<Evaluation>(evalsMap.values()));

        return semesterInfo;
    }

    @Override
    public List<SemesterInfo> getSemesterInfoList(String cip) {
        throw new NotImplementedException();
    }
}
