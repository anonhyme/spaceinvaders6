package org.spaceinvaders.client.application.restpage;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import org.spaceinvaders.client.application.util.AbstractAsyncCallback;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.api.SemesterGradesResource;
import org.spaceinvaders.shared.api.SemesterInfoResource;
import org.spaceinvaders.shared.api.UserInfoResource;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;
import org.spaceinvaders.shared.dto.UserInfo;

import java.util.SortedMap;

public class RestPagePresenter extends Presenter<RestPagePresenter.MyView, RestPagePresenter.MyProxy> {
    interface MyView extends View {
    }

    private final ResourceDelegate<UserInfoResource> userInfoDelegate;
    private final ResourceDelegate<SemesterGradesResource> semesterGradesDelegate;

    @NameToken(NameTokens.lolololo)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<RestPagePresenter> {
    }

    @Inject
    ResourceDelegate<SemesterInfoResource> semesterInfoDelegate;

    @Inject
    public RestPagePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<UserInfoResource> userInfoDelegate,
            ResourceDelegate<SemesterGradesResource> semesterGradesDelegate) {
        super(eventBus, view, proxy, RevealType.Root);

        this.userInfoDelegate = userInfoDelegate;
        this.semesterGradesDelegate = semesterGradesDelegate;
    }

    @Override
    protected void onBind() {
        super.onBind();
        userInfoDelegate
                .withCallback(new AbstractAsyncCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
                        GWT.log("Rest cip = " + result.getLastName());
                    }
                }).get();

        semesterGradesDelegate
                .withCallback(new AbstractAsyncCallback<SortedMap<String, Evaluation>>() {
                    @Override
                    public void onSuccess(SortedMap<String, Evaluation> result) {
                        GWT.log("Rest evaluation map label = " + result.get(result.firstKey()).getEvaluationLabel());
                    }
                }).getAllEvaluations(3);

//        semesterGradesDelegate
//                .withCallback(new AbstractAsyncCallback<List<CompetenceEvalResult>>() {
//                    @Override
//                    public void onSuccess(List<CompetenceEvalResult> results) {
//                        GWT.log("Rest competence label = " + results.get(0).getCompetenceLabel());
//                    }
//                }).getAllCompetenceEvalResults(3);

        semesterInfoDelegate
                .withCallback(new AbstractAsyncCallback<SemesterInfo>() {
                    @Override
                    public void onSuccess(SemesterInfo result) {
                        GWT.log("Rest semester info frist competence ap label = " + result.getCompetences().get(0).getApLabel());
                    }
                }).get(3);
    }
}







