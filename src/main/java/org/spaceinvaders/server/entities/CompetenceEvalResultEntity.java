package org.spaceinvaders.server.entities;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetSemesterEvalResults",
                resultClasses = CompetenceEvalResultEntity.class,
                procedureName = "note.get_semester_eval_results",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "student_id", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "session_id", type = Integer.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetApEvalResults",
                resultClasses = CompetenceEvalResultEntity.class,
                procedureName = "note.get_ap_eval_results",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "student_id", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "session_id", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "ap_id", type = Integer.class),
                }
        )
})
@Entity
@Table(name = "t_competence_eval_result", schema = "note", catalog = "S6_PROJET_P02")
public class CompetenceEvalResultEntity {
    private String evalLabel;
    private String courseLabel;
    private String competenceLabel;
    private Integer resultValue;
    private Double avgResultValue;
    private Integer maxResultValue;
    private Integer standardDev;
    private Boolean hasResult;

    @Id
    @Column(name = "eval_label")
    public String getEvalLabel() {
        return evalLabel;
    }

    public void setEvalLabel(String evalLabel) {
        this.evalLabel = evalLabel;
    }

    @Id
    @Column(name = "course_label")
    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }

    @Id
    @Column(name = "competence_label")
    public String getCompetenceLabel() {
        return competenceLabel;
    }

    public void setCompetenceLabel(String competenceLabel) {
        this.competenceLabel = competenceLabel;
    }

    @Basic
    @Column(name = "result_value")
    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    @Basic
    @Column(name = "avg_result_value")
    public Double getAvgResultValue() {
        return avgResultValue;
    }

    public void setAvgResultValue(Double avgResultValue) {
        this.avgResultValue = avgResultValue;
    }

    @Basic
    @Column(name = "max_result_value")
    public Integer getMaxResultValue() {
        return maxResultValue;
    }

    public void setMaxResultValue(Integer maxResultValue) {
        this.maxResultValue = maxResultValue;
    }

    @Basic
    @Column(name = "standard_dev")
    public Integer getStandardDev() {
        return standardDev;
    }

    public void setStandardDev(Integer standardDev) {
        this.standardDev = standardDev;
    }

    @Basic
    @Column(name = "has_result")
    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceEvalResultEntity that = (CompetenceEvalResultEntity) o;

        if (evalLabel != null ? !evalLabel.equals(that.evalLabel) : that.evalLabel != null)
            return false;
        if (courseLabel != null ? !courseLabel.equals(that.courseLabel) : that.courseLabel != null)
            return false;
        if (competenceLabel != null ? !competenceLabel.equals(that.competenceLabel) : that.competenceLabel != null)
            return false;
        if (resultValue != null ? !resultValue.equals(that.resultValue) : that.resultValue != null)
            return false;
        if (avgResultValue != null ? !avgResultValue.equals(that.avgResultValue) : that.avgResultValue != null)
            return false;
        if (maxResultValue != null ? !maxResultValue.equals(that.maxResultValue) : that.maxResultValue != null)
            return false;
        return !(standardDev != null ? !standardDev.equals(that.standardDev) : that.standardDev != null) && !(hasResult != null ? !hasResult.equals(that.hasResult) : that.hasResult != null);

    }

    @Override
    public int hashCode() {
        int result = evalLabel != null ? evalLabel.hashCode() : 0;
        result = 31 * result + (courseLabel != null ? courseLabel.hashCode() : 0);
        result = 31 * result + (competenceLabel != null ? competenceLabel.hashCode() : 0);
        result = 31 * result + (resultValue != null ? resultValue.hashCode() : 0);
        result = 31 * result + (avgResultValue != null ? avgResultValue.hashCode() : 0);
        result = 31 * result + (maxResultValue != null ? maxResultValue.hashCode() : 0);
        result = 31 * result + (standardDev != null ? standardDev.hashCode() : 0);
        result = 31 * result + (hasResult != null ? hasResult.hashCode() : 0);
        return result;
    }
}
