package org.spaceinvaders.client.application.home;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

import org.spaceinvaders.client.application.ApplicationPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.client.rpc.SemesterServiceAsync;
import org.spaceinvaders.shared.model.TableDataTest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> implements HomeUiHandlers {
    public interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
        void updateTable(List<TableDataTest> result);
    }

    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }


    private List<TableDataTest> topResult = new ArrayList<TableDataTest>();

    private SemesterServiceAsync exampleService;

    @Inject
    HomePagePresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      SemesterServiceAsync exampleService,
                      DispatchAsync dispatchAsync
    ) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

        this.exampleService = exampleService;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

    }

    @Override
    protected void onReset() {
        super.onReset();
    }

//    @Override
//    public void getData() {
//        exampleService.fetchData(new AsyncCallback<List<TableDataTest>>() {
//            List<TableDataTest> tableDataTests = new ArrayList<TableDataTest>();
//
//            @Override
//            public void onFailure(Throwable caught) {
//            }
//
//            @Override
//            public void onSuccess(List<TableDataTest> result) {
//                setResult(result);
//                getView().updateTable(result);
//            }
//        });
//        GWT.log("getData() " + getTopResult());
//    }

    public List<TableDataTest> getDataList() {
        GWT.log("getDataList [" + topResult + "]");
        return getTopResult();
    }

    void setResult(List<TableDataTest> result) {
        GWT.log("SetResult [" + result + "]");
        this.topResult = result;
    }

    public List<TableDataTest> getTopResult() {
        GWT.log("getTopResult [" + topResult + "]");
        return topResult;
    }
}
