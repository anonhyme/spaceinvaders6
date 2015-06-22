package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SemesterInfo implements Serializable {

    //    private List<AP> aps; // todo : add aps to semester info
    private List<Competence> competences;
    private List<Evaluation> evals;
    private String label;
    private int semesterId;

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public SemesterInfo() {
    }

    public SemesterInfo(List<Competence> competences, List<Evaluation> evals, String label, int semesterId) {
        this.competences = competences;
        this.evals = evals;
        this.label = label;
        this.semesterId = semesterId;
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

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }
}