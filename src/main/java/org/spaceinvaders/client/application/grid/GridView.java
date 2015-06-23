package org.spaceinvaders.client.application.grid;

import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.spaceinvaders.client.resources.AppResources;
import org.spaceinvaders.client.resources.CustomTooltipResources;
import org.spaceinvaders.client.widgets.cell.CellPresenter;
import org.spaceinvaders.client.widgets.cell.WidgetsFactory;
import org.spaceinvaders.client.widgets.cellGwt.ResultCell;
import org.spaceinvaders.client.widgets.cellGwt.TooltipCellTemplates;
import org.spaceinvaders.client.widgets.cellGwt.TooltipCellWidget;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.inject.Inject;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

public class GridView extends ViewWithUiHandlers<GridUiHandlers> implements GridPresenter.MyView {
    interface Binder extends UiBinder<Widget, GridView> {
    }

    @Inject
    private CustomTooltipResources style;

    private CellPresenter cellPresenter;
    private WidgetsFactory widgetsFactory;

    @UiField
    HTMLPanel panel;

    @UiField
    Container containerCellTable;

    @UiField
    Container alertBitchContainer;

    AppResources appResources;

    TooltipCellWidget tooltipCellWidget;

    protected ListDataProvider<Evaluation> dataSemesterProvider = new ListDataProvider<Evaluation>();

    private CellTable<Evaluation> cellTable;


    @Inject
    GridView(Binder uiBinder, AppResources appResources, TooltipCellWidget tooltipCellWidget) {
        this.appResources = appResources;
        this.tooltipCellWidget = tooltipCellWidget;
//        this.tooltipCellWidget.setData("hello");

        initWidget(uiBinder.createAndBindUi(this));

    }

    @Override
    public void addCellPresenter(WidgetsFactory widgetsFactory) {
        this.widgetsFactory = widgetsFactory;
    }

    @Override
    public void updateSemesterTable(SemesterInfo semesterInfo, List<Evaluation> evaluations) {
        //TODO refactor
        cellTable = new CellTable<>();
        Alert alert = new Alert("fdsa");

        $(cellTable).as(Tooltip.Tooltip).tooltip(setRowTooltip());

        GWT.log("updateSemesterTable " + $(alertBitchContainer));

        alertBitchContainer.clear();

        alertBitchContainer.add(alert);

        initColumn(semesterInfo);
        dataSemesterProvider.setList(evaluations);

        cellTable.setStriped(true);
        cellTable.setCondensed(true);
        cellTable.setColumnWidth(0, "22%");

        containerCellTable.clear();
        containerCellTable.add(cellTable);
    }


    private void initColumn(SemesterInfo semesterInfo) {
        setEvaluationTypeColumn();
        for (Competence competence : semesterInfo.getCompetences()) {
            cellTable.addColumn(new EvaluationColumn(competence.getCompetenceLabel()), competence.getCompetenceLabel());
        }
        dataSemesterProvider.addDataDisplay(cellTable);
    }

    private void setEvaluationTypeColumn() {
        Column<Evaluation, String> column = new Column<Evaluation, String>(new ResultCell(widgetsFactory)) {
            @Override
            public String getValue(Evaluation data) {
                String value = " ";
                GWT.log("Column getValue :::: ");
                //TODO Check why it's not working without the try/catch
                try {
                    value = data.getEvaluationLabel();

                } catch (Exception e) {
                }
//                tooltipCellWidget.setData(value);
                return value;
            }
        };

        cellTable.addColumn(column, "Évaluation");

    }

    public CellPresenter getCellPresenter() {
        return cellPresenter;
    }

    //    public class _ResultCell extends AbstractCell<String> {
//        @com.google.inject.Inject
//        private WidgetsFactory widgetsFactory;
//
//        @Override
//        public void render(Context context, String value, SafeHtmlBuilder sb) {
//            // Value can be null, so do a null check..
//            if (value == null) {
//                return;
//            }
//            CellPresenter cellPresenter = widgetsFactory.createCell("hello from cell ");
//
//            SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(cellPresenter.asWidget().toString());
//
//            GWT.log("render safeHtml " + safeHtml);
//            sb.append(safeHtml);
//        }
//
//    }

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

    private TooltipOptions setRowTooltip(){
        TooltipOptions options = new TooltipOptions();
        options.withResources(getTooltipResources());
        options.withContent(TooltipCellTemplates.INSTANCE.popover());
        options.withSelector("tbody tr");
        options.withContainer("element");
        return options;
    }

}
