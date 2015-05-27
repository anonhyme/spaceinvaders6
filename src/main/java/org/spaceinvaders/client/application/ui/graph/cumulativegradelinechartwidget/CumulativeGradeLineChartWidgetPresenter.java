
package org.spaceinvaders.client.application.ui.graph.cumulativegradelinechartwidget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.ui.graph.EvalInfo;
import org.spaceinvaders.client.application.ui.graph.LineChart;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CumulativeGradeLineChartWidgetPresenter extends PresenterWidget<CumulativeGradeLineChartWidgetPresenter.MyView> {
    public interface MyView extends View {
        void setChart(IsWidget chart);
    }
    LineChart chart;


            @Inject
    CumulativeGradeLineChartWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
        chart = new LineChart();
    }

    public void setData( List<CompetenceEvalResult> results){

        ArrayList<LineChart.Data> chartData = new ArrayList<>();
        Set<String> evalNames = new HashSet<>();
        ArrayList<EvalInfo> evalTotals = new ArrayList<>();
        for (CompetenceEvalResult c : results) {
            if (!evalNames.contains(c.getEvalLabel())) {
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
        double currentStudentTotal =0;
        double currentAvgTotal =0;
        double currentMaxTotal = 0;
        chartData.add(chart.new Data("", 0,0,0));
        for (EvalInfo e : evalTotals) {
            currentStudentTotal+= e.getStudentTotal();
            currentAvgTotal += e.getAverageTotal();
            currentMaxTotal +=e.getMaxTotal();
            chartData.add(chart.new Data(e.getName(), currentStudentTotal, currentAvgTotal, currentMaxTotal ));
           // chartData.add(chart.new Data(e.getName(), e.getStudentTotal(), e.getAverageTotal(), e.getMaxTotal()));
        }
         chart.setData(chartData);
    }

    public void showChart(){
        MyView view = getView();
        view.setChart(chart);
    }

    public void resizeChart(int width, int height){
        this.chart.resize(width,height);
    }


}
