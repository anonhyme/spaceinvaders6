package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.model.EvaluationGrid;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/22/2015
 *
 * @author antoine
 */
public class DefaultGridColumn extends Column<EvaluationGrid, String> {


    /**
     * Construct a new Column with a given {@link Cell}.
     *
     * @param cell the Cell used by this Column
     */
    public DefaultGridColumn(Cell<String> cell) {
        super(cell);
    }

    @Override
    public String getValue(EvaluationGrid object) {
        return object.getEvaluationTotal();
    }
}
