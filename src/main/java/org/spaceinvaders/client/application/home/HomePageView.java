package org.spaceinvaders.client.application.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import org.gwtbootstrap3.client.ui.Container;
import org.spaceinvaders.client.application.ui.grid.Grid;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomePageView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePagePresenter.MyView {

    @UiField
    Grid grid;

    @UiField
    Container container;


    List<TableDataTest> tableData = new ArrayList<>();

    public interface Binder extends UiBinder<Widget, HomePageView> {

    }

    @Inject
    HomePageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        GWT.log("result OnAtatch ......... " + tableData);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
//        getUiHandlers().getData();
//        tableData = getUiHandlers().getDataList();
        GWT.log("result OnAtatch ......... " + tableData);
        grid.setGrid(tableData);
//        grid = new Grid(tableData);
//        container.add(grid);
    }

    @Override
    public void updateTable(List<TableDataTest> result) {
        tableData = result;
//        grid.updateData(result);
//        GWT.log("result  UpdateTable" + result);
//        tableData.addAll(result);
//        tableData = result;
    }
}
