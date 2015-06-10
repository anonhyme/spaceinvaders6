package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.application.griddemo.GridDemoPresenter;
import org.spaceinvaders.client.events.LoginEvent;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.commons.WidgetsFactory;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.GetSemesterInfoResult;
import org.spaceinvaders.shared.dispatch.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.GetUserInfoResult;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

// TODO : rename this class and others to something more appropriate (SemesterGridPresenter?)

public class SemesterGradesPresenter extends Presenter<SemesterGradesPresenter.MyView, SemesterGradesPresenter.MyProxy> {
    public interface MyView extends View {
    }
    private static final Logger LOGGER = Logger.getLogger(SemesterGradesPresenter.class.getName());

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterGradesPresenter> {
    }

    public static final Object SLOT_MENU_WIDGET = new Object();

    private final WidgetsFactory widgetsFactory;
    private DispatchAsync dispatcher;
    private String userName = "";

    @Inject
    SemesterGradesPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            DispatchAsync dispatchAsync,
                            WidgetsFactory widgetsFactory) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.dispatcher = dispatchAsync;
        this.widgetsFactory = widgetsFactory;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        LOGGER.log(Level.INFO, "callServerLoginAction(): Server failed to process login call.");
        dispatcher.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {
                setUserName(result.getUserInfo().getCip());
            }
        });

        // TODO : Remove that and put it where we'll really use it
        //TODO : Inject User session to get the id
//        this.dispatcher.execute(new GetSemesterGradesAction("bedh2102", 3), new AsyncCallback<GetSemesterGradesResult>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert(caught.getMessage());
//            }
//
//            @Override
//            public void onSuccess(GetSemesterGradesResult result) {
//                GWT.log("result competence = " + result.getEvaluationResults().get(0).getCompetenceLabel());
//            }
//        });

//        this.dispatcher.execute(new GetSemesterInfoAction("bedh2102", 3), new AsyncCallback<GetSemesterInfoResult>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert(caught.getMessage());
//            }
//
//            @Override
//            public void onSuccess(GetSemesterInfoResult result) {
//                GWT.log("ap = " + result.getSemesterInfo().getCompetences().get(0).getApLabel());
//                GWT.log("competence = " + result.getSemesterInfo().getCompetences().get(4).getCompetenceLabel());
//            }
//        });
    }

    private void setUserName(String userName) {
        GWT.log("setUserName ::::::::: " + userName);
        LoginEvent.fire(userName, this);
        addToSlot(SLOT_MENU_WIDGET, widgetsFactory.createTopMenu(userName));
    }

    private String getUserName(){
        return this.userName;
    }
}