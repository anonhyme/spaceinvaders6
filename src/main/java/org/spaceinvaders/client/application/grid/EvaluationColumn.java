package org.spaceinvaders.client.application.grid;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;

import org.spaceinvaders.client.widgets.cellGwt.ResultCell;
import org.spaceinvaders.client.widgets.cellGwt.ResultCellStr;
import org.spaceinvaders.client.widgets.cellGwt.templates.TooltipCellWidget;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/21/2015
 *
 * @author antoine
 */
public class EvaluationColumn extends Column<Evaluation, List<String>> {
    @Inject
    TooltipCellWidget tooltipCellWidget;

    private final String key;

    public EvaluationColumn(String key) {
        super(new ResultCellStr());
        this.key = key;
    }

    @Override
    public List<String> getValue(Evaluation evaluationDataGrid) {
        NumberFormat formatter = NumberFormat.getFormat("#.##");
        List<String> cellData = new ArrayList<String>();
        String resultEvaluation = "   ";

        CompetenceEvalResult result = evaluationDataGrid.getCompetenceEvalResult(key);

        if (result != null) {
            double res = 100 * result.getResultValue().doubleValue() / result.getMaxResultValue();
            cellData.add(formatter.format(res));
            cellData.add("0");
            cellData.add("30");
//            resultEvaluation = formatter.format(res);
        }
        return cellData;
    }
}
