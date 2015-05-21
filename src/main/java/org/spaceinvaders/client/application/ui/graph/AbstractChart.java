package org.spaceinvaders.client.application.ui.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.FramedPanel;

/**
 * Created by Etienne on 2015-05-20.
 */
public abstract class AbstractChart extends Composite {

    // Needs to define DataModel
    /* Example
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

    public interface DataModelProperties extends PropertyAccess<DataModel> {
        ModelKeyProvider<DataModel> id();
        ValueProvider<DataModel, String> name();
        ValueProvider<DataModel, Double> data();
    }
     public static final DataModelProperties dataModelProperties = GWT.create(DataModelProperties.class);
    private ListStore<DataModel> listStore;
    //private Chart<DataModel> chart;
    //private FramedPanel panel;
*/

    abstract public void setData();


}
