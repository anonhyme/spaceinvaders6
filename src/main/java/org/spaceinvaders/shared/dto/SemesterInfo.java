package org.spaceinvaders.shared.dto;

import org.spaceinvaders.server.cas.UserSessionImpl;
import org.spaceinvaders.shared.exception.ApExeption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SemesterInfo implements Serializable {
    private int id;
    private String label;
    private List<Ap> aps;
    private List<Evaluation> evals;
    private List<Competence> competences;

    /**
     * For serialization only
     */
    @SuppressWarnings("unused")
    public SemesterInfo() {
    }

    public SemesterInfo(int id, String label, List<Ap> aps, List<Evaluation> evals) {
        this.evals = evals;
        this.aps = aps;
        this.id = id;
        this.label = "Session " + id;
    }

    public List<Competence> getCompetences() {
        //TODO handle if ap is null
        if (competences == null) {
            competences = new ArrayList<>();
            for (Ap ap : aps) {
                for (Competence competence : ap.getCompetences()) {
                    if (!competences.contains(competence)) {
                        competences.add(competence);
                    }
                }
            }
        }
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

    public List<Ap> getAps() {
        return aps;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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