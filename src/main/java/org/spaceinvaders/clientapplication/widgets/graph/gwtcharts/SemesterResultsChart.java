package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.BarChart;
import com.googlecode.gwt.charts.client.corechart.BarChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import org.spaceinvaders.client.application.widgets.graph.ApInfo;
import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Etienne on 2015-06-10.
 */
public class SemesterResultsChart extends AbstractGWTChart {
    private BarChart chart;
    List<Evaluation> data;

    public SemesterResultsChart(List<Evaluation> data){
        this.data = data;
    }

    public Widget getChart(){
        if (chart== null){
            chart = new BarChart();
        }
        return chart;
    }

    @Override
    public void loadChart() {

    }

    public void loadChart(SemesterInfo semesterInfo, List<Evaluation> evaluations){
//        Set<String> apNames = new HashSet<>();
//
////        // todo :  create a call to get this from the server (ApInfo should be a DTO)
////        List<ApInfo> apTotals = new ArrayList<>();
////        for (Competence c : data) {
////            if (!apNames.contains(c.getCourseLabel())) {
////                apNames.add(c.getCourseLabel());
////                apTotals.add(new ApInfo(c.getCourseLabel(), c.getResultValue(), c.getAvgResultValue(), c.getMaxResultValue()));
////            } else {
////                for (ApInfo apInfo : apTotals) {
////                    if (apInfo.getName() == c.getCourseLabel()) {
////                        apInfo.addToStudentTotal(c.getResultValue());
////                        apInfo.addToAverageTotal((c.getAvgResultValue()));
////                        apInfo.addToMaxTotal(c.getMaxResultValue());
////                        break;
////                    }
////                }
////            }
////        }
//
//        DataTable dataTable = DataTable.create();
//        dataTable.addColumn(ColumnType.STRING, "Résultat");
//        dataTable.addColumn(ColumnType.NUMBER, "Etudiant");
//        dataTable.addColumn(ColumnType.NUMBER, "Moyenne");
//        dataTable.addColumn(ColumnType.NUMBER, "Max");
//
//        dataTable.addRows(apTotals.size());
//
////        for (int i = 0; i < apNames.size(); i++) {
////            dataTable.setValue(i, 0, apTotals.get(i).getName());
////        }
//
//        List<Ap> aps = semesterInfo.getAps();
//        for (int i = 0; i < apNames.size(); i++) {
//            dataTable.setValue(i, 0, aps.get(i).getName());
//        }
//
//
//        for (int i = 0; i < apNames.size(); i++) {
//            ApInfo e = apTotals.get(i);
//            dataTable.setValue(i, 1, e.getStudentTotal());
//            dataTable.setValue(i, 2, e.getAverageTotal());
//            dataTable.setValue(i, 3, e.getMaxTotal());
//        }
//
//        // Set options
//        BarChartOptions options = BarChartOptions.create();
//        options.setFontName("Tahoma");
//        options.setTitle("Résultats de session");
//        options.setVAxis(VAxis.create("AP"));
//        options.setHAxis(HAxis.create("Résultats"));
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
