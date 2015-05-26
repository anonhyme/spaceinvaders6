package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.spaceinvaders.shared.model.*;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class GridDemoView extends ViewWithUiHandlers<GridDemoUiHandlers> implements GridDemoPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridDemoView> {
    }

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    CellTable<EvaluationGrid> cellTable;

    CellTable<AbstractGrid> abstractDataGrid;

    CellTable<GridData> dataCellTable;

    List<SemesterCourses> semesterCourses;

    protected ListDataProvider<EvaluationGrid> dataSemesterProvider = new ListDataProvider<>();

    @Inject
    GridDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateSemesterTable(List<EvaluationGrid> result) {
        GWT.log("updateSemesterTable ");
        dataSemesterProvider.getList().clear();
        dataSemesterProvider.setList(result);
        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();
        cellTable.redraw();
    }

    @Override
    public void initSemesterTable(EvaluationGrid semesterInfo) {
        GWT.log("initSemesterTable ");
        //TODO List AP and courses
//        this.semesterInfo = semesterInfo;

        EvaluationGrid evaluationGrid = new EvaluationGrid();

        cellTable = new CellTable<>();
        abstractDataGrid = new CellTable<AbstractGrid>();

        this.semesterCourses = evaluationGrid.getSemesterCourse();

        initColumn(semesterInfo.getSemesterCourse());

        dataSemesterProvider.setList(defaultEval(semesterInfo.getSemesterCourse().size(), 10));
        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();

        cellTable.redraw();
        cellTable.setStriped(true);
        cellTable.setBordered(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "5%");
        cellTable.setColumnWidth(1, "10%");

        cellTable.setRowData(0, defaultEval(10, semesterInfo.getSemesterCourse().size()));

        containerCellTable.add(cellTable);
    }

    private void initColumn(List<SemesterCourses> semesterCourses) {
        setEvaluationTypeColumn();
        setEvaluationTotalColumn();

        //TODO add semesterInfo in EvaluationGrid Replace SemesterInfo
        for (int i = 0; i < semesterCourses.size(); i++) {
            GWT.log("initColumn " + semesterCourses.get(i).getCourseName());
            cellTable.addColumn(new IndexedColumn(i), semesterCourses.get(i).getCourseName());
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setEvaluationTypeColumn() {
        Column<EvaluationGrid, String> column = new Column<EvaluationGrid, String>(new TextCell()) {
            @Override
            public String getValue(EvaluationGrid data) {
                return data.getEvaluationType();
            }
        };
        cellTable.addColumn(column, "Evaluation");
    }

    private void setEvaluationTotalColumn() {
        Column<EvaluationGrid, String> column = new Column<EvaluationGrid, String>(new TextCell()) {
            @Override
            public String getValue(EvaluationGrid data) {
                return data.getEvaluationTotal();
            }
        };
        cellTable.addColumn(column, "Total");
    }

    private List<EvaluationGrid> defaultEval(int countCol, int countRow) {
        List<EvaluationGrid> evaluations = new ArrayList<EvaluationGrid>();
        List<SemesterCourses> semesterCourses = new ArrayList<SemesterCourses>();
        for (int i = 0; i < countRow; i++) {
            semesterCourses.add(new SemesterCourses());
        }

        for (int i = 0; i < countCol; i++) {
            evaluations.add(new EvaluationGrid("APP " + i, String.valueOf(i), semesterCourses));
        }
        return evaluations;
    }
}
