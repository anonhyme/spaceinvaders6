package org.spaceinvaders.client.application.semestergrades;

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
    SemesterGradesPresenter semesterGradesPresenter;

    @Test
    public void onReveal_anytime_setsTitleIntoView(SemesterGradesPresenter.MyView myView){

    }
}
