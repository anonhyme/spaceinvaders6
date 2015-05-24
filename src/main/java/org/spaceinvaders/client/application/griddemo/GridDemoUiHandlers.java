package org.spaceinvaders.client.application.griddemo;

import com.gwtplatform.mvp.client.UiHandlers;

import org.spaceinvaders.shared.model.TableDataTest;

public interface GridDemoUiHandlers extends UiHandlers {
    void fetchSemesterInfo();

    void initDataGrid();

}
