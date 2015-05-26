package org.spaceinvaders.shared.dto;

import java.io.Serializable;

public class Evaluation implements Serializable {
    private String evaluationLabel;

    public Evaluation(String label) {
        this.evaluationLabel = label;
    }

    public Evaluation() {}

    public String getEvaluationLabel() {
        return evaluationLabel;
    }

    public void setEvaluationLabel(String label) {
        this.evaluationLabel = label;
    }
}
