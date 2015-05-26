package org.spaceinvaders.shared.model;

import java.util.List;

/**
 * Created by anonhyme on 5/25/2015.
 */
public class AbstractGrid<T extends BaseGrid> {
    private T t;
    private String evaluationType;
    private String evaluationResultTotal;

    public AbstractGrid() {
    }

    public AbstractGrid(T t) {
        this.t = t;
    }

    List<String> getColumnValue() {
        return t.getApValue();
    }

    List<String> getRowValue() {
        return t.getEvaluationValue();
    }
}
