package org.spaceinvaders.client.application.ui.grid;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.inject.Inject;

import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.spaceinvaders.client.rpc.ExampleServiceAsync;
import org.spaceinvaders.shared.model.ExampleRPC;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.Collections;
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

    @UiField(provided = true)
    DataGrid<TableDataTest> dataGrid = new DataGrid<TableDataTest>(10);

    @Inject
    ExampleServiceAsync exampleService;

    @Inject
    public Grid() {
        initWidget(uiBinder.createAndBindUi(this));
        initTable(dataGrid, dataGridProvider);
        initMockData(dataGridProvider);
    }

    private void initMockData(final ListDataProvider<TableDataTest> dataProvider) {

        for (int i = 0; i < 10; i++) {
            dataProvider.getList().add(new TableDataTest("Test " + i, "Test " + i, "Test " + i));
        }

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

//        AsyncDataProvider<TableDataTest> dataProvider2 = new AsyncDataProvider<TableDataTest>() {
//            @Override
//            protected void onRangeChanged(HasData<TableDataTest> display) {
//                final Range range = display.getVisibleRange();
//
//                // Get the ColumnSortInfo from the table.
//                final ColumnSortList sortList = grid.getColumnSortList();
//
//                // This timer is here to illustrate the asynchronous nature of this data
//                // provider. In practice, you would use an asynchronous RPC call to
//                // request data in the specified range.
//                exampleService.fetchData(new AsyncCallback<List<TableDataTest>>() {
//                    @Override
//                    public void onFailure(Throwable caught) {
//                    }
//
//                    @Override
//                    public void onSuccess(List<TableDataTest> result) {
//                        dataGridProvider.setList(result);
//                        dataGridProvider.flush();
//                    }
//                });
////                new Timer() {
////                    @Override
////                    public void run() {
////                        int start = range.getStart();
////                        int end = start + range.getLength();
////                        // This sorting code is here so the example works. In practice, you
////                        // would sort on the server.
////                        Collections.sort(CONTACTS, new Comparator<Tester.Contact>() {
////                            public int compare(Contact o1, Contact o2) {
////                                if (o1 == o2) {
////                                    return 0;
////                                }
////
////                                // Compare the name columns.
////                                int diff = -1;
////                                if (o1 != null) {
////                                    diff = (o2 != null) ? o1.name.compareTo(o2.name) : 1;
////                                }
////                                return sortList.get(0).isAscending() ? diff : -diff;
////                            }
////                        });
////                        List<Contact> dataInRange = CONTACTS.subList(start, end);
////
////                        // Push the data back into the list.
////                        table.setRowData(start, dataInRange);
////                    }
////                }.schedule(2000);
//
//            }
//        };
//        dataProvider2.addDataDisplay(grid);
    }
}

