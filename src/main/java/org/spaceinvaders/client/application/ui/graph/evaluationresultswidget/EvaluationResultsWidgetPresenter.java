
package org.spaceinvaders.client.application.ui.graph.evaluationresultswidget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.sencha.gxt.chart.client.chart.Chart;
import org.spaceinvaders.client.application.ui.graph.EvalInfo;
import org.spaceinvaders.client.application.ui.graph.GroupBarChart;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluationResultsWidgetPresenter extends PresenterWidget<EvaluationResultsWidgetPresenter.MyView> {
    public interface MyView extends View {
        void setChart(IsWidget chart);
    }

    GroupBarChart chart;

    @Inject
    EvaluationResultsWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
        chart = new GroupBarChart();
        chart.setTitle("Notes", "Évaluation");
       // chart.setAxes(Chart.Position.LEFT, Chart.Position.BOTTOM);
    }

    public void setData( List<CompetenceEvalResult> results){
        ArrayList<GroupBarChart.Data> chartData = new ArrayList<>();
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
        for (EvalInfo e : evalTotals) {
            chartData.add(chart.new Data(e.getName(), e.getStudentTotal(), e.getAverageTotal(), e.getMaxTotal()));
        }
        chart.setData(chartData);
    }

    public void showChart(){
        MyView view = getView();
        view.setChart(chart);
    }

    public void resizeChart(int width, int height){
        this.chart.resize(width, height);
    }

}
