package org.spaceinvaders.client.application.widgets.grid;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;
import org.spaceinvaders.client.widgets.cell.EvaluationResultCell;
import org.spaceinvaders.client.widgets.cell.EvaluationResultType;
import org.spaceinvaders.shared.dto.Competence;
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

    public EvaluationColumn(String key) {
        super(new EvaluationResultCell());
        this.key = key;
    }

    @Override
    public HashMap<EvaluationResultType, String> getValue(Evaluation evaluationDataGrid) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        this.dataMap = new HashMap<EvaluationResultType, String>();

        String resultEvaluation = "   ";

        Result result = evaluation.getResult(key);

        if (result != null) {
            double res = 100 * result.getStudentTotal() / result.getMaxTotal();
//            dataMap.put("type", "text");
            dataMap.put(EvaluationResultType.RESULT, formatter.format(res));
            dataMap.put(EvaluationResultType.AVERAGE, result.getAvgResultValue().toString());
            dataMap.put(EvaluationResultType.STD_DEV, result.getStandardDev().toString());
//            resultEvaluation = formatter.format(res);
        }
        return dataMap;
    }

}
