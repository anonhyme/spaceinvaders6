package org.spaceinvaders.server.entities;

import javax.persistence.*;

// TODO : stored procedure

@Entity
@Table(name = "ap_summary_t", schema = "note", catalog = "S6_PROJET_P02")
public class ApSummaryEntity {
    private String apName;
    private Integer resultValue;
    private Integer avgValue;
    private Integer maxValue;

    @Id
    @Column(name = "ap_name")
    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    @Column(name = "result_value")
    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    @Column(name = "avg_value")
    public Integer getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Integer avgValue) {
        this.avgValue = avgValue;
    }

    @Column(name = "max_value")
    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApSummaryEntity that = (ApSummaryEntity) o;

        if (apName != null ? !apName.equals(that.apName) : that.apName != null) return false;
        if (resultValue != null ? !resultValue.equals(that.resultValue) : that.resultValue != null) return false;
        if (avgValue != null ? !avgValue.equals(that.avgValue) : that.avgValue != null) return false;
        if (maxValue != null ? !maxValue.equals(that.maxValue) : that.maxValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apName != null ? apName.hashCode() : 0;
        result = 31 * result + (resultValue != null ? resultValue.hashCode() : 0);
        result = 31 * result + (avgValue != null ? avgValue.hashCode() : 0);
        result = 31 * result + (maxValue != null ? maxValue.hashCode() : 0);
        return result;
    }
}
