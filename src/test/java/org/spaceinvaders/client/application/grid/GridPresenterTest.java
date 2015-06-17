package org.spaceinvaders.client.application.grid;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/16/2015
 *
 * @author antoine
 */
@RunWith(JukitoRunner.class)
public class GridPresenterTest {

    private GridPresenter gridPresenter;

    @Inject
    private  EventBus eventBus;
    @Inject
    private  GridPresenter.MyView view;
    @Inject
    private  DispatchAsync dispatchAsync;
    @Inject
    private  GridPresenter.MyProxy proxy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
