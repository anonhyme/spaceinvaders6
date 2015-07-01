package org.spaceinvaders.client.application.widgets.grid;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

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
    public String getValue(Evaluation evaluation) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        String evaluationLabel = "   ";

        Result result = evaluation.getResult(key);
        if (result != null) {
            double res = 100 * result.getStudentTotal() / result.getMaxTotal();
            evaluationLabel = formatter.format(res);
        }
        return evaluationLabel;
    }
}
