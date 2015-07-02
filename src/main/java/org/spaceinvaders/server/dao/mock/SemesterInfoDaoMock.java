package org.spaceinvaders.server.dao.mock;

import org.spaceinvaders.server.dao.SemesterInfoDao;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.List;

public class SemesterInfoDaoMock implements SemesterInfoDao {
    //TODO cip is never used....
    @Override
    public SemesterInfo getSemesterInfo(String cip, int semesterId) {
        return makeRandomSemesterInfo(semesterId);
    }

    @Override
    public List<SemesterInfo> getSemesterInfoList(String cip, int semesterId) {
        //TODO should use the cip to fetch all valid session for the current user
        List<SemesterInfo> semesterInfoList = new ArrayList<SemesterInfo>();
        for (int i = 1; i < 5; i++) {
            semesterInfoList.add(makeRandomSemesterInfo(semesterId));
        }
        return semesterInfoList;
    }

    private SemesterInfo makeRandomSemesterInfo(int semesterId) {
        List<Evaluation> evals = new ArrayList<>();
        List<Ap> aps = new ArrayList<>();
        List<Competence> gen501Competences = new ArrayList<>();
        Ap gen501;

        List<Competence> gen402Competences = new ArrayList<>();
        Ap gen402;

        List<Competence> gen666Competences = new ArrayList<>();
        Ap gen666;

        evals.add(new Evaluation("APP1 - Rapport", 0));
        evals.add(new Evaluation("APP1 - Sommatif", 1));
        evals.add(new Evaluation("APP2 - Sommatif", 2));
        evals.add(new Evaluation("APP3 - Rapport", 3));
        evals.add(new Evaluation("APP3 - Sommatif", 4));

        gen501Competences.add(new Competence("GEN501-1", 0));
        gen501Competences.add(new Competence("GEN501-2", 1));
        gen501 = new Ap("GEN501", 0, gen501Competences);

        gen402Competences.add(new Competence("GEN402-1", 2));
        gen402 = new Ap("GEN402", 1, gen402Competences);

        gen666Competences.add(new Competence("GEN666-1", 3));
        gen666Competences.add(new Competence("GEN666-2", 4));
        gen666 = new Ap("GEN666", 2, gen666Competences);

        aps.add(gen501);
        aps.add(gen402);
        aps.add(gen666);

        return new SemesterInfo(evals, aps, semesterId);
    }

}
