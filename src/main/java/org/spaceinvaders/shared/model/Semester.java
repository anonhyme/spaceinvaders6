package org.spaceinvaders.shared.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/22/2015
 *
 * @author antoine
 */
public class Semester {

    int columnCout;

    int rowCount;

    List<String> evaluation;

    List<String> courses;


    public int getColumnCout() {
        return columnCout;
    }

    public void setColumnCout(int columnCout) {
        this.columnCout = columnCout;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<String> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(List<String> evaluation) {
        this.evaluation = evaluation;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
