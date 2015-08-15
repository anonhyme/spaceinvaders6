package org.spaceinvaders.client.application.widgets.grid;

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
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.List;

import javax.inject.Inject;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridView> {
    }

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    AppResources appResources;

    protected ListDataProvider<Evaluation> dataSemesterProvider = new ListDataProvider<Evaluation>();

    private CellTable<Evaluation> cellTable;

    @Inject
    GridView(Binder uiBinder, AppResources appResources) {
        this.appResources = appResources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void updateSemesterTable(List<String> competenceLabels, List<Evaluation> evaluations) {
        //TODO refactor
        cellTable = new CellTable<>();
        initColumn(competenceLabels);
        dataSemesterProvider.setList(evaluations);
        containerCellTable.addStyleName(appResources.topNavBar().materialContainer());

        cellTable.setStriped(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "20%");
        cellTable.setHover(false);

        containerCellTable.clear();

        containerCellTable.add(cellTable);
    }

    private void initColumn(List<String> competenceLabels) {
        setEvaluationTypeColumn();
        for (String competenceLabel : competenceLabels) {
            cellTable.addColumn(new EvaluationColumn(competenceLabel, getUiHandlers().getInstance()), competenceLabel);
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setEvaluationTypeColumn() {
        Column<Evaluation, String> column = new Column<Evaluation, String>(new TextCell()) {
            @Override
            public String getValue(Evaluation data) {
                String value = " empty ";
                try {
                    value = data.getLabel();
                } catch (Exception ignored) {
                }
                return value;
            }
        };
        cellTable.addColumn(column, "Évaluation");
    }

}