package org.spaceinvaders.shared.dto;

import java.io.Serializable;

public final class Competence implements Serializable {
    private String label;
    private int id;

    public Competence() {
    }

    public Competence(String label, int id) {
        this.label = label;
        this.id = id;
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

    @Override
    public int hashCode() {
        return 31 * id; // 31 is prime
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        Competence other = (Competence) obj;
        return id == other.id;
    }
}
