package org.spaceinvaders.server.dao.mock;

import org.spaceinvaders.server.dao.EvaluationDao;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class EvaluationDaoMock implements EvaluationDao {
    @Override
    public TreeMap<String, Evaluation> getAll(String cip, int semesterID) {
        TreeMap<String, Evaluation> results = new TreeMap<>();

        List<Competence> competences = Arrays.asList(
                new Competence("GEN501-1", 0),
                new Competence("GEN501-2", 1),
                new Competence("GEN402-1", 2),
                new Competence("GEN666-1", 3),
                new Competence("GEN666-1", 4));

        int evalIndex = 0;

        Evaluation eval;
        String evalLabel = "APP1 - Rapport";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(0).getLabel(), getMockResult());
        eval.addResult(competences.get(1).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        evalLabel = "APP1 - Sommatif";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(0).getLabel(), getMockResult());
        eval.addResult(competences.get(1).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        evalLabel = "APP2 - Sommatif";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(2).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        evalLabel = "APP3 - Rapport";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(3).getLabel(), getMockResult());
        eval.addResult(competences.get(4).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        evalLabel = "APP3 - Sommatif";
        eval = new Evaluation(evalLabel, evalIndex);
        eval.addResult(competences.get(3).getLabel(), getMockResult());
        eval.addResult(competences.get(4).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        return results;
    }

    public TreeMap<String, Evaluation> getApEvaluations(String cip, int semesterID, int apID) {
        TreeMap<String, Evaluation> results = new TreeMap<>();

        List<Competence> competences = Arrays.asList(
                new Competence("GEN501-1", 0),
                new Competence("GEN501-2", 1));

        int evalIndex = 0;

        Evaluation eval;
        String evalLabel = "APP1 - Rapport";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(0).getLabel(), getMockResult());
        eval.addResult(competences.get(1).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        evalLabel = "APP1 - Validation";
        eval = new Evaluation(evalLabel, evalIndex++);
        eval.addResult(competences.get(0).getLabel(), getMockResult());
        eval.addResult(competences.get(1).getLabel(), getMockResult());
        results.put(evalLabel, eval);


        evalLabel = "APP1 - Sommatif";
        eval = new Evaluation(evalLabel, evalIndex);
        eval.addResult(competences.get(0).getLabel(), getMockResult());
        eval.addResult(competences.get(1).getLabel(), getMockResult());
        results.put(evalLabel, eval);

        return results;
    }

    private Result getMockResult() {
        double lowestResult = 50;
        double maxTotal = 100;
        double maxSTD = 15;

        double studentTotal = getRandom(maxTotal, lowestResult);
        double avgTotal = getRandom(maxTotal, lowestResult);
        double std = getRandom(0, maxSTD);

        return new Result(studentTotal, avgTotal, maxTotal, std);
    }

    private double getRandom(double min, double max) {
        return Math.round(Math.random() * (max - min) + min);
    }


}
