package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SemesterInfo implements Serializable {
    private List<Competence> competences;
    private List<Evaluation> evals;

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public SemesterInfo() {}

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
}