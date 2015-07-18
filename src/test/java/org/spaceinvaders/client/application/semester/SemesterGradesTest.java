package org.spaceinvaders.client.application.semester;

import com.google.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 6/5/2015
 *
 * @author antoine
 */
@RunWith(JukitoRunner.class)
public class SemesterGradesTest {
    @Inject
    SemesterPresenter semesterPresenter;

    @Test
    public void onBind_anytime_updateTitle(SemesterPresenter.MyView myView) {

    }
}
