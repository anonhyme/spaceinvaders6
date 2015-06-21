package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class SemesterInfo implements Serializable {
    private List<Ap> aps;
    private List<Evaluation> evals;

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

    public List<String> getCompetences() {
        List<String> competences = new ArrayList<>();

        for (Ap ap : aps) {
            for (String competence : ap.getCompetencesStrings()) {
                if (!competences.contains(competence)) {
                    competences.add(competence);
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

    public List<Ap> getAps() {
        return aps;
    }

    public void setAps(List<Ap> aps) {
        this.aps = aps;
    }
}