package org.spaceinvaders.shared.model;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;

import java.util.List;

/**
 * Created by anonhyme on 5/25/2015.
 */
/**
 * An interface for extracting a value of type C from an underlying data value
 * of type T, provide a {@link Cell} to render that value, and provide a
 * {@link FieldUpdater} to perform notification of updates to the cell.
 *
 * @param <T> the underlying data type
 * @param <C> the return data type
 */
public interface BaseGrid<T> {

    T getT();

    List<String> getApValue();

    List<String> getEvaluationValue();
}
