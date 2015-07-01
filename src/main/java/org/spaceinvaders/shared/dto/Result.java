package org.spaceinvaders.shared.dto;

import java.io.Serializable;

public class Result implements Serializable {
    private double studentTotal;
    private double avgTotal;
    private double maxTotal;
    private double standardDev;

    public Result(double studentTotal, double averageTotal, double maxTotal, double standardDev){
        this.studentTotal = studentTotal;
        this.avgTotal = averageTotal;
        this.maxTotal = maxTotal;
        this.standardDev = standardDev;
    }

    public void setStudentTotal(double studentTotal) {
        this.studentTotal = studentTotal;
    }

    public void setAvgTotal(double avgTotal) {
        this.avgTotal = avgTotal;
    }

    public void setMaxTotal(double maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setStandardDev(double standardDev) {
        this.standardDev = standardDev;
    }

    public Result() {
    }

    public double getStudentTotal() {
        return studentTotal;
    }

    public double getAvgTotal() {
        return avgTotal;
    }

    public double getMaxTotal() {
        return maxTotal;
    }

    public double getStandardDev() {
        return standardDev;
    }

    public void addToStudentTotal(double value){
        studentTotal += value;

    }
    public void addToAvgTotal (double value){
        avgTotal += value;

    }

    public void addToMaxTotal (double value){
        maxTotal += value;
    }
}
