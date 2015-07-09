package org.spaceinvaders.client.application.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.events.SemesterChangedEvent;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dto.SemesterInfo;
import org.spaceinvaders.shared.dto.UserInfo;

import java.util.List;

@Singleton
public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements MenuUiHandlers {


    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setUserName(String userName);

        void setSemesterDropdown(List<SemesterInfo> semesterInfoList);
    }

    private final ResourceDelegate<UserInfoResource> userInfoDelegate;
    private final ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

    @Inject
    MenuPresenter(EventBus eventBus,
                  MyView view,
                  ResourceDelegate<UserInfoResource> userInfoDelegate,
                  ResourceDelegate<SemesterInfoResource> semesterInfoDelegate) {
        super(eventBus, view);
        this.userInfoDelegate = userInfoDelegate;
        this.semesterInfoDelegate = semesterInfoDelegate;
        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();
        fetchUserInfo();
        fetchSemesterList();
    }

    private void fetchUserInfo() {
        GWT.log("fetchUserInfo asyncCall get");
        userInfoDelegate.withCallback(new AbstractAsyncCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                getView().setUserName(result.getFirstName() + " " + result.getLastName());
            }
        }).get();
    }

    private void fetchSemesterList() {
        semesterInfoDelegate.withCallback(new AbstractAsyncCallback<List<SemesterInfo>>() {
            @Override
            public void onSuccess(List<SemesterInfo> result) {
                getView().setSemesterDropdown(result);
            }
        }).getAll();
    }

    @Override
    public void semesterChanged(int semesterID) {
        GWT.log("::: semesterChanged ::: " + semesterID);
        SemesterChangedEvent.fire(semesterID, this);
    }

    @Override
    public void disconnect() {
        GWT.log("Should disconnect .. but no one did it yet");
    }
}
