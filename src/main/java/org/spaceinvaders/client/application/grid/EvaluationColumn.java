package org.spaceinvaders.client.application.grid;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class EvaluationColumn extends Column<Evaluation, String> {
    private final String key;

    public EvaluationColumn(String key) {
        super(new TextCell());
        this.key = key;
    }

    @Override
    public String getValue(Evaluation evaluationDataGrid) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        String evaluationLabel = "   ";

        CompetenceEvalResult result = evaluationDataGrid.getCompetenceEvalResult(key);
        if (result != null) {
            double res = 100 * result.getResultValue().doubleValue() / result.getMaxResultValue();
            evaluationLabel = formatter.format(res);
        }
        return evaluationLabel;
    }
}