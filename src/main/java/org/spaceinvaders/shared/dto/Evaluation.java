package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.*;

public class Evaluation implements Serializable {
    private String evaluationLabel;
    private TreeMap<String, CompetenceEvalResult> competenceEvalResultMap;

    public Evaluation(String label) {
        this.evaluationLabel = label;
        this.competenceEvalResultMap = new TreeMap<>();
    }

    public Evaluation() {
        this.evaluationLabel = "";
        this.competenceEvalResultMap = new TreeMap<>();
    }

    public void setCompetenceEvalResult(CompetenceEvalResult competenceEvalResult) {
        String competenceLabel = competenceEvalResult.getCompetenceLabel();
        if (!competenceEvalResultMap.containsKey(competenceLabel)) {
            competenceEvalResultMap.put(competenceLabel, competenceEvalResult);
        }
    }

    public CompetenceEvalResult getCompetenceEvalResult(String competenceLabel) {
        if (competenceEvalResultMap.containsKey(competenceLabel)) {
            return competenceEvalResultMap.get(competenceLabel);
        }

        return null;
    }

    public TreeMap<String, CompetenceEvalResult> getCompetenceEvalResults()
    {
        return competenceEvalResultMap;
    }

    public String getEvaluationLabel() {
        return evaluationLabel;
    }

    public void setEvaluationLabel(String label) {
        this.evaluationLabel = label;
    }

    public static SortedMap<String, Evaluation> getEvaluations(List<CompetenceEvalResult> competenceEvalResults)
    {
        SortedMap<String, Evaluation> map = new TreeMap<>();
        for (CompetenceEvalResult competenceResult : competenceEvalResults) {
            String evalLabel = competenceResult.getEvalLabel();
            if (!map.containsKey(evalLabel)) {
                map.put(evalLabel, new Evaluation(evalLabel));
            }
            map.get(evalLabel).setCompetenceEvalResult(competenceResult);
        }
        return map;
    }
}
