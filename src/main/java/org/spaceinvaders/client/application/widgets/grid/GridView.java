package org.spaceinvaders.client.application.widgets.grid;

import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipResources;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.spaceinvaders.client.resources.AppResources;
import org.spaceinvaders.client.resources.CustomTooltipResources;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;
import org.spaceinvaders.shared.exception.ApExeption;

import javax.inject.Inject;

import java.util.List;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridView> {
    }


    @Inject
    private CustomTooltipResources style;

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    @UiField
    Container alertBitchContainer;

    AppResources appResources;


    protected ListDataProvider<Evaluation> dataSemesterProvider = new ListDataProvider<Evaluation>();

    private CellTable<Evaluation> cellTable;

    @Inject
    GridView(Binder uiBinder, AppResources appResources) {
        this.appResources = appResources;
        initWidget(uiBinder.createAndBindUi(this));
    }


    @Override
    public void updateSemesterTable(SemesterInfo semesterInfo, List<Evaluation> evaluations) {
        //TODO refactor
        cellTable = new CellTable<>();
        initColumn(semesterInfo);
        dataSemesterProvider.setList(evaluations);

        cellTable.setStriped(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "22%");
        cellTable.setHover(false);
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);

        containerCellTable.clear();
        containerCellTable.add(cellTable);

    }


    private void initColumn(SemesterInfo semesterInfo) {
        setEvaluationTypeColumn();
        List<Competence> competencesLabels = semesterInfo.getCompetences();
        for (Competence competenceLabel : competencesLabels) {
            cellTable.addColumn(new EvaluationColumn(competenceLabel.getLabel()), competenceLabel.getLabel());
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setEvaluationTypeColumn() {
        Column<Evaluation, String> column = new Column<Evaluation, String>(new TextCell()) {
            @Override
            public String getValue(Evaluation data) {
                String value = " empty ";
                //TODO Check why it's not working without the try/catch
                try {
                    GWT.log("getValue :::::  " + data.getLabel());
                    value = data.getLabel();
                } catch (Exception e) {
                }
                return value;
            }
        };
        cellTable.addColumn(column, "Évaluation");
    }

    private TooltipResources getTooltipResources() {
        return new TooltipResources() {
            @Override
            @Source("css/Tooltip.gss")
            public TooltipStyle css() {
                return getStyle().css();
            }
        };
    }

    public CustomTooltipResources getStyle() {
        return style;
    }

    private TooltipOptions setRowTooltip() {
        TooltipOptions options = new TooltipOptions();
        options.withResources(getTooltipResources());
//        options.withContent(TooltipCellTemplates.INSTANCE.popover());
        options.withSelector("tbody tr");
        options.withContainer("element");
        return options;
    }
}