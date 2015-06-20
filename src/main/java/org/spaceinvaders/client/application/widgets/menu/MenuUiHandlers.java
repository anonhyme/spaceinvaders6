package org.spaceinvaders.client.application.widgets.menu;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MenuUiHandlers extends UiHandlers {
    void disconnect();

    void semesterChanged(int semesterID);
}
