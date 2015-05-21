package org.spaceinvaders.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "ap_summary_t", schema = "note", catalog = "S6_PROJET_P02")
public class ApSummaryEntity {
    private String apName;
    private Integer apResultPercent;

    @Id
    @Column(name = "ap_name")
    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    @Basic
    @Column(name = "ap_result_percent")
    public Integer getApResultPercent() {
        return apResultPercent;
    }

    public void setApResultPercent(Integer apResultPercent) {
        this.apResultPercent = apResultPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApSummaryEntity that = (ApSummaryEntity) o;

        if (apName != null ? !apName.equals(that.apName) : that.apName != null) return false;
        if (apResultPercent != null ? !apResultPercent.equals(that.apResultPercent) : that.apResultPercent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apName != null ? apName.hashCode() : 0;
        result = 31 * result + (apResultPercent != null ? apResultPercent.hashCode() : 0);
        return result;
    }
}
