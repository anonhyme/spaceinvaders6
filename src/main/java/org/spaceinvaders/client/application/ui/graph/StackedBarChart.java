package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
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

//Data Structure should be:
// string key gradeType (
// data evaluation1, evaluation2, etc
public class StackedBarChart extends AbstractChart {
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

    @Override
    public void setData() {
        store.add(new Data("Rapport", Math.random() * 100, 60, 100));
        store.add(new Data("Evaluation 1", 60, 80, 100));
        store.add(new Data("Evaluation 2", 70, 90, 100));
        store.add(new Data("Examen", 80, 70, 100));
        store.add(new Data("Examen final", 60, 50, 100));
    }


    @Override
    public Widget asWidget() {
        if (panel == null) {
            Chart<Data> chart = new Chart<Data>(600, 500);
            chart.setStore(store);
            chart.setAnimated(true);
            chart.setShadowChart(false);

            NumericAxis<Data> axis = new NumericAxis<Data>();
            axis.setPosition(Chart.Position.BOTTOM);
            axis.addField(dataAccess.grade());
            axis.addField(dataAccess.averageGrade());
            axis.addField(dataAccess.maxGrade());
            axis.setDisplayGrid(true);
            chart.addAxis(axis);


            CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
            catAxis.setPosition(Chart.Position.LEFT);
            catAxis.setField(dataAccess.evaluationName());
            chart.addAxis(catAxis);


            final BarSeries<Data> bar = new BarSeries<Data>();
            bar.setYAxisPosition(Chart.Position.BOTTOM);
            bar.addYField(dataAccess.grade());
            bar.addYField(dataAccess.averageGrade());
            bar.addYField(dataAccess.maxGrade());
            bar.addColor(new RGB(148, 174, 10));
            bar.addColor(new RGB(17, 95, 166));
            bar.addColor(new RGB(166, 17, 32));
            bar.addColor(new RGB(255, 136, 9));
            bar.setStacked(true);

            //bar.setToolTipConfig(config);

            final Legend<Data> legend = new Legend<Data>();
            legend.setItemHiding(true);
            legend.setItemHighlighting(true);
            legend.getBorderConfig().setStrokeWidth(0);

            chart.addSeries(bar);
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