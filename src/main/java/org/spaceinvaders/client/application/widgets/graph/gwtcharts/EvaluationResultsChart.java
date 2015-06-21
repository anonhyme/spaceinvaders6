package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import org.spaceinvaders.client.application.widgets.graph.EvalInfo;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Etienne on 2015-06-10.
 */
public class EvaluationResultsChart extends AbstractGWTChart {

    private ColumnChart chart;
    String apId;
    List<Evaluation> data;

    public EvaluationResultsChart(List<Evaluation> data, String apName){
        this.data = data;
        this.apId = apName;
    }

    public Widget getChart(){
        if (chart== null){
            chart = new ColumnChart();
        }
        return chart;
    }

    public void loadChart( ){
//        Set<String> evalNames = new HashSet<>();
//        ArrayList<EvalInfo> evalTotals = new ArrayList<>();
//        for (CompetenceResult c : data) {
//            if (!evalNames.contains(c.getEvalLabel()) && c.getCourseLabel().equals(apId)) {
//                evalNames.add(c.getEvalLabel());
//                evalTotals.add(new EvalInfo(c.getEvalLabel(), c.getResultValue(), c.getAvgResultValue(), c.getMaxResultValue()));
//            } else {
//                for (EvalInfo evalTotal : evalTotals) {
//                    if (evalTotal.getName() == c.getEvalLabel()) {
//                        EvalInfo e = evalTotal;
//                        e.addToStudentTotal(c.getResultValue());
//                        e.addToAverageTotal((c.getAvgResultValue()));
//                        e.addToMaxTotal(c.getMaxResultValue());
//                        break;
//                    }
//                }
//            }
//        }
//
//        DataTable dataTable = DataTable.create();
//        dataTable.addColumn(ColumnType.STRING, "Résultat");
//        dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
//        dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
//        dataTable.addColumn(ColumnType.NUMBER, "Max");
//
//        dataTable.addRows(evalTotals.size());
//
//        for (int i = 0; i < evalTotals.size(); i++) {
//            dataTable.setValue(i, 0, evalTotals.get(i).getName());
//        }
//
//        for (int i = 0; i < evalTotals.size(); i++) {
//            EvalInfo e = evalTotals.get(i);
//            dataTable.setValue(i, 1, e.getStudentTotal());
//            dataTable.setValue(i, 2, e.getAverageTotal());
//            dataTable.setValue(i, 3, e.getMaxTotal());
//        }
//
//        // Set options
//        ColumnChartOptions options = ColumnChartOptions.create();
//        options.setFontName("Tahoma");
//        options.setTitle("Résultats de l'AP");
//        options.setHAxis(HAxis.create("Évaluation"));
//        options.setVAxis(VAxis.create("Résultat"));
//        if (isCustomSize) {
//            options.setWidth(width);
//            options.setHeight(height);
//        }
//        if (colorsSet){
//            options.setColors(colors);
//        }
//
//        // Draw the chart
//        chart.draw(dataTable, options);

    }



}
