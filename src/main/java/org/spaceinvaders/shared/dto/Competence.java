package org.spaceinvaders.shared.dto;

import java.io.Serializable;

public class Competence implements Serializable {
    private String apLabel;
    private String competenceLabel;

    public Competence() {}

    public String getApLabel() {
        return apLabel;
    }

    public void setApLabel(String apLabel) {
        this.apLabel = apLabel;
    }

    public String getCompetenceLabel() {
        return competenceLabel;
    }

    public void setCompetenceLabel(String competenceLabel) {
        this.competenceLabel = competenceLabel;
    }
}
