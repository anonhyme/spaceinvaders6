package org.spaceinvaders.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class SemesterCourses implements IsSerializable {
    private String courseName;
    private String competence;
    private String data;


    public SemesterCourses() {
        this.courseName = "    ";
        this.competence = "    ";
        this.data = "    ";
    }

    public SemesterCourses(String courseName, String competence) {
        this.courseName = courseName;
        this.competence = competence;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
