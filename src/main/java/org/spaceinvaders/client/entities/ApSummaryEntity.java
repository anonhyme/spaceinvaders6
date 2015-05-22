package org.spaceinvaders.client.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ap_summary_t", schema = "note", catalog = "S6_PROJET_P02")
public class ApSummaryEntity {
    private String apName;
    private Integer apResult;
    private Integer apAverage;
    private Integer apMax;

    @Id
    @Column(name = "ap_name")
    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    @Column(name = "ap_result")
    public Integer getApResult() {
        return apResult;
    }

    @Column(name = "av_average")
    public Integer getApAverage() {
        return apAverage;
    }

    @Column(name = "av_max")
    public Integer getApMax() {
        return apMax;
    }

    public void setApResult(Integer apResult) {
        this.apResult = apResult;
    }
    public void setApAverage(Integer apResult) {
        this.apAverage = apResult;
    }
    public void setApMax(Integer apMax) {
        this.apMax = apMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApSummaryEntity that = (ApSummaryEntity) o;

        if (apName != null ? !apName.equals(that.apName) : that.apName != null) return false;
       // if (apResultPercent != null ? !apResultPercent.equals(that.apResultPercent) : that.apResultPercent != null)
      //      return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apName != null ? apName.hashCode() : 0;
       // result = 31 * result + (apResultPercent != null ? apResultPercent.hashCode() : 0);
        return result;
    }
}
