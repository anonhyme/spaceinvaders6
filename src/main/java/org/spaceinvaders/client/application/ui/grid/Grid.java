package org.spaceinvaders.client.application.ui.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.spaceinvaders.client.rpc.SemesterServiceAsync;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/18/2015
 *
 * @author antoine
 */
public class Grid extends Composite {
    interface DataGridUiBinder extends UiBinder<Widget, Grid> {
    }

    private static DataGridUiBinder uiBinder = GWT.create(DataGridUiBinder.class);
    private ListDataProvider<TableDataTest> dataGridProvider = new ListDataProvider<TableDataTest>();
    private List<TableDataTest> resultList;

    @UiField(provided = true)
    DataGrid<TableDataTest> dataGrid = new DataGrid<TableDataTest>(10);

    @Inject
    SemesterServiceAsync exampleService;

    @Inject
    public Grid() {
        initWidget(uiBinder.createAndBindUi(this));
//        initTable(dataGrid, dataGridProvider);
//        initMockData(dataGridProvider);
    }

    public void setGrid(List<TableDataTest> resultList) {
        GWT.log("result On Grid Side " + resultList.get(0).getField1());

//        initWidget(uiBinder.createAndBindUi(this));
        initTable(dataGrid, dataGridProvider);
        initMockDataList(dataGridProvider, resultList);
    }

    public void updateData(List<TableDataTest> resultList) {
        updateTable(dataGridProvider, resultList);
    }

    public void clearData() {
        dataGrid = new DataGrid<TableDataTest>(10);
        dataGridProvider.getList().clear();

    }

    private void updateTable(final ListDataProvider<TableDataTest> dataProvider, List<TableDataTest> resultList) {
        dataProvider.getList().clear();
        GWT.log("dataProviderClear ......... [" + dataProvider.getList() + "]");

        dataProvider.setList(resultList);
        dataProvider.flush();

    }

    private void initMockDataList(final ListDataProvider<TableDataTest> dataProvider, List<TableDataTest> resultList) {
        dataProvider.setList(resultList);
        dataProvider.flush();
    }

    private void initTable(final AbstractCellTable<TableDataTest> grid, final ListDataProvider<TableDataTest> dataProvider) {
        final TextColumn<TableDataTest> col1 = new TextColumn<TableDataTest>() {

            @Override
            public String getValue(final TableDataTest object) {
                return String.valueOf(object.getField1());
            }
        };

        final TextColumn<TableDataTest> col2 = new TextColumn<TableDataTest>() {
            @Override
            public String getValue(final TableDataTest object) {
                return String.valueOf(object.getField1());
            }
        };

        final TextColumn<TableDataTest> col3 = new TextColumn<TableDataTest>() {

            @Override
            public String getValue(final TableDataTest object) {
                return String.valueOf(object.getField1());
            }
        };

        grid.addColumn(col1, "Field 1");
        grid.addColumn(col2, "Field 2");
        grid.addColumn(col3, "Field 3");

        dataProvider.addDataDisplay(grid);
    }
}

