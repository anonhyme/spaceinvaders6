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
public abstract class AbstractChart<T> extends Composite {

    abstract public void setData(T data);

    abstract public void resize(int x, int y);


}
