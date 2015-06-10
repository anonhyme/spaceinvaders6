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

    HashMap<String, Integer> competenceMap;

    protected ListDataProvider<CompetenceEvalResult> dataSemesterProvider = new ListDataProvider<CompetenceEvalResult>();

    EvaluationDataGrid evaluationDataGrid = new EvaluationDataGrid();

    @Inject
    GridDemoView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult) {
        evaluationDataGrid.setCompetenceEvalResult(semesterGradesResult.getEvaluationResults());
    }

    @Override
    public void initSemesterTable(GetSemesterInfoResult semesterInfoResult) {
        //TODO List AP and courses
        evaluationDataGrid.setSemesterInfo(semesterInfoResult.getSemesterInfo());
        cellTable = new CellTable<>();
        initColumn(evaluationDataGrid.getAllCompetences());
        dataSemesterProvider.setList(evaluationDataGrid.getAllRow());

        dataSemesterProvider.flush();
        dataSemesterProvider.refresh();
        cellTable.setStriped(true);
        cellTable.setBordered(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "15%");
        cellTable.setColumnWidth(1, "8%");
        cellTable.redraw();
        containerCellTable.add(cellTable);
    }

    private void initColumn(List<Competence> competences) {
        setEvaluationTypeColumn();
        setCompetenceHashMap(competences);
        for (int i = 0; i < competences.size(); i++) {
            cellTable.addColumn(new CompetenceColumn(i, competenceMap), competences.get(i).getCompetenceLabel());
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setCompetenceHashMap(List<Competence> competences) {
        this.competenceMap = new HashMap<>();
        for (int i = 0; i < competences.size(); i++) {
            competenceMap.put(competences.get(i).getCompetenceLabel(), i);
        }
    }

    private void setEvaluationTypeColumn() {
        Column<CompetenceEvalResult, String> column = new Column<CompetenceEvalResult, String>(new TextCell()) {
            @Override
            public String getValue(CompetenceEvalResult data) {
                String value = "";
                //TODO Check why it's not working without the try/catch
                try {
                    value = data.getEvalLabel();
                } catch (Exception e) {
                }
                return value;
            }
        };
        cellTable.addColumn(column, "Evaluation");
    }
}
