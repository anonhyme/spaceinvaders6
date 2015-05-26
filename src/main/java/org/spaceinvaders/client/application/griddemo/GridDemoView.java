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

    List<SemesterCourses> semesterCourses;
    SemesterInfo semesterInfo;


    protected ListDataProvider<EvaluationGrid> dataSemesterProvider = new ListDataProvider<EvaluationGrid>();
    protected ListDataProvider<TableDataTest> dataProvider = new ListDataProvider<TableDataTest>();

    @Inject
    GridDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void onAttach() {
        super.onAttach();
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
    public void initSemesterTable(SemesterInfo semesterInfo) {

        this.semesterInfo = semesterInfo;
        GWT.log("initSemesterTable ");
        EvaluationGrid evaluationGrid = new EvaluationGrid();


        cellTable = new CellTable<EvaluationGrid>();
        abstractDataGrid = new CellTable<AbstractGrid>();
        this.semesterCourses = evaluationGrid.getSemesterCourse();

        initColumn();
        dataSemesterProvider.setList(defaultEval(semesterInfo.getCoursesList().size(), 10));

        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();
        cellTable.redraw();
        cellTable.setStriped(true);
        cellTable.setBordered(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "5%");
        cellTable.setColumnWidth(1, "10%");

        cellTable.setRowData(0, defaultEval(10, semesterInfo.getCoursesList().size()));


        containerCellTable.add(cellTable);
    }

    private void initColumn() {
        GWT.log("initColumn");

        cellTable.addColumn(getTypeColumn(), "Type");
        cellTable.addColumn(getEvalTotalColumn(), "Eval Total");

        //TODO add semesterInfo in EvaluationGrid Replace SemesterInfo
        for (int i = 0; i < semesterInfo.getCoursesList().size(); i++) {
            GWT.log("initColumn " + semesterInfo.getCourseNameFor(i));
            cellTable.addColumn(new IndexedColumn(i), semesterInfo.getCourseNameFor(i));
        }

        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private Column<EvaluationGrid, String> getTypeColumn() {
        return new Column<EvaluationGrid, String>(new TextCell()) {
            @Override
            public String getValue(EvaluationGrid data) {
                return data.getEvaluationType();
            }
        };
    }

    private Column<EvaluationGrid, String> getEvalTotalColumn() {
        Column<EvaluationGrid, String> column = new Column<EvaluationGrid, String>(new TextCell()) {
            @Override
            public String getValue(EvaluationGrid data) {
                return data.getEvaluationTotal();
            }
        };
        return column;
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
