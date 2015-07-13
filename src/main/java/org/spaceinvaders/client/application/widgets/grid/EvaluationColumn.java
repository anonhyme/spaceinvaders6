package org.spaceinvaders.client.application.widgets.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.client.widgets.cell.EvaluationResultCell;
import org.spaceinvaders.client.widgets.cell.EvaluationResultType;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class EvaluationColumn extends Column<Evaluation, HashMap<EvaluationResultType, String>> {
    private final String key;
    private final String EMPTY = "empty";
    private final String POPOVER = "popover";


    public EvaluationColumn(String key, GridPresenter gridPresenter) {
        super(new EvaluationResultCell(gridPresenter));
        this.key = key;
    }

    @Override
    public HashMap<EvaluationResultType, String> getValue(Evaluation evaluation) {
        HashMap<EvaluationResultType, String> dataMap = new HashMap<>();
        Result result = evaluation.getResult(key);
        String[] apKey = key.split("-");

        if ((result != null) && result.getIsValid()) {
            String resultPercent = formatDoubleToString(100 * result.getStudentTotal() / result.getMaxTotal()) + "%";
            GWT.log("result presenter  :::: " + resultPercent );
            dataMap.put(EvaluationResultType.RESULT, formatDoubleToString(result.getStudentTotal()));
            dataMap.put(EvaluationResultType.MAX_EVALUATION, formatDoubleToString(result.getMaxTotal()));
            dataMap.put(EvaluationResultType.AP, apKey[0]);
            dataMap.put(EvaluationResultType.COMPETENCE, apKey[1]);
            dataMap.put(EvaluationResultType.RESULT_PERCENTAGE, resultPercent);
            dataMap.put(EvaluationResultType.AVERAGE, formatDoubleToString(result.getAvgTotal()));
            dataMap.put(EvaluationResultType.STD_DEV, formatDoubleToString(result.getStandardDev()));
        }
        return dataMap;
    }

    private String formatDoubleToString(Double value) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        return formatter.format(value);
    }
}
