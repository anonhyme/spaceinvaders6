package org.spaceinvaders.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;

import org.spaceinvaders.shared.model.TableDataTest;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/20/2015
 *
 * @author antoine
 */
public interface HomeUiHandlers extends UiHandlers {

    List<TableDataTest> getDataList();
}
