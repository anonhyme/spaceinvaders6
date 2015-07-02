package org.spaceinvaders.server.dao.mock;

import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;

public class SemesterInfoDaoMock implements SemesterInfoDao {
    @Override
    public SemesterInfo getSemesterInfo(String cip, int semesterID) {
        List<Evaluation> evals = new ArrayList<>();

        evals.add(new Evaluation("APP1 - Rapport", 0));
        evals.add(new Evaluation("APP1 - Sommatif", 1));

        evals.add(new Evaluation("APP2 - Sommatif", 2));

        evals.add(new Evaluation("APP3 - Rapport", 3));
        evals.add(new Evaluation("APP3 - Sommatif", 4));

        List<Competence> gen501Competences = new ArrayList<>();
        gen501Competences.add(new Competence("GEN501-1", 0));
        gen501Competences.add(new Competence("GEN501-2", 1));
        Ap gen501 = new Ap("GEN501", 0, gen501Competences);

        List<Competence> gen402Competences = new ArrayList<>();
        gen402Competences.add(new Competence("GEN402-1", 2));
        Ap gen402 = new Ap("GEN402", 1, gen402Competences);

        List<Competence> gen666Competences = new ArrayList<>();
        gen666Competences.add(new Competence("GEN666-1", 3));
        gen666Competences.add(new Competence("GEN666-2", 4));
        Ap gen666 = new Ap("GEN666", 2, gen666Competences);

        List<Ap> aps = new ArrayList<>();
        aps.add(gen501);
        aps.add(gen402);
        aps.add(gen666);

        return new SemesterInfo(evals, aps);
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
