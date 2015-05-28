package org.spaceinvaders.server.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@NamedStoredProcedureQuery(
    name = "GetSemesterEvals",
    resultClasses = EvaluationEntity.class,
    procedureName = "note.get_semester_evals",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "student_id", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "session_id", type = Integer.class)
    }
)
@Entity
@Table(name = "evaluation_t", schema = "note", catalog = "S6_PROJET_P02")
public class EvaluationEntity {
    private String evaluationLabel;

    @Id
    @Column(name = "evaluation_label")
    public String getEvaluationLabel() {
        return evaluationLabel;
    }

    public void setEvaluationLabel(String evaluationLabel) {
        this.evaluationLabel = evaluationLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationEntity that = (EvaluationEntity) o;

        if (evaluationLabel != null ? !evaluationLabel.equals(that.evaluationLabel) : that.evaluationLabel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return evaluationLabel != null ? evaluationLabel.hashCode() : 0;
    }
}
