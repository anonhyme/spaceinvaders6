package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.user.client.ui.Widget;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.ArrayList;
import java.util.TreeMap;

public class CumulativeLineChart extends AbstractGWTChart {

    private LineChart chart;
    private ArrayList<Evaluation> data;
    private Ap ap;
    private LineChartOptions options;

    public CumulativeLineChart(TreeMap<String, Evaluation> data, Ap ap) {
        this.data = new ArrayList<>(data.values());
        this.ap = ap;
    }

    @Override
    void setDataTable() {
        dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Résultat");
        dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
        dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
        dataTable.addColumn(ColumnType.NUMBER, "Max");

        dataTable.addRows(data.size());

        double currentStudentTotal = 0;
        double currentAvgTotal = 0;
        double currentMaxTotal = 0;

        for (int i = 0; i < data.size(); i++) {

            dataTable.setValue(i, 0, data.get(i).getLabel());
            Result r = data.get(i).getApResult(ap);

            currentStudentTotal += r.getStudentTotal();
            currentAvgTotal += r.getAvgTotal();
            currentMaxTotal += r.getMaxTotal();

            dataTable.setValue(i, 1, currentStudentTotal);
            dataTable.setValue(i, 2, currentAvgTotal);
            dataTable.setValue(i, 3, currentMaxTotal);
        }
    }

    @Override
    void setOptions() {
        options = LineChartOptions.create();
        options.setFontName("Tahoma");
        options.setTitle("Résultats cumulatifs de l'AP");
        options.setHAxis(HAxis.create("Évaluation"));

        VAxis v = VAxis.create("Total");
        v.setMinValue(0);
        options.setVAxis(v);
        options.setPointSize(5);

        if (colorsSet) {
            options.setColors(colors);
        }
    }

    @Override
    public void update() {
        if (isCustomSize) {
            options.setWidth(width);
            options.setHeight(height);
        }

        chart.draw(dataTable, options);
    }

    @Override
    public Widget getChart() {
        if (chart == null) {
            chart = new LineChart();
        }
        return chart;
    }
}
