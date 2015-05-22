package org.spaceinvaders.client.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "competence_results_t", schema = "note", catalog = "S6_PROJET_P02")
public class CompetenceResultsEntity {
    public String evalLabel;
    private String competenceLabel;
    private Integer resultValue;
    private Integer avgResultValue;
    private Integer maxResultValue;
    private Integer standardDev;
    private Integer cumulatedFrequencyPercent;

    @Id
    @Column(name = "eval_label")
    public String getEvalLabel() {
        return evalLabel;
    }

    public void setEvalLabel(String evalLabel) {
        this.evalLabel = evalLabel;
    }

    @Id
    @Column(name = "competence_label")
    public String getCompetenceLabel() {
        return competenceLabel;
    }

    public void setCompetenceLabel(String competenceLabel) {
        this.competenceLabel = competenceLabel;
    }

    @Column(name = "result_value")
    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    @Column(name = "avg_result_value")
    public Integer getAvgResultValue() {
        return avgResultValue;
    }

    public void setAvgResultValue(Integer avgResultValue) {
        this.avgResultValue = avgResultValue;
    }

    @Column(name = "max_result_value")
    public Integer getMaxResultValue() {
        return maxResultValue;
    }

    public void setMaxResultValue(Integer maxResultValue) {
        this.maxResultValue = maxResultValue;
    }

    @Column(name = "standard_dev")
    public Integer getStandardDev() {
        return standardDev;
    }

    public void setStandardDev(Integer standardDev) {
        this.standardDev = standardDev;
    }

    @Column(name = "cumulated_frequency_percent")
    public Integer getCumulatedFrequencyPercent() {
        return cumulatedFrequencyPercent;
    }

    public void setCumulatedFrequencyPercent(Integer cumulatedFrequencyPercent) {
        this.cumulatedFrequencyPercent = cumulatedFrequencyPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceResultsEntity that = (CompetenceResultsEntity) o;

        if (evalLabel != null ? !evalLabel.equals(that.evalLabel) : that.evalLabel != null) return false;
        if (competenceLabel != null ? !competenceLabel.equals(that.competenceLabel) : that.competenceLabel != null)
            return false;
        if (resultValue != null ? !resultValue.equals(that.resultValue) : that.resultValue != null) return false;
        if (avgResultValue != null ? !avgResultValue.equals(that.avgResultValue) : that.avgResultValue != null)
            return false;
        if (maxResultValue != null ? !maxResultValue.equals(that.maxResultValue) : that.maxResultValue != null)
            return false;
        if (standardDev != null ? !standardDev.equals(that.standardDev) : that.standardDev != null) return false;
        if (cumulatedFrequencyPercent != null ? !cumulatedFrequencyPercent.equals(that.cumulatedFrequencyPercent) : that.cumulatedFrequencyPercent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = evalLabel != null ? evalLabel.hashCode() : 0;
        result = 31 * result + (competenceLabel != null ? competenceLabel.hashCode() : 0);
        result = 31 * result + (resultValue != null ? resultValue.hashCode() : 0);
        result = 31 * result + (avgResultValue != null ? avgResultValue.hashCode() : 0);
        result = 31 * result + (maxResultValue != null ? maxResultValue.hashCode() : 0);
        result = 31 * result + (standardDev != null ? standardDev.hashCode() : 0);
        result = 31 * result + (cumulatedFrequencyPercent != null ? cumulatedFrequencyPercent.hashCode() : 0);
        return result;
    }
}
