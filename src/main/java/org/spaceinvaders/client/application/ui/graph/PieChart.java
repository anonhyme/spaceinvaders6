package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.chart.client.chart.series.Series;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.*;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;

/**
 * Created by Etienne on 2015-05-19.
 */
public class PieChart extends AbstractChart {
    // Chart data model
    public class DataModel {
        private int id;
        private String name;
        private double data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getData() {
            return data;
        }

        public void setData(double data) {
            this.data = data;
        }
    }

    // Chart data model property access definitions
    public interface DataModelProperties extends PropertyAccess<DataModel> {
        ModelKeyProvider<DataModel> id();

        ValueProvider<DataModel, String> name();

        ValueProvider<DataModel, Double> data();
    }

    public static final DataModelProperties dataModelProperties = GWT.create(DataModelProperties.class);
    private ListStore<DataModel> listStore;
    private Chart<DataModel> chart;
    private FramedPanel panel;


    @Override
    public Widget asWidget() {
        if (panel == null) {
            // Setup the chart list store
            listStore = new ListStore<DataModel>(dataModelProperties.id());

            // Setup the chart
            chart = new Chart<DataModel>();
            chart.setStore(listStore);

            // Set the chart legend
            Legend<DataModel> legend = new Legend<DataModel>();
            legend.setPosition(Chart.Position.RIGHT);
            legend.setItemHighlighting(true);
            legend.setItemHiding(true);
            chart.setLegend(legend);

            // Setup series slice colors
            Gradient slice1 = new Gradient("slice1", 45);
            slice1.addStop(new Stop(0, new RGB(148, 174, 10)));
            slice1.addStop(new Stop(100, new RGB(107, 126, 7)));
            chart.addGradient(slice1);
            Gradient slice2 = new Gradient("slice2", 45);
            slice2.addStop(new Stop(0, new RGB(17, 95, 166)));
            slice2.addStop(new Stop(100, new RGB(12, 69, 120)));
            chart.addGradient(slice2);

            // Setup the chart series
            PieSeries<DataModel> series = new PieSeries<DataModel>();
            series.setAngleField(dataModelProperties.data());
            series.setDonut(50);
            series.addColor(slice1);
            series.addColor(slice2);

            // Setup the label config
            SeriesLabelConfig<DataModel> labelConfig = new SeriesLabelConfig<DataModel>();
            labelConfig.setLabelPosition(Series.LabelPosition.START);
            labelConfig.setValueProvider(dataModelProperties.name(), new StringLabelProvider<String>());
            series.setLabelConfig(labelConfig);

            // Setup the legend label config
            series.setLegendValueProvider(dataModelProperties.name(), new LabelProvider<String>() {
                public String getLabel(String item) {
                    return item.substring(0, 3);
                }
            });
            chart.addSeries(series);

            addSomeData();

            VerticalLayoutContainer layout = new VerticalLayoutContainer();
            layout.add(chart, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

            panel = new FramedPanel();
            panel.setLayoutData(new MarginData(10));
            panel.setCollapsible(true);
            panel.setHeadingText("Pie Chart");
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

    public void setData() {

    }

    private void addSomeData() {
        DataModel item1 = new DataModel();
        item1.setId(1);
        item1.setName("male");
        item1.setData(50);

        DataModel item2 = new DataModel();
        item2.setId(2);
        item2.setName("female");
        item2.setData(50);

        listStore.add(item1);
        listStore.add(item2);
    }
}