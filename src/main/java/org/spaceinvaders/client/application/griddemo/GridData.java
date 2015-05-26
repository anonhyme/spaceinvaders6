package org.spaceinvaders.client.application.griddemo;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/26/2015
 *
 * @author antoine
 */
public class GridData<T> {
    T t;
    String header;
    String rowTitle;

    public GridData() {
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRowTitle() {
        return rowTitle;
    }

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }
}
