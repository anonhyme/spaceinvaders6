package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.dto.CompetenceEvalResult;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class IndexedColumn extends Column<CompetenceEvalResult, String> {
    private final int index;

    public IndexedColumn(int index) {
        super(new TextCell());
        this.index = index;
    }

    @Override
    public String getValue(CompetenceEvalResult evaluationDataGrid) {
        GWT.log("Set Column ");
        return evaluationDataGrid.getAvgResultValue().toString();
    }
}
