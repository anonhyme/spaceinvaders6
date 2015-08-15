package org.spaceinvaders.client.application.widgets.graph.gwtcharts;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.gwt.charts.client.DataTable;

public abstract class AbstractGWTChart {
    protected int width;
    protected int height;
    protected boolean isCustomSize = false;
    protected String[] colors;
    protected boolean colorsSet;
    protected DataTable dataTable;

    private static double WINDOW_WIDTH_RATIO = 0.6;
    private static double WINDOW_HEIGHT_RATIO = 0.4;

    abstract public Widget getChart();

    abstract void setDataTable();

    abstract void setOptions();

    abstract void update();

    public void loadChart() {
        setDataTable();
        setOptions();
        update();
        registerResizeHandler();
    }

    public void setSizeFromWindowSize(int windowWidth, int windowHeight) {
        this.width = (int) (windowWidth * WINDOW_WIDTH_RATIO);
        this.height = (int) (windowHeight * WINDOW_HEIGHT_RATIO);
        isCustomSize = true;
    }

    public void setColors(String[] colorsArray) {
        colors = colorsArray;
        colorsSet = true;
    }

    private AbstractGWTChart getInstance() {
        return this;
    }

    private void registerResizeHandler() {
        Window.addResizeHandler(new ResizeHandler() {
            Timer resizeTimer = new Timer() {
                @Override
                public void run() {
                }
            };

            @Override
            public void onResize(ResizeEvent event) {
                resizeTimer.cancel();
                resizeTimer.schedule(300);
                getInstance().setSizeFromWindowSize(event.getWidth(), event.getHeight());
                getInstance().update();
            }
        });
    }
}
