package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.GaugeAxis;
import com.sencha.gxt.chart.client.chart.series.GaugeSeries;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.fx.client.easing.Default;
import com.sencha.gxt.fx.client.easing.EasingFunction;
import com.sencha.gxt.fx.client.easing.ElasticIn;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;


/**
 * Created by Etienne on 2015-05-20.
 */
public class GaugeChart extends AbstractChart{

    public class Data {
        private String competencyName;
        private double gradeTotal;
        private double currentMax;

        public Data(String name, double gradeTotal, double currentMax) {
            this.competencyName = name;
            this.gradeTotal = gradeTotal;
            this.currentMax = currentMax;
        }

        public double getGradeTotal() {
            return gradeTotal;
        }

        public void setGradeTotal(double gradeTotal) {
            this.gradeTotal = gradeTotal;
        }

        public double getCurrentMax() {
            return currentMax;
        }

        public void setCurrentMax(double currentMax) {
            this.currentMax = currentMax;
        }

        public String getCompetencyName() {
            return competencyName;
        }

        public void setCompetencyName(String name) {
            this.competencyName = name;
        }
    }

    public interface DataPropertyAccess extends PropertyAccess<Data> {
        ValueProvider<Data, Double> gradeTotal();

        ValueProvider<Data, Double> currentMax();

        ValueProvider<Data, String> competencyName();

        @Editor.Path("id")
        ModelKeyProvider<Data> nameKey();
    }

    private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
    ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());

    private FramedPanel panel;

    @Override
    public void setData() {
        store.add(new Data("GEN501", 50, 30));
    }

    public Widget asWidget() {
        if (panel == null) {
            final Chart<Data> chart1 = createGauge(store, 30, new RGB("#82B525"), false, new Default(), dataAccess.gradeTotal());
            final Chart<Data> chart2 = createGauge(store, 80, new RGB("#3AA8CB"), false, new ElasticIn(), dataAccess.currentMax());


            VerticalLayoutContainer layout = new VerticalLayoutContainer();
            layout.add(chart1, new VerticalLayoutContainer.VerticalLayoutData(1, 0.5));
            layout.add(chart2, new VerticalLayoutContainer.VerticalLayoutData(1, 0.5));

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

    private Chart<Data> createGauge(ListStore<Data> store, double donut, Color color, boolean needle,
                                    EasingFunction easing, ValueProvider<Data, Double> provider) {
        GaugeAxis<Data> axis = new GaugeAxis<Data>();
        axis.setMargin(8);
        axis.setDisplayGrid(true);
        axis.setMinimum(0);
        axis.setMaximum(100);

        final GaugeSeries<Data> gauge = new GaugeSeries<Data>();
        gauge.addColor(color);
        gauge.addColor(new RGB("#ddd"));
        gauge.setAngleField(provider);
        gauge.setNeedle(needle);
        gauge.setDonut(donut);

        Chart<Data> chart = new Chart<Data>();
        chart.setStore(store);
        chart.setAnimationDuration(750);
        chart.setAnimationEasing(easing);
        chart.setDefaultInsets(30);
        chart.addAxis(axis);
        chart.addSeries(gauge);

        return chart;
    }
}
