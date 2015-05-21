package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
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
public class GroupBarChart extends AbstractChart {
    public class Data {
        private String className;
        private double grade;
        private double averageGrade;
        private double maxGrade;

        public Data(String name, double grade, double averageGrade, double maxGrade) {
            this.className = name;
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

        public String getClassName() {
            return className;
        }

        public void setClassName(String name) {
            this.className = name;
        }
    }

    private FramedPanel panel;

    public interface DataPropertyAccess extends PropertyAccess<Data> {
        ValueProvider<Data, Double> grade();

        ValueProvider<Data, Double> averageGrade();

        ValueProvider<Data, Double> maxGrade();

        ValueProvider<Data, String> className();

        @Editor.Path("className")
        ModelKeyProvider<Data> nameKey();
    }

    private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
    ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());

    @Override
    public void setData() {
        store.add(new Data("GEN501", 50, 60, 99));
        store.add(new Data("GEN502", 60, 80, 99));
        store.add(new Data("GEN503", 50, 60, 99));
        store.add(new Data("GEN504", 60, 80, 99));
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            TextSprite title = new TextSprite("Note cumulative");
            title.setFontSize(18);

            NumericAxis<Data> axis = new NumericAxis<Data>();
            axis.setPosition(Chart.Position.BOTTOM);
            axis.addField(dataAccess.grade());
            axis.addField(dataAccess.averageGrade());
            axis.addField(dataAccess.maxGrade());
            axis.setTitleConfig(title);
            axis.setDisplayGrid(true);
            axis.setMinimum(0);
            axis.setMaximum(100);

            title = new TextSprite("Cours");
            title.setFontSize(18);

            CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
            catAxis.setPosition(Chart.Position.LEFT);
            catAxis.setField(dataAccess.className());
            catAxis.setTitleConfig(title);

            final BarSeries<Data> bar = new BarSeries<Data>();
            bar.setYAxisPosition(Chart.Position.BOTTOM);
            bar.addYField(dataAccess.grade());
            bar.addYField(dataAccess.averageGrade());
            bar.addYField(dataAccess.maxGrade());
            bar.addColor(new RGB(148, 174, 10));
            bar.addColor(new RGB(17, 95, 166));
            bar.addColor(new RGB(166, 17, 32));

            final Legend<Data> legend = new Legend<Data>();
            legend.setItemHighlighting(true);
            legend.setItemHiding(true);
            legend.getBorderConfig().setStrokeWidth(0);

            Chart<Data> chart = new Chart<Data>();
            chart.setStore(store);
            chart.setShadowChart(false);
            chart.addAxis(axis);
            chart.addAxis(catAxis);
            chart.addSeries(bar);
            chart.setLegend(legend);
            chart.setDefaultInsets(20);

            VerticalLayoutContainer layout = new VerticalLayoutContainer();
            layout.add(chart, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

            panel = new FramedPanel();
            panel.setLayoutData(new MarginData(10));
            panel.setCollapsible(true);
            panel.setHeadingText("Grouped Bar Chart");
            panel.setPixelSize(620, 500);
            panel.setBodyBorder(true);
            panel.add(chart);

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
