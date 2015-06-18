package org.spaceinvaders.client.application.ui.graph.GwtCharts;

import com.google.gwt.user.client.ui.Widget;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

/**
 * Created by Etienne on 2015-06-10.
 */
public abstract class AbstractGWTChart{
    protected int width;
    protected int height;
    protected boolean isCustomSize = false;
    protected String[] colors;
    protected boolean colorsSet;

    abstract public Widget getChart();

    abstract public void loadChart();

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        isCustomSize = true;
    }

    public void setColors(String[] colorsArray){
        colors = colorsArray;
        colorsSet = true;
    }
}
