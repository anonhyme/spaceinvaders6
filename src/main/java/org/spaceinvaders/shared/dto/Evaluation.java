package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.TreeMap;

public class Evaluation implements Serializable {
    private String label;
    private int id;

    private TreeMap<String, Result> results; // key is competenceLabel

    public Evaluation(String label, int id) {
        this.label = label;
        this.id = id;
        results = new TreeMap<>();
    }

    public Evaluation() {
        results = new TreeMap<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TreeMap<String, Result> getResults() {
        return results;
    }

    public void setResults(TreeMap<String, Result> results) {
        this.results = results;
    }

    public Result getResult(String competenceLabel) {
        return results.get(competenceLabel);
    }

    public Result getResult(Ap ap) {
        // For each evaluation
        for (String competenceLabel : results.keySet()) {
            if (ap.getCompetencesStrings().contains(competenceLabel)) {
                return results.get(competenceLabel);
            }
        }
        return null;
    }

    public void addResult(String competenceLabel, Result result) {
        if (!results.containsKey(competenceLabel)) {
            results.put(competenceLabel, result);
        }
    }


}
