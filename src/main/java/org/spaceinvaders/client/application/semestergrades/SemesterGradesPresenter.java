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

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterGradesAction;
import org.spaceinvaders.shared.dispatch.actions.GetSemesterInfoAction;
import org.spaceinvaders.shared.dispatch.actions.GetUserInfoAction;
import org.spaceinvaders.shared.dispatch.results.GetSemesterGradesResult;
import org.spaceinvaders.shared.dispatch.results.GetSemesterInfoResult;
import org.spaceinvaders.shared.dispatch.results.GetUserInfoResult;

import javax.inject.Inject;

// TODO : rename this class and others to something more appropriate (SemesterGridPresenter?)

public class SemesterGradesPresenter extends Presenter<SemesterGradesPresenter.MyView, SemesterGradesPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterGradesPresenter> {
    }

    private DispatchAsync dispatcher;
    private MenuPresenter menuPresenter;
    public static final Object SLOT_MENU_WIDGET = new Object();

    @Inject
    SemesterGradesPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            DispatchAsync dispatchAsync,
                            MenuPresenter menuPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.dispatcher = dispatchAsync;
        this.menuPresenter = menuPresenter;
    }


    private void addNavbar() {
        GWT.log("SemesterGradesPresenter ::: Navbar added ");
        addToSlot(SLOT_MENU_WIDGET, menuPresenter);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        addNavbar();
    }

    @Override
    protected void onBind() {
        super.onBind();

        this.dispatcher.execute(new GetUserInfoAction(), new AsyncCallback<GetUserInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetUserInfoResult result) {
                GWT.log("Last name = " + result.getUserInfo().getLastName());
            }
        });

        // TODO : Remove that and put it where we'll really use it
//        this.dispatcher.execute(new GetSemesterGradesAction(3), new AsyncCallback<GetSemesterGradesResult>() {
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

        this.dispatcher.execute(new GetSemesterInfoAction(3), new AsyncCallback<GetSemesterInfoResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(GetSemesterInfoResult result) {
                GWT.log("ap = " + result.getSemesterInfo().getCompetences().get(0).getApLabel());
                GWT.log("competence = " + result.getSemesterInfo().getCompetences().get(4).getCompetenceLabel());
            }
        });
    }
}