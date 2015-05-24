package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.model.EvaluationGrid;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class IndexedColumn extends Column<EvaluationGrid, String> {
    private final int index;

    public IndexedColumn(int index) {
        super(new TextCell());
        this.index = index;
    }

    @Override
    public String getValue(EvaluationGrid object) {
        return object.getSemesterCourse().get(this.index).getCourseName();
    }
}
