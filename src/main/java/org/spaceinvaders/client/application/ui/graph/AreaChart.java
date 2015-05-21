package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.AreaSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;

/**
 * Created by Etienne on 2015-05-20.
 */
public class AreaChart extends AbstractChart {

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
        addData("Rapport", 50, 60, 100);
        addData("Evaluation 1", 60, 80, 100);
        addData("Evaluation 2", 70, 90, 100);
        addData("Examen", 80, 70, 100);
        addData("Examen final", 60, 50, 100);

    }

    private int currentTotalGrade;
    private int currentTotalAverageGrade;
    private int currentTotalMaxGrade;

    void AreaChart() {
        currentTotalGrade = 0;
        currentTotalAverageGrade = 0;
        currentTotalMaxGrade = 0;
    }

    private void addData(String name, double grade, double average, double maxGrade) {
        currentTotalGrade += grade;
        currentTotalAverageGrade += average;
        currentTotalMaxGrade += maxGrade;
        store.add(new Data(name, currentTotalGrade, currentTotalAverageGrade, currentTotalMaxGrade));
    }

    private Chart<Data> chart;

    @Override
    public Widget asWidget() {
        if (panel == null) {

            TextSprite titleHits = new TextSprite("Progression session");
            titleHits.setFontSize(18);

            TextSprite titleMonthYear = new TextSprite("Evaluation");
            titleMonthYear.setFontSize(18);

            PathSprite gridConfig = new PathSprite();
            gridConfig.setStroke(new RGB("#bbb"));
            gridConfig.setFill(new RGB("#ddd"));
            gridConfig.setZIndex(1);
            gridConfig.setStrokeWidth(1);

            NumericAxis<Data> axis = new NumericAxis<Data>();
            axis.setPosition(Chart.Position.LEFT);
            axis.addField(dataAccess.grade());
            axis.addField(dataAccess.averageGrade());
            axis.addField(dataAccess.maxGrade());
            axis.setGridOddConfig(gridConfig);
            axis.setDisplayGrid(true);
            axis.setTitleConfig(titleHits);
            axis.setMinorTickSteps(2);

            TextSprite labelConfig = new TextSprite();
            labelConfig.setRotation(315);

            CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
            catAxis.setPosition(Chart.Position.BOTTOM);
            catAxis.setField(dataAccess.evaluationName());
            catAxis.setTitleConfig(titleMonthYear);
            catAxis.setDisplayGrid(true);
            catAxis.setLabelConfig(labelConfig);
            catAxis.setLabelPadding(-10);
            catAxis.setMinorTickSteps(5);
            catAxis.setLabelTolerance(20);

            PathSprite highlightLine = new PathSprite();
            highlightLine.setHidden(true);
            highlightLine.addCommand(new MoveTo(0, 0));
            highlightLine.setZIndex(1000);
            highlightLine.setStrokeWidth(5);
            highlightLine.setStroke(new RGB("#444"));
            highlightLine.setOpacity(0.3);

            AreaSeries<Data> series = new AreaSeries<Data>();
            series.setHighlighting(true);
            series.setYAxisPosition(Chart.Position.LEFT);
            series.addYField(dataAccess.grade());
            series.addYField(dataAccess.averageGrade());
            series.addYField(dataAccess.maxGrade());
            series.addColor(new RGB(148, 174, 10));
            series.addColor(new RGB(17, 95, 166));
            series.addColor(new RGB(166, 17, 32));
            series.setHighlightLineConfig(highlightLine);
            series.setRenderer(new SeriesRenderer<Data>() {
                @Override
                public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
                    sprite.setOpacity(0.93);
                    sprite.redraw();
                }
            });

            Legend<Data> legend = new Legend<Data>();
            legend.setItemHighlighting(true);
            legend.setItemHiding(true);
            legend.getBorderConfig().setStrokeWidth(0);

            chart = new Chart<Data>();
            chart.setStore(store);
            // Allow room for rotated labels
            chart.setDefaultInsets(20);
            chart.addAxis(axis);
            chart.addAxis(catAxis);
            chart.addSeries(series);
            chart.setLegend(legend);


            VerticalLayoutContainer layout = new VerticalLayoutContainer();
            layout.add(chart, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

            panel = new FramedPanel();
            panel.setLayoutData(new MarginData(10));
            panel.setCollapsible(true);
            panel.setHeadingText("Area Chart");
            panel.setPixelSize(620, 500);
            panel.setBodyBorder(true);
            panel.add(layout);

            final Resizable resize = new Resizable(panel, Resizable.Dir.E, Resizable.Dir.SE, Resizable.Dir.S);
            resize.setMinHeight(400);
            resize.setMinWidth(500);
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
