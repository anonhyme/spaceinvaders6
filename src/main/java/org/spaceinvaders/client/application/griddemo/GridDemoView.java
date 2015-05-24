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
import org.spaceinvaders.shared.model.Evaluation;
import org.spaceinvaders.shared.model.EvaluationGrid;
import org.spaceinvaders.shared.model.SemesterCourses;
import org.spaceinvaders.shared.model.SemesterInfo;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GridDemoView extends ViewWithUiHandlers<GridDemoUiHandlers> implements GridDemoPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridDemoView> {
    }
//    @UiField(provided = true)
//    @UiField
//    CellTable<TableDataTest> dataGrid;

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    CellTable<EvaluationGrid> dataGrid;

    List<SemesterCourses> semesterCourses;
    SemesterInfo semesterInfo;

//    EvaluationGrid evaluationGrid = new EvaluationGrid();

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
        dataGrid.redraw();
    }

    @Override
    public void initSemesterTable(SemesterInfo semesterInfo) {

        this.semesterInfo = semesterInfo;
//        this.evaluationGrid = evaluationGrid;
        GWT.log("initSemesterTable ");
        EvaluationGrid evaluationGrid = new EvaluationGrid();


        dataGrid = new CellTable<EvaluationGrid>();
        this.semesterCourses = evaluationGrid.getSemesterCourse();

        initColumn();
        dataSemesterProvider.setList(defaultEval(semesterInfo.getCoursesList().size(), 10));
        int rowCount = semesterInfo.getCoursesList().size();

        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();
        dataGrid.redraw();
        dataGrid.setStriped(true);
        dataGrid.setBordered(true);
        dataGrid.setCondensed(true);
        dataGrid.setColumnWidth(0, "5%");
        dataGrid.setColumnWidth(1, "10%");
        dataGrid.setAccessKey('1');

        dataGrid.setRowData(0, defaultEval(10, semesterInfo.getCoursesList().size()));


        containerCellTable.add(dataGrid);
    }

    private void initColumn() {
        GWT.log("initColumn");

        dataGrid.addColumn(getTypeColumn(), "Type");
        dataGrid.addColumn(getEvalTotalColumn(), "Eval Total");

        //TODO add semesterInfo in EvaluationGrid Replace SemesterInfo
        for (int i = 0; i < semesterInfo.getCoursesList().size(); i++) {
            GWT.log("initColumn " + semesterInfo.getCourseNameFor(i));
            dataGrid.addColumn(new IndexedColumn(i), semesterInfo.getCourseNameFor(i));
        }

        dataSemesterProvider.addDataDisplay(dataGrid);
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

    private List<EvaluationGrid> defaultEval(int countCol, int countRow){
        List<EvaluationGrid> evaluations = new ArrayList<EvaluationGrid>();
        List<SemesterCourses> semesterCourses = new ArrayList<SemesterCourses>();
        for (int i = 0; i < countRow; i++) {
            semesterCourses.add(new SemesterCourses());
        }

        for (int i = 0; i < countCol; i++) {
            evaluations.add(new EvaluationGrid("APP "+i, String.valueOf(i), semesterCourses));
        }
        return evaluations;
    }
}
