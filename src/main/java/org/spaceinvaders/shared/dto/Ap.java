package org.spaceinvaders.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ap implements Serializable {
    private String name;
    private int id;
    private List<Competence> competences;
    private List<String> competencesStrings;

    public Ap() {
    }

    public Ap(String name, int id, List<Competence> competences) {
        this.name = name;
        this.id = id;
        this.competences = competences;
        this.competencesStrings = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public void setCompetencesStrings(List<String> competencesStrings) {
        this.competencesStrings = competencesStrings;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public List<String> getCompetencesStrings() {
        if ((competencesStrings.size() == 0) && (competences.size() != 0)) {
            competencesStrings = new ArrayList<>();
            for (Competence comp : competences) {
                competencesStrings.add(comp.getLabel());
            }
        }
        return competencesStrings;
    }

    public boolean containsCompetence(String competenceLabel) {
        for (Competence competence : competences) {
            if (competence.getLabel() == competenceLabel) {
                return true;
            }
        }
        return false;
    }
}