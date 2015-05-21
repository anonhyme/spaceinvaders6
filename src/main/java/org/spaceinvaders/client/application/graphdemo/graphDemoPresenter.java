


package org.spaceinvaders.client.application.graphdemo;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetPresenter;
import org.spaceinvaders.client.place.NameTokens;

public class GraphDemoPresenter extends Presenter<GraphDemoPresenter.MyView, GraphDemoPresenter.MyProxy> {

    interface MyView extends View {
        void setGraph(GraphWidgetPresenter presenter);
    }

    @Inject
    Provider<GraphWidgetPresenter> graphWidgetPresenterProvider;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_graphDemo = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.graphDemo)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<GraphDemoPresenter> {
    }


    @Inject
    public GraphDemoPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy
    ) {
        super(eventBus, view, proxy, RevealType.Root);
        // super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);

    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

    protected void onBind() {
        super.onBind();
        MyView view = getView();

        GraphWidgetPresenter graphPresenter = graphWidgetPresenterProvider.get();
        graphPresenter.setGraphType(GraphWidgetPresenter.ChartType.CumulativeGradeLineChart);
        graphPresenter.setChartData();
        graphPresenter.showChart();
        view.setGraph(graphPresenter);


        GraphWidgetPresenter graphPresenter2 = graphWidgetPresenterProvider.get();
        graphPresenter2.setGraphType(GraphWidgetPresenter.ChartType.GroupBar);
        graphPresenter2.setChartData();
        graphPresenter2.showChart();
        view.setGraph(graphPresenter2);
        //setInSlot(SLOT_graphDemo, graphPresenter);
    }
}
