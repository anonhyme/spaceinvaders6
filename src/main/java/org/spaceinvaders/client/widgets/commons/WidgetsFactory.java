package org.spaceinvaders.client.widgets.commons;

import org.spaceinvaders.client.widgets.materialmenu.MaterialMenuPresenter;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/2/2015
 *
 * @author antoine
 */
public interface WidgetsFactory {
    MaterialMenuPresenter createTopMenu(String userId);
}
