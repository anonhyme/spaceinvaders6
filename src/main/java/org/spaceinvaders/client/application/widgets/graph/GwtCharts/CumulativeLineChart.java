package org.spaceinvaders.client.application.widgets.graph.GwtCharts;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import org.spaceinvaders.client.application.widgets.graph.EvalInfo;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Etienne on 2015-06-10.
 */
public class CumulativeLineChart extends AbstractGWTChart {

    private LineChart chart;
    private String apId;
    List<CompetenceEvalResult> data;

    public CumulativeLineChart(List<CompetenceEvalResult> data, String apName){
        this.data = data;
        apId = apName;
    }

    @Override
     public void loadChart(){
         Set<String> evalNames = new HashSet<>();
         ArrayList<EvalInfo> evalTotals = new ArrayList<>();
         for (CompetenceEvalResult c : data) {
             if (!evalNames.contains(c.getEvalLabel())  && c.getCourseLabel().equals(apId)) {
                 evalNames.add(c.getEvalLabel());
                 evalTotals.add(new EvalInfo(c.getEvalLabel(), c.getResultValue(), c.getAvgResultValue(), c.getMaxResultValue()));
             } else {
                 for (EvalInfo evalTotal : evalTotals) {
                     if (evalTotal.getName() == c.getEvalLabel()) {
                         EvalInfo e = evalTotal;
                         e.addToStudentTotal(c.getResultValue());
                         e.addToAverageTotal((c.getAvgResultValue()));
                         e.addToMaxTotal(c.getMaxResultValue());
                         break;
                     }
                 }
             }
         }

         DataTable dataTable = DataTable.create();
         dataTable.addColumn(ColumnType.STRING, "Résultat");
         dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
         dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
         dataTable.addColumn(ColumnType.NUMBER, "Max");

         dataTable.addRows(evalTotals.size());

         for (int i = 0; i < evalTotals.size(); i++) {
             dataTable.setValue(i, 0, evalTotals.get(i).getName());
         }

         double currentStudentTotal =0;
         double currentAvgTotal =0;
         double currentMaxTotal = 0;
         for (int i = 0; i < evalTotals.size(); i++) {
             EvalInfo e = evalTotals.get(i);
             currentStudentTotal+= e.getStudentTotal();
             currentAvgTotal += e.getAverageTotal();
             currentMaxTotal +=e.getMaxTotal();

             dataTable.setValue(i, 1, currentStudentTotal);
             dataTable.setValue(i, 2, currentAvgTotal);
             dataTable.setValue(i, 3, currentMaxTotal);
         }

         // Set options
         LineChartOptions options = LineChartOptions.create();
         options.setFontName("Tahoma");
         options.setTitle("Résultats cumulatifs de l'AP");
         options.setHAxis(HAxis.create("Évaluation"));
         options.setVAxis(VAxis.create("Total"));
         options.setPointSize(5 );
        if (isCustomSize) {
            options.setWidth(width);
            options.setHeight(height);
        }
        if (colorsSet){
            options.setColors(colors);
        }

         // Draw the chart
         chart.draw(dataTable, options);
     }

    @Override
     public Widget getChart(){
         if (chart== null){
             chart = new LineChart();
         }
         return chart;
     }
}
