package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SemesterInfo implements Serializable {

    // private List<AP> aps; //TODO : add aps to semester info
    private List<Competence> competences;
    private List<Evaluation> evals;
    private String label;
    private int id;

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public SemesterInfo() {
    }

    public SemesterInfo(List<Competence> competences, List<Evaluation> evals) {
        this.competences = competences;
        this.evals = evals;
    }

    public SemesterInfo(List<Competence> competences, List<Evaluation> evals, String label, int id) {
        this.competences = competences;
        this.evals = evals;
        this.label = label;
        this.id = id;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<Evaluation> getEvals() {
        return evals;
    }

    public void setEvals(List<Evaluation> evals) {
        this.evals = evals;
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
}