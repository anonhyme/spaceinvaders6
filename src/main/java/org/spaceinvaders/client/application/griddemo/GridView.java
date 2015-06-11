package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.spaceinvaders.client.resources.AppResources;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridView> {
    }

    AppResources appResources;

    CellTable<CompetenceEvalResult> cellTable;

    HashMap<String, Integer> competenceMap;

    protected ListDataProvider<CompetenceEvalResult> dataSemesterProvider = new ListDataProvider<CompetenceEvalResult>();

    @UiField
    HTMLPanel menuPanel;

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    List<CompetenceEvalResult> competenceEvalResult;

    @Inject
    GridView(Binder uiBinder, AppResources appResources) {
        this.appResources = appResources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void initSemesterGradesResult(GetSemesterGradesResult semesterGradesResult) {
        this.competenceEvalResult = semesterGradesResult.getEvaluationResults();
    }

    @Override
    public void initSemesterTable(GetSemesterInfoResult semesterInfoResult) {
        //TODO to refactor
        cellTable = new CellTable<>();
        initColumn(semesterInfoResult.getSemesterInfo());
        dataSemesterProvider.setList(competenceEvalResult);

        //TODO check for @UiField(provided=true)
        //!!!! Do not erase !!!!
        //dataSemesterProvider.flush();
        //dataSemesterProvider.refresh();
        //cellTable.redraw();
        cellTable.setStriped(true);
        cellTable.setBordered(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "15%");
        cellTable.setColumnWidth(1, "8%");

        containerCellTable.add(cellTable);
    }

    private void initColumn(SemesterInfo semesterInfo) {
        List<Competence> competences = semesterInfo.getCompetences();
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

    @Override
    public void addToSlot(Object slot, IsWidget content) {
        super.addToSlot(slot, content);
        if (slot == GridPresenter.SLOT_WIDGET_ELEMENT) {
            menuPanel.add(content);
        }
    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
        super.removeFromSlot(slot, content);
        if (slot == GridPresenter.SLOT_WIDGET_ELEMENT) {
            menuPanel.remove(content);
        }
    }
}
