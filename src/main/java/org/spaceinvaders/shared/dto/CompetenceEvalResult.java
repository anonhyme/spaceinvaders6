package org.spaceinvaders.shared.dto;

import java.io.Serializable;

public class CompetenceEvalResult implements Serializable {
    private String evalLabel;
    private String courseLabel;
    private String competenceLabel;
    private Integer resultValue;
    private Integer avgResultValue;
    private Integer maxResultValue;
    private Integer standardDev;

    public String getEvalLabel() {
        return evalLabel;
    }

    public void setEvalLabel(String evalLabel) {
        this.evalLabel = evalLabel;
    }

    // TODO : should be named AP label instead
    public String getApLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }

    public String getCompetenceLabel() {
        return competenceLabel;
    }

    public void setCompetenceLabel(String competenceLabel) {
        this.competenceLabel = competenceLabel;
    }

    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    public Integer getAvgResultValue() {
        return avgResultValue;
    }

    public void setAvgResultValue(Integer avgResultValue) {
        this.avgResultValue = avgResultValue;
    }

    public Integer getMaxResultValue() {
        return maxResultValue;
    }

    public void setMaxResultValue(Integer maxResultValue) {
        this.maxResultValue = maxResultValue;
    }

    public Integer getStandardDev() {
        return standardDev;
    }

    public void setStandardDev(Integer standardDev) {
        this.standardDev = standardDev;
    }
}