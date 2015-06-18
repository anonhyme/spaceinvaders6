package org.spaceinvaders.client.application.menu;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MenuUiHandlers extends UiHandlers {
    void disconnect();

    String getUserName();
}
