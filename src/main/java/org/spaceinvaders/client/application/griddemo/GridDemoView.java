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
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class GridDemoView extends ViewWithUiHandlers<GridDemoUiHandlers> implements GridDemoPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridDemoView> {
    }

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    @UiField
    Container containerModal;

    CellTable<CompetenceEvalResult> cellTable;

//    HashMap<String, >

    protected ListDataProvider<CompetenceEvalResult> dataSemesterProvider = new ListDataProvider<CompetenceEvalResult>();

    EvaluationDataGrid evaluationDataGrid = new EvaluationDataGrid();

    @Inject
    GridDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

//    @Override
//    public void updateSemesterTable(List<EvaluationDataGrid> result) {
//        GWT.log("updateSemesterTable ");
//        dataSemesterProvider.getList().clear();
////        dataSemesterProvider.setList(result);
//        dataSemesterProvider.flush();
//        dataSemesterProvider.refresh();
//        cellTable.redraw();
//    }

    @Override
    public void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult) {
        GWT.log("initSemesterGradesResult ");
        evaluationDataGrid.setCompetenceEvalResult(semesterGradesResult.getEvaluationResults());
        GWT.log("initSemesterGradesResult " + evaluationDataGrid.getCompetenceEvalResult().get(0).getCompetenceLabel());
    }

    @Override
    public void initSemesterTable(GetSemesterInfoResult semesterInfoResult) {
        GWT.log("initSemesterTable ");
        //TODO List AP and courses
        evaluationDataGrid.setSemesterInfo(semesterInfoResult.getSemesterInfo());

        cellTable = new CellTable<>();

        initColumn(evaluationDataGrid.getAllCompetences());
        dataSemesterProvider.setList(evaluationDataGrid.getAllRow());

        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();

        cellTableSetup();
        cellTable.redraw();
        containerCellTable.add(cellTable);
    }

    /**
     * Set table presentation
     */
    private void cellTableSetup() {
        cellTable.setStriped(true);
        cellTable.setBordered(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "15%");
        cellTable.setColumnWidth(1, "8%");

    }

    private void initColumn(List<Competence> competences) {
        GWT.log("initColumn ");
        setEvaluationTypeColumn();
        HashMap<String, String> competenceMap;


//        setEvaluationTotalColumn();

        for (int i = 0; i < competences.size(); i++) {
            GWT.log("initColumn " + competences.get(i).getCompetenceLabel());
            GWT.log("initColumn " + competences.get(i).getApLabel());
            cellTable.addColumn(new IndexedColumn(i), competences.get(i).getCompetenceLabel());
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setEvaluationTypeColumn() {
        Column<CompetenceEvalResult, String> column = new Column<CompetenceEvalResult, String>(new TextCell()) {
            @Override
            public String getValue(CompetenceEvalResult data) {
                String value = "   ";
                GWT.log("getValue .......................");
                try {
                    GWT.log("getValue trying the shit ");
                    value = data.getEvalLabel();
                } catch (Exception e) {
                    GWT.log("getValue exception catch ");
                }
                return value;
            }
        };
        cellTable.addColumn(column, "Evaluation");
    }

    private void setEvaluationTotalColumn() {
        Column<CompetenceEvalResult, String> column = new Column<CompetenceEvalResult, String>(new TextCell()) {
            @Override
            public String getValue(CompetenceEvalResult data) {
                return data.getMaxResultValue().toString();
            }
        };
        cellTable.addColumn(column, "Total");
    }
}
