package org.spaceinvaders.server.dao.mock;

import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.entities.CompetenceEvalResultEntity;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompetenceEvalResultDaoMock implements CompetenceEvalResultDao {
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
        result.setResultValue(randInt(0, 200));
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(6);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP2");
        result.setCourseLabel("GEN501");
        result.setCompetenceLabel("GEN501-2");
        result.setResultValue(randInt(0, 200));
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(5);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP2");
        result.setCourseLabel("GEN402");
        result.setCompetenceLabel("GEN402-1");
        result.setResultValue(randInt(0, 200));
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(11);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP3");
        result.setCourseLabel("GEN666");
        result.setCompetenceLabel("GEN666-1");
        result.setResultValue(randInt(0, 200));
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(3);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP3");
        result.setCourseLabel("GEN666");
        result.setCompetenceLabel("GEN666-2");
        result.setResultValue(randInt(0, 200)); //80, 73, 110, 4;
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(4);
        results.add(result);

        result = new CompetenceEvalResult();
        result.setEvalLabel("Sommatif APP3");
        result.setCourseLabel("GEN666");
        result.setCompetenceLabel("GEN666-3");
        result.setResultValue(randInt(0, 200)); //80, 73, 110, 4;
        result.setAvgResultValue(randInt(0, 200));
        result.setMaxResultValue(randInt(0, 200));
        result.setStandardDev(4);
        results.add(result);

        return results;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
