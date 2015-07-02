package org.spaceinvaders.client.application.grid;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.client.widgets.cellGwt.EvaluationResultType;
import org.spaceinvaders.client.widgets.cellGwt.EvaluationResultCell;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

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

    public EvaluationColumn(String key) {
        super(new EvaluationResultCell());
        this.key = key;
    }

    @Override
    public HashMap<EvaluationResultType, String> getValue(Evaluation evaluationDataGrid) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        this.dataMap = new HashMap<EvaluationResultType, String>();

        String resultEvaluation = "   ";

        CompetenceEvalResult result = evaluationDataGrid.getCompetenceEvalResult(key);

        if (result != null) {
            double res = 100 * result.getResultValue().doubleValue() / result.getMaxResultValue();
//            dataMap.put("type", "text");
            dataMap.put(EvaluationResultType.RESULT, formatter.format(res));
            dataMap.put(EvaluationResultType.AVERAGE, result.getAvgResultValue().toString());
            dataMap.put(EvaluationResultType.STD_DEV, result.getStandardDev().toString());
//            resultEvaluation = formatter.format(res);
        }
        return dataMap;
    }

}
