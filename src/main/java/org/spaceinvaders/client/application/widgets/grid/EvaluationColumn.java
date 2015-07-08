package org.spaceinvaders.client.application.widgets.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.client.application.semester.SemesterPresenter;
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
    private HashMap<EvaluationResultType, String> dataMap;
    private final String EMPTY = "empty";
    private final String POPOVER = "popover";


    public EvaluationColumn(String key, GridPresenter gridPresenter) {
        super(new EvaluationResultCell(gridPresenter));
        this.key = key;

    }

    @Override
    public HashMap<EvaluationResultType, String> getValue(Evaluation evaluation) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        this.dataMap = new HashMap<EvaluationResultType, String>();

        String resultEvaluation = "   ";

        Result result = evaluation.getResult(key);
        String[] apKey = key.split("-");
        if (result != null) {
            String ap = apKey[0];
            String competence = apKey[1];
            String maxTotal = formatDoubleToString(result.getMaxTotal());
            String studentResult = formatDoubleToString(100 * result.getStudentTotal() / result.getMaxTotal());
            String averageTotal = formatDoubleToString(result.getAvgTotal());
            String standardDev = formatDoubleToString(result.getStandardDev());

            dataMap.put(EvaluationResultType.AP, ap);
            dataMap.put(EvaluationResultType.RESULT, studentResult);
            dataMap.put(EvaluationResultType.AVERAGE, averageTotal);
            dataMap.put(EvaluationResultType.STD_DEV, standardDev);
        }
        return dataMap;
    }

    private String formatDoubleToString(Double value) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        return formatter.format(value);
    }

}
