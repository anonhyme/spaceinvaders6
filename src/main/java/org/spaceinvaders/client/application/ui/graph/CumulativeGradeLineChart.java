package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;

/**
 * Created by Etienne on 2015-05-20.
 */
public class CumulativeGradeLineChart extends AbstractChart {

    public class Data {
        private String evaluationName;
        private double grade;
        private double averageGrade;
        private double maxGrade;

        public Data(String name, double grade, double averageGrade, double maxGrade) {
            this.evaluationName = name;
            this.grade = grade;
            this.averageGrade = averageGrade;
            this.maxGrade = maxGrade;
        }

        public double getGrade() {
            return grade;
        }

        public void setGrade(double grade) {
            this.grade = grade;
        }

        public double getAverageGrade() {
            return averageGrade;
        }

        public void setAverageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
        }

        public double getMaxGrade() {
            return maxGrade;
        }

        public void setMaxGrade(double maxGrade) {
            this.maxGrade = maxGrade;
        }

        public String getEvaluationName() {
            return evaluationName;
        }

        public void setEvaluationNameName(String name) {
            this.evaluationName = name;
        }
    }

    private FramedPanel panel;

    public interface DataPropertyAccess extends PropertyAccess<Data> {
        ValueProvider<Data, Double> grade();

        ValueProvider<Data, Double> averageGrade();

        ValueProvider<Data, Double> maxGrade();

        ValueProvider<Data, String> evaluationName();

        @Editor.Path("evaluationName")
        ModelKeyProvider<Data> nameKey();
    }

    private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
    ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());


    private int currentTotalGrade =0;
    private int currentTotalAverageGrade =0 ;
    private int currentTotalMaxGrade = 0;

    @Override
    public void setData() {
        addData("Rapport", 50, 40, 100);
        addData("Evaluation 1", 60, 50, 100);
        addData("Evaluation 2", 70, 100, 100);
        addData("Examen", 80, 70, 100);
        addData("Examen final", 60, 50, 100);

    }




    private void addData(String name, double grade, double average, double maxGrade) {
        currentTotalGrade += grade;
        currentTotalAverageGrade += average;
        currentTotalMaxGrade += maxGrade;
        store.add(new Data(name, currentTotalGrade, currentTotalAverageGrade, currentTotalMaxGrade));
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            Chart<Data> chart = new Chart<Data>(600, 500);
            chart.setStore(store);
            chart.setAnimated(true);
            chart.setShadowChart(false);

            NumericAxis<Data> verticalAxis = new NumericAxis<Data>();

            verticalAxis.setPosition(Chart.Position.LEFT);
            verticalAxis.addField(dataAccess.maxGrade());
            verticalAxis.addField(dataAccess.averageGrade());
            verticalAxis.addField(dataAccess.grade());
            chart.addAxis(verticalAxis);

            CategoryAxis<Data, String> horizontalAxis = new CategoryAxis<Data, String>();
            horizontalAxis.setPosition(Chart.Position.BOTTOM);
            horizontalAxis.setField(dataAccess.evaluationName());
            chart.addAxis(horizontalAxis);

            LineSeries<Data> series = new LineSeries<Data>();
            series.setYAxisPosition(Chart.Position.LEFT);
            series.setYField(dataAccess.grade());
            series.setStroke(new RGB(0, 255, 0));
            series.setFill(new RGB(0, 255, 0));
            series.setStrokeWidth(3);
            series.setSmooth(false);
            chart.addSeries(series);

            LineSeries<Data> series2 = new LineSeries<Data>();
            series2.setYAxisPosition(Chart.Position.LEFT);
            series2.setYField(dataAccess.averageGrade());
            series2.setStroke(new RGB(255, 0, 0));
            series2.setFill(new RGB(255, 0, 0));
            series2.setStrokeWidth(3);
            series2.setSmooth(false);
            chart.addSeries(series2);

            LineSeries<Data> series3 = new LineSeries<Data>();
            series3.setYAxisPosition(Chart.Position.LEFT);
            series3.setYField(dataAccess.maxGrade());
            series3.setStroke(new RGB(0, 0, 255));
            //series3.setFill(new RGB(0, 0, 255));
            series3.setStrokeWidth(3);
            series3.setSmooth(false);
            chart.addSeries(series3);


            final Legend<Data> legend = new Legend<Data>();
            legend.setItemHighlighting(true);
            legend.setItemHiding(true);
            legend.getBorderConfig().setStrokeWidth(0);
            chart.setLegend(legend);

            VerticalLayoutContainer layout = new VerticalLayoutContainer();
            layout.add(chart, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

            panel = new FramedPanel();
            panel.setLayoutData(new MarginData(10));
            panel.setCollapsible(true);
            panel.setHeadingText("Stacked Bar Chart");
            panel.setPixelSize(620, 500);
            panel.setBodyBorder(true);
            panel.add(layout);

            final Resizable resize = new Resizable(panel, Resizable.Dir.E, Resizable.Dir.SE, Resizable.Dir.S);
            resize.setMinHeight(400);
            resize.setMinWidth(400);
            panel.addExpandHandler(new ExpandEvent.ExpandHandler() {
                @Override
                public void onExpand(ExpandEvent event) {
                    resize.setEnabled(true);
                }
            });
            panel.addCollapseHandler(new CollapseEvent.CollapseHandler() {
                @Override
                public void onCollapse(CollapseEvent event) {
                    resize.setEnabled(false);
                }
            });
            new Draggable(panel, panel.getHeader()).setUseProxy(false);
        }
        return panel;
    }
}
