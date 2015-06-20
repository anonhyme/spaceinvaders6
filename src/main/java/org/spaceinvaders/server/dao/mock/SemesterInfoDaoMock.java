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

        if(semesterID == 3) {
            competences.add(new Competence("GEN666", "GEN666-3"));
        }

        return new SemesterInfo(competences, evals, "Session " + semesterID, semesterID);
    }

    @Override
    public List<SemesterInfo> getSemesterInfoList(String cip) {
        //TODO should use the cip to fetch all valid session for the current user
        List<SemesterInfo> semesterInfoList = new ArrayList<SemesterInfo>();
        for (int i = 1; i < 5; i++) {
            SemesterInfo semesterInfo = new SemesterInfo();
            semesterInfo.setId(i);
            semesterInfo.setLabel("Session " + i);
            semesterInfoList.add(semesterInfo);
        }
        return semesterInfoList;
    }
}
