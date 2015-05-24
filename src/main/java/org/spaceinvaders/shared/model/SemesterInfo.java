package org.spaceinvaders.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class SemesterInfo implements IsSerializable {
    List<SemesterCourses> coursesList;

    public SemesterInfo() {
        coursesList = new ArrayList<>();
    }

    public int getCourseCount() {
        return coursesList.size();
    }

    public List<SemesterCourses> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<SemesterCourses> coursesList) {
        this.coursesList = coursesList;
    }

    public String getCourseNameFor(int idx) {
        return coursesList.get(idx).getCourseName();
    }
}
