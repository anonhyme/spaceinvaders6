package org.spaceinvaders.client.widgets.commons;

import org.spaceinvaders.client.widgets.menu.MenuPresenter;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */
public interface WidgetsFactory {
    MenuPresenter createTopMenu(String userId);
}
