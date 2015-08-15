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
    public SemesterInfo getSemesterInfo(String cip, int semesterId) {
        return SemesterInfoMock(semesterId);
    }

    @Override
    public List<SemesterInfo> getSemesterInfoList(String cip) {
        List<SemesterInfo> semesterInfoList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            semesterInfoList.add(SemesterInfoMock(i));
        }
        return semesterInfoList;
    }

    private SemesterInfo SemesterInfoMock(int semesterId) {
        List<Evaluation> evals = new ArrayList<>();
        List<Ap> aps = new ArrayList<>();
        List<Competence> gen501Competences = new ArrayList<>();
        Ap gen501;

        List<Competence> gen402Competences = new ArrayList<>();
        Ap gen402;

        List<Competence> gen666Competences = new ArrayList<>();
        Ap gen666;

        int evalIndex = 0;
        evals.add(new Evaluation("APP1 - Rapport", evalIndex++));
        evals.add(new Evaluation("APP1 - Sommatif", evalIndex++));
        evals.add(new Evaluation("APP2 - Sommatif", evalIndex++));
        evals.add(new Evaluation("APP3 - Rapport", evalIndex++));
        evals.add(new Evaluation("APP3 - Sommatif", evalIndex));

        int compIndex = 0;
        gen501Competences.add(new Competence("GEN501-1", compIndex++));
        gen501Competences.add(new Competence("GEN501-2", compIndex++));
        gen501 = new Ap("GEN501", 0, gen501Competences);

        gen402Competences.add(new Competence("GEN402-1", compIndex++));
        gen402 = new Ap("GEN402", 1, gen402Competences);

        gen666Competences.add(new Competence("GEN666-1", compIndex++));
        gen666Competences.add(new Competence("GEN666-2", compIndex));
        gen666 = new Ap("GEN666", 2, gen666Competences);

        aps.add(gen501);
        aps.add(gen402);
        aps.add(gen666);

        return new SemesterInfo(semesterId, "Session " + semesterId, aps, evals);
    }

}
