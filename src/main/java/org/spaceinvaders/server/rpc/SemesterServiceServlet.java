package org.spaceinvaders.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.spaceinvaders.client.rpc.SemesterService;
import org.spaceinvaders.shared.model.EvaluationGrid;
import org.spaceinvaders.shared.model.SemesterCourses;
import org.spaceinvaders.shared.model.SemesterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
public class SemesterServiceServlet extends RemoteServiceServlet implements SemesterService {

    @Override
    public EvaluationGrid fetchSemesterInfo() {
        SemesterInfo semesterInfo = new SemesterInfo();
        EvaluationGrid evaluationGrid = new EvaluationGrid();
        List<SemesterCourses> semesterCourse = new ArrayList<>();
        semesterCourse.add(new SemesterCourses("GEN600", "1"));
        semesterCourse.add(new SemesterCourses("GEN650", "1"));
        semesterCourse.add(new SemesterCourses("GIF600", "1"));
        semesterCourse.add(new SemesterCourses("GIF611", "1"));
        semesterCourse.add(new SemesterCourses("GIF620", "1"));
        semesterInfo.setCoursesList(semesterCourse);

        evaluationGrid.setSemesterCourse(semesterCourse);
        return evaluationGrid;
    }

}
