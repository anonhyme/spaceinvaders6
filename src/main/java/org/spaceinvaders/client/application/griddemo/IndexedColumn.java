package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.DoubleSummaryStatistics;
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
        GWT.log("Set Column ");
        GWT.log("getValue " + evaluationDataGrid.getResultValue());
        GWT.log("getValue " + evaluationDataGrid.getMaxResultValue());

        //TODO MAKE IT WORK
//        Integer result = new Integer((evaluationDataGrid.getResultValue() / evaluationDataGrid.getMaxResultValue())*100);

        GWT.log("getValue :: result" + (evaluationDataGrid.getResultValue() / evaluationDataGrid.getMaxResultValue())*100);
        int res = divideAndConquere(evaluationDataGrid.getResultValue(),evaluationDataGrid.getMaxResultValue());
        String evaluationLabel = "   ";
        if (hashMap.get(evaluationDataGrid.getCompetenceLabel()) == index) {
            evaluationLabel = Integer.toString(res);

        }
        return evaluationLabel;
    }

    public static native int divideAndConquere(int num, int den) /*-{
        return (num/den)*100;
    }-*/;

    private Integer divide(int num, int den) {
        return (num/den)*100;
    }

}
