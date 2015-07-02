package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.user.client.ui.Widget;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.*;

public class EvaluationResultsChart extends AbstractGWTChart {
    private ColumnChart chart;
    private String apName;
    private ArrayList<Evaluation> data;
    private Ap ap;

    public EvaluationResultsChart(TreeMap<String, Evaluation> data, Ap ap) {
        this.data = new ArrayList<>(data.values());
        this.ap = ap;
        this.apName = ap.getName();
    }

    public Widget getChart() {
        if (chart == null) {
            chart = new ColumnChart();
        }
        return chart;
    }

    public void loadChart() {
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Résultat");
        dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
        dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
        dataTable.addColumn(ColumnType.NUMBER, "Max");

        dataTable.addRows(data.size());

        for (int i = 0; i < data.size(); i++) {
            dataTable.setValue(i, 0, data.get(i).getLabel());
            Result r = data.get(i).getApResult(ap);
            dataTable.setValue(i, 1, r.getStudentTotal());
            dataTable.setValue(i, 2, r.getAvgTotal());
            dataTable.setValue(i, 3, r.getMaxTotal());
        }

        // Set options
        ColumnChartOptions options = ColumnChartOptions.create();
        options.setFontName("Tahoma");
        options.setTitle("Résultats de l'AP");
        options.setHAxis(HAxis.create("Évaluation"));
        options.setVAxis(VAxis.create("Résultat"));
        if (isCustomSize) {
            options.setWidth(width);
            options.setHeight(height);
        }
        if (colorsSet) {
            options.setColors(colors);
        }

        // Draw the chart
        chart.draw(dataTable, options);
    }
}
