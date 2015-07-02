package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SemesterInfo implements Serializable {
    private List<Ap> aps;
    private List<Evaluation> evals;
    private String label;
    private List<String> competences;
    private int id;

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public SemesterInfo() {
    }

    public SemesterInfo(List<Evaluation> evals, List<Ap> aps) {
        this.evals = evals;
        this.aps = aps;
    }

    public SemesterInfo(List<Competence> competences, List<Evaluation> evals, String label, int id) {
        this.competences = competences;
        this.evals = evals;
        this.label = label;
        this.id = id;
    }

    public List<String> getCompetences() {
        if (competences == null) {
            competences = new ArrayList<>();

            for (Ap ap : aps) {
                for (String competence : ap.getCompetencesStrings()) {
                    if (!competences.contains(competence)) {
                        competences.add(competence);
                    }
                }
            }
        }

        return competences;
    }

    public List<Evaluation> getEvals() {
        return evals;
    }

    public void setEvals(List<Evaluation> evals) {
        this.evals = evals;
    }

    public String getLabel() {
        return label;
    public List<Ap> getAps() {
        return aps;
    }

    public void setLabel(String label) {
        this.label = label;
    public void setAps(List<Ap> aps) {
        this.aps = aps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}