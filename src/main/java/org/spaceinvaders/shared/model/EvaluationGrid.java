package org.spaceinvaders.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class EvaluationGrid implements IsSerializable {

    private String evaluationType;
    private String evaluationTotal;
    private List<SemesterCourses> semesterCourse;

    public EvaluationGrid() {
    }

    public EvaluationGrid(String evaluationType, String evaluationTotal, List<SemesterCourses> semesterCourse) {
        this.evaluationType = evaluationType;
        this.evaluationTotal = evaluationTotal;
        this.semesterCourse = semesterCourse;
    }

    public EvaluationGrid(String evaluationType, String evaluationTotal) {
        this.evaluationType = evaluationType;
        this.evaluationTotal = evaluationTotal;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationTotal() {
        return evaluationTotal;
    }

    public void setEvaluationTotal(String evaluationTotal) {
        this.evaluationTotal = evaluationTotal;
    }

    public List<SemesterCourses> getSemesterCourse() {
        return semesterCourse;
    }

    public void setSemesterCourse(List<SemesterCourses> semesterCourse) {
        this.semesterCourse = semesterCourse;
    }
}
