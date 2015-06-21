package org.spaceinvaders.client.application.widgets.graph;

/**
 * Created by Etienne on 2015-05-26.
 */
public class EvalInfo {

    private String name;
    private double studentTotal;
    private double averageTotal;
    private double maxTotal;

    public EvalInfo(String name, double studentTotal, double averageTotal, double maxTotal){
        this.name = name;
        this.studentTotal = studentTotal;
        this.averageTotal = averageTotal;
        this.maxTotal = maxTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStudentTotal() {
        return studentTotal;
    }

    public void setStudentTotal(double studentTotal) {
        this.studentTotal = studentTotal;
    }

    public void addToStudentTotal(double studentTotal) {
        this.studentTotal += studentTotal;
    }

    public double getAverageTotal() {
        return averageTotal;
    }

    public void setAverageTotal(double averageTotal) {
        this.averageTotal = averageTotal;
    }

    public void addToAverageTotal(double averageTotal) {
        this.averageTotal += averageTotal;
    }

    public double getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(double maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void addToMaxTotal(double maxTotal) {
        this.maxTotal += maxTotal;
    }
}
