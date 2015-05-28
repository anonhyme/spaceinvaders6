package org.spaceinvaders.server.dao.dummy;

import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.entities.CompetenceEvalResultEntity;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.List;

public class CompetenceEvalResultDummyDao implements CompetenceEvalResultDao {
    @Override
    public List<CompetenceEvalResultEntity> getSemesterResultsEntities(String cip, int semesterID) {
        return null;
    }

    @Override
    public List<CompetenceEvalResult> getSemesterResults(String cip, int semesterID) {
        List<CompetenceEvalResult> results = new ArrayList<>();

        CompetenceEvalResult result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP2");
        result.setCourseLabel("GEN501");
        result.setCompetenceLabel("GEN501-1");
        result.setResultValue(80);
        result.setAvgResultValue(75);
        result.setMaxResultValue(120);
        result.setStandardDev(6);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP2");
        result.setCourseLabel("GEN501");
        result.setCompetenceLabel("GEN501-2");
        result.setResultValue(56);
        result.setAvgResultValue(50);
        result.setMaxResultValue(70);
        result.setStandardDev(5);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP2");
        result.setCourseLabel("GEN402");
        result.setCompetenceLabel("GEN402-1");
        result.setResultValue(74);
        result.setAvgResultValue(70);
        result.setMaxResultValue(75);
        result.setStandardDev(11);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP3");
        result.setCourseLabel("GEN666");
        result.setCompetenceLabel("GEN666-1");
        result.setResultValue(80);
        result.setAvgResultValue(75);
        result.setMaxResultValue(85);
        result.setStandardDev(3);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP3");
        result.setCourseLabel("GEN666");
        result.setCompetenceLabel("GEN666-2");
        result.setResultValue(80); //80, 73, 110, 4;
        result.setAvgResultValue(73);
        result.setMaxResultValue(110);
        result.setStandardDev(4);
        results.add(result);

        return results;
    }
}
