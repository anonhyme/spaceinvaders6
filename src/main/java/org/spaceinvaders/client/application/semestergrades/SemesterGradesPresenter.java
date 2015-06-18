package org.spaceinvaders.client.application.semestergrades;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.widgets.menu.MenuPresenter;
import org.spaceinvaders.shared.api.UserInfoResource;

import javax.inject.Inject;

public class SemesterGradesPresenter extends Presenter<SemesterGradesPresenter.MyView, SemesterGradesPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.semesterGrades)
    public interface MyProxy extends ProxyPlace<SemesterGradesPresenter> {
    }

    public static final Object SLOT_MENU_WIDGET = new Object();
    private MenuPresenter menuPresenter;
    private final RestDispatch restDispatch;

    @Inject
    SemesterGradesPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            MenuPresenter menuPresenter,
                            RestDispatch restDispatch) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.menuPresenter = menuPresenter;
        this.restDispatch = restDispatch;
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
    }
}