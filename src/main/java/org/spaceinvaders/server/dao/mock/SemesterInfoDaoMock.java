package org.spaceinvaders.server.dao.mock;

import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.server.entities.CompetenceEntity;
import org.spaceinvaders.server.entities.EvaluationEntity;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;

public class SemesterInfoDaoMock implements SemesterInfoDao {
    @Override
    public List<EvaluationEntity> getSemesterEvals(String cip, int semesterID) {
        return null;
    }

    @Override
    public List<CompetenceEntity> getSemesterCompetences(String cip, int semesterID) {
        return null;
    }

    @Override
    public SemesterInfo getSemesterInfo(String cip, int semesterID) {
        List<Evaluation> evals = new ArrayList<>();
        evals.add(new Evaluation("Sommatif APP2"));
        evals.add(new Evaluation("Sommatif APP3"));

        List<Competence> competences = new ArrayList<>();
        competences.add(new Competence("GEN501", "GEN501-1"));
        competences.add(new Competence("GEN501", "GEN501-2"));
        competences.add(new Competence("GEN402", "GEN402-1"));
        competences.add(new Competence("GEN666", "GEN666-1"));
        competences.add(new Competence("GEN666", "GEN666-2"));

        return new SemesterInfo(competences, evals, "Session 6", semesterID);
    }

    @Override
    public String getSemesterLabel(String cip, int semesterID) {
        return null;
    }

    @Override
    public List<SemesterInfo> getAllSemestersInfo(String cip) {
        List<SemesterInfo> semestersInfo = new ArrayList<>();

        List<Evaluation> evals1 = new ArrayList<>();
        evals1.add(new Evaluation("Sommatif APP2"));
        evals1.add(new Evaluation("Sommatif APP3"));

        List<Evaluation> evals2 = new ArrayList<>();
        evals2.add(new Evaluation("Sommatif APP4"));
        evals2.add(new Evaluation("Sommatif APP5"));

        List<Competence> competences1 = new ArrayList<>();
        competences1.add(new Competence("GEN501", "GEN501-1"));
        competences1.add(new Competence("GEN501", "GEN501-2"));
        competences1.add(new Competence("GEN402", "GEN402-1"));
        competences1.add(new Competence("GEN666", "GEN666-1"));
        competences1.add(new Competence("GEN666", "GEN666-2"));

        List<Competence> competences2 = new ArrayList<>();
        competences2.add(new Competence("GEN502", "GEN502-1"));
        competences2.add(new Competence("GEN502", "GEN502-2"));

        semestersInfo.add(new SemesterInfo(competences1,evals1,"Session 5",5));
        semestersInfo.add(new SemesterInfo(competences2,evals2,"Session 6",6));

        return semestersInfo;
    }
}
