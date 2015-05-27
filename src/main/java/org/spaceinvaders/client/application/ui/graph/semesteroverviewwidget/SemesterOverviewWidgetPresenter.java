
package org.spaceinvaders.client.application.ui.graph.semesteroverviewwidget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.ui.graph.ApInfo;
import org.spaceinvaders.client.application.ui.graph.GroupBarChart;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemesterOverviewWidgetPresenter extends PresenterWidget<SemesterOverviewWidgetPresenter.MyView> {
    public interface MyView extends View {

        void setChart(IsWidget chart);
    }

    GroupBarChart chart;

    @Inject
    SemesterOverviewWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
        chart = new GroupBarChart();
        chart.setTitle("Notes", "AP");
        // chart.setAxes(Chart.Position.LEFT, Chart.Position.BOTTOM);
    }

    public void setData( List<CompetenceEvalResult> results){
        ArrayList<GroupBarChart.Data> chartData = new ArrayList<>();
        Set<String> apNames = new HashSet<>();
        ArrayList<ApInfo> apTotals = new ArrayList<>();
        for (CompetenceEvalResult c : results) {
            if (!apNames.contains(c.getCourseLabel())) {
                apNames.add(c.getCourseLabel());
                apTotals.add(new ApInfo(c.getCourseLabel(), c.getResultValue(), c.getAvgResultValue(), c.getMaxResultValue()));
            } else {
                for (ApInfo apInfo : apTotals) {
                    if (apInfo.getName() == c.getCourseLabel()) {
                        apInfo.addToStudentTotal(c.getResultValue());
                        apInfo.addToAverageTotal((c.getAvgResultValue()));
                        apInfo.addToMaxTotal(c.getMaxResultValue());
                        break;
                    }
                }
            }
        }
        for (ApInfo e : apTotals) {
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
