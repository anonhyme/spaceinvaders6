package org.spaceinvaders.client.application.grid;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.spaceinvaders.client.resources.AppResources;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridView> {
    }

    @UiField
    HTMLPanel menuPanel;

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    AppResources appResources;

    protected ListDataProvider<Evaluation> dataSemesterProvider = new ListDataProvider<Evaluation>();
    private CellTable<Evaluation> cellTable;
    private List<Evaluation> evaluations;
    private HashMap<String, Integer> competenceMap;

    @Inject
    GridView(Binder uiBinder, AppResources appResources) {
        this.appResources = appResources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void initSemesterGradesMapResult(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public void initSemesterTable(SemesterInfo semesterInfo) {
        //TODO to refactor
        cellTable = new CellTable<>();
        initColumn(semesterInfo);
        dataSemesterProvider.setList(evaluations);

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
            cellTable.addColumn(new EvaluationColumn(competences.get(i).getCompetenceLabel(), competenceMap), competences.get(i).getCompetenceLabel());
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
        Column<Evaluation, String> column = new Column<Evaluation, String>(new TextCell()) {
            @Override
            public String getValue(Evaluation data) {
                String value = "";
                //TODO Check why it's not working without the try/catch
                try {
                    value = data.getEvaluationLabel();
                } catch (Exception e) {
                }
                return value;
            }
        };
        cellTable.addColumn(column, "Evaluation");
    }
}
