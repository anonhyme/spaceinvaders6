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

    public void addResult(String competenceLabel, Result result) {
        if (!results.containsKey(competenceLabel)) {
            results.put(competenceLabel, result);
        }
    }

    public Result getApResult(Ap ap) {
        // For each evaluation
        Result r = new Result();
        for (String competenceLabel : results.keySet()) {
            if (ap.getCompetencesStrings().contains(competenceLabel)) {
                Result temp = results.get(competenceLabel);
                r.addToMaxTotal(temp.getMaxTotal());
                r.addToAvgTotal(temp.getAvgTotal());
                r.addToStudentTotal(temp.getStudentTotal());
            }
        }
        return r;
    }

    public Evaluation getApResults(Ap ap) {
        Evaluation apEval = new Evaluation();
        apEval.setLabel(label);
        for (String compLabel : results.keySet()) {
            if (ap.containsCompetence(compLabel)) {
                apEval.addResult(compLabel, results.get(compLabel));
            }
        }
        return apEval;
    }
}
