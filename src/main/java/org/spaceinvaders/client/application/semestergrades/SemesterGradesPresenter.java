package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.client.place.NameTokens;

import javax.inject.Inject;

public class SemesterGradesPresenter extends Presenter<SemesterGradesPresenter.MyView, SemesterGradesPresenter.MyProxy>
        implements SemesterChangedEvent.SemesterChangedHandler {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterGradesPresenter> {
    }

    @Inject
    SemesterGradesPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            RestDispatch restDispatch) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
    }

    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(SemesterChangedEvent.TYPE, this);
    }

    @Override
    public void onSemesterChanged(SemesterChangedEvent event) {
        GWT.log(SemesterGradesPresenter.class.toString() + ": this is how you know if the semester changed");
    }
}