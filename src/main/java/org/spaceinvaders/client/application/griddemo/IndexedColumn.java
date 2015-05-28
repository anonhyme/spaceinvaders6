package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import com.google.gwt.i18n.client.NumberFormat;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class IndexedColumn extends Column<CompetenceEvalResult, String> {
    private final int index;
    private final HashMap<String, Integer> hashMap;

    public IndexedColumn(int index) {
        super(new TextCell());
        this.index = index;
        hashMap = null;
    }

    public IndexedColumn(int index, HashMap<String, Integer> hashMap) {
        super(new TextCell());
        this.index = index;
        this.hashMap = new HashMap<>(hashMap);
    }

    @Override
    public String getValue(CompetenceEvalResult evaluationDataGrid) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");

        String evaluationLabel = "   ";
        if (hashMap.get(evaluationDataGrid.getCompetenceLabel()) == index) {
            double res = 100 * evaluationDataGrid.getResultValue().doubleValue() / evaluationDataGrid.getMaxResultValue();
            evaluationLabel = formatter.format(res);
        }
        return evaluationLabel;
    }
}
