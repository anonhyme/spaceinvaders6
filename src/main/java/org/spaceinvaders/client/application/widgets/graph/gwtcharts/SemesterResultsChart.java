package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.user.client.ui.Widget;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.BarChart;
import com.googlecode.gwt.charts.client.corechart.BarChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import org.spaceinvaders.shared.dto.*;

import java.util.List;

/**
 * Created by Etienne on 2015-06-10.
 */
public class SemesterResultsChart extends AbstractGWTChart {
    private BarChart chart;
    private List<Evaluation> evaluations;
    private SemesterInfo semesterInfo;
    List<Ap> aps;
    private BarChartOptions options;

    public SemesterResultsChart(SemesterInfo semesterInfo, List<Evaluation> evaluations) {
        this.semesterInfo = semesterInfo;
        this.evaluations = evaluations;
    }

    public Widget getChart() {
        if (chart == null) {
            chart = new BarChart();
        }
        return chart;
    }

    @Override
    public void loadChart() {
        aps = semesterInfo.getAps();
        super.loadChart();
    }

    @Override
    void setDataTable() {
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Résultat");
        dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
        dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
        dataTable.addColumn(ColumnType.NUMBER, "Max");

        dataTable.addRows(aps.size());

        for (int i = 0; i < aps.size(); i++) {
            Ap ap = aps.get(i);
            Result apTotal = new Result();
            for (int j = 0; j < evaluations.size(); j++) {
                Result r = evaluations.get(j).getApResult(ap);
                apTotal.addToAvgTotal(r.getAvgTotal());
                apTotal.addToMaxTotal(r.getMaxTotal());
                apTotal.addToStudentTotal(r.getStudentTotal());
            }
            dataTable.setValue(i, 0, aps.get(i).getName());
            dataTable.setValue(i, 1, apTotal.getStudentTotal());
            dataTable.setValue(i, 2, apTotal.getAvgTotal());
            dataTable.setValue(i, 3, apTotal.getMaxTotal());
        }
    }

    @Override
    void setOptions() {
        options = BarChartOptions.create();
        options.setFontName("Tahoma");
        options.setTitle("Résultats de session");
        options.setVAxis(VAxis.create("AP"));
        options.setHAxis(HAxis.create("Résultats"));
        if (colorsSet) {
            options.setColors(colors);
        }
    }

    @Override
    void update() {
        if (isCustomSize) {
            options.setWidth(width);
            options.setHeight(height);
        }
        chart.draw(dataTable, options);
    }
}
