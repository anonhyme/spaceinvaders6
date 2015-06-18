package org.spaceinvaders.client.application.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dto.UserInfo;

@Singleton
public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements MenuUiHandlers {

    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setUserName(String userName);
    }

    private final ResourceDelegate<UserInfoResource> userInfoDelegate;

    @Inject
    MenuPresenter(EventBus eventBus,
                  MyView view,
                  ResourceDelegate<UserInfoResource> userInfoDelegate) {
        super(eventBus, view);
        this.userInfoDelegate = userInfoDelegate;
        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();
        fetchUserInfo();
    }

    private void fetchUserInfo() {
        userInfoDelegate.withCallback(new AbstractAsyncCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                getView().setUserName(result.getFirstName() + " " + result.getLastName());
            }
        }).get();
    }

    @Override
    public void semesterChanged(int semesterID) {
        SemesterChangedEvent.fire(semesterID, this);
    }

    @Override
    public void disconnect() {
        Window.alert("The Presenter says Hi !");
        Window.alert("But it's stupid to make this alert in the presenter !");

        SemesterChangedEvent.fire(3, this);
    }
}
