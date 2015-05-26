package org.spaceinvaders.shared.model;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;

import java.util.List;

/**
 * Created by anonhyme on 5/25/2015.
 */
public interface BaseGrid {

    List<String> getApList();

    List<String> getEvaluationList();
}
