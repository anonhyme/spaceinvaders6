package org.spaceinvaders.server.entities;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetSemesterCompetences",
                resultClasses = CompetenceEntity.class,
                procedureName = "note.get_semester_competences",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "student_id", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "session_id", type = Integer.class)
                })
})
@Entity
@Table(name = "t_competence", schema = "note", catalog = "S6_PROJET_P02")
public class CompetenceEntity {
    private String apLabel;
    private String competenceLabel;

    @Id
    @Column(name = "ap_label")
    public String getApLabel() {
        return apLabel;
    }

    public void setApLabel(String apLabel) {
        this.apLabel = apLabel;
    }

    @Id
    @Column(name = "competence_label")
    public String getCompetenceLabel() {
        return competenceLabel;
    }

    public void setCompetenceLabel(String competenceLabel) {
        this.competenceLabel = competenceLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenceEntity that = (CompetenceEntity) o;

        if (apLabel != null ? !apLabel.equals(that.apLabel) : that.apLabel != null) {
            return false;
        }
        return !(competenceLabel != null ?
                !competenceLabel.equals(that.competenceLabel) : that.competenceLabel != null);

    }

    @Override
    public int hashCode() {
        int result = apLabel != null ? apLabel.hashCode() : 0;
        result = 31 * result + (competenceLabel != null ? competenceLabel.hashCode() : 0);
        return result;
    }
}
