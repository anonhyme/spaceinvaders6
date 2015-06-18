package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class Evaluation implements Serializable {
    private String evaluationLabel;
    private TreeMap<String, CompetenceEvalResult> competenceEvalResults;

    public Evaluation(String label) {
        this.evaluationLabel = label;
        this.competenceEvalResults = new TreeMap<>();
    }

    public Evaluation() {
        this.evaluationLabel = "";
        this.competenceEvalResults = new TreeMap<>();
    }

    public void setCompetenceEvalResult(CompetenceEvalResult competenceEvalResults) {
        String competenceLabel = competenceEvalResults.getCompetenceLabel();
        if (!this.competenceEvalResults.containsKey(competenceLabel)) {
            this.competenceEvalResults.put(competenceLabel, competenceEvalResults);
        }
    }

    public CompetenceEvalResult getCompetenceEvalResult(String competenceLabel) {
        if (competenceEvalResults.containsKey(competenceLabel)) {
            return competenceEvalResults.get(competenceLabel);
        }

        return null;
    }

    public void setCompetenceEvalResults(TreeMap<String, CompetenceEvalResult> competenceEvalResults) {
        this.competenceEvalResults = competenceEvalResults;
    }

    public TreeMap<String, CompetenceEvalResult> getCompetenceEvalResults() {
        return competenceEvalResults;
    }

    public String getEvaluationLabel() {
        return evaluationLabel;
    }

    public void setEvaluationLabel(String label) {
        this.evaluationLabel = label;
    }

    public static TreeMap<String, Evaluation> getEvaluations(List<CompetenceEvalResult> competenceEvalResults) {
        TreeMap<String, Evaluation> map = new TreeMap<>();
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
