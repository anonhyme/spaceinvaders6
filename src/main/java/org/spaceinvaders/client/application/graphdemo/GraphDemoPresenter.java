


package org.spaceinvaders.client.application.graphdemo;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.IsWidget;
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
import org.spaceinvaders.client.application.ui.graph.CumulativeGradeLineChart;
import org.spaceinvaders.client.application.ui.graph.graphWidget.GraphWidgetPresenter;
import org.spaceinvaders.client.entities.ApSummaryEntity;
import org.spaceinvaders.client.entities.CompetenceResultsEntity;
import org.spaceinvaders.client.place.NameTokens;

import java.util.ArrayList;
import java.util.List;

public class GraphDemoPresenter extends Presenter<GraphDemoPresenter.MyView, GraphDemoPresenter.MyProxy> {

    interface MyView extends View {
        void setGraph(IsWidget presenter);
        void setCol1(IsWidget presenter);
        void setCol2(IsWidget presenter);
        void setCol3(IsWidget presenter);
        void setCol4(IsWidget presenter);
        void setCol5(IsWidget presenter);
        void setCol6(IsWidget presenter);
        void setStudentProgress(double progress);
        void setClassProgress(double progress);
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

        List<ApSummaryEntity> apSummaryEntityList= new ArrayList<ApSummaryEntity>();
        List<CompetenceResultsEntity> competenceResultsEntityList= new ArrayList<CompetenceResultsEntity>();

        for (int i =0; i<6; i++) {
            ApSummaryEntity e = new ApSummaryEntity();
            e.setApName("GIF50" + i);
            e.setApResult((int) Math.round(Math.random() * 100));
            e.setApAverage((int) Math.round(Math.random() * 100));
            e.setApMax(100);
            apSummaryEntityList.add(e);
        }
        ApSummaryEntity e = new ApSummaryEntity();
        e.setApName("Gel501");

        for (int i =0; i<6; i++) {
            CompetenceResultsEntity ce = new CompetenceResultsEntity();
            ce.setEvalLabel("Rapport " + i);
            ce.setCompetenceLabel("1");
            ce.setResultValue((int) Math.round(Math.random() * 100));
            ce.setAvgResultValue((int) Math.round(Math.random() * 100));
            ce.setMaxResultValue(100);
            competenceResultsEntityList.add(ce);
        }

        for (int i =0; i<6; i++) {
            CompetenceResultsEntity ce = new CompetenceResultsEntity();
            ce.setEvalLabel("Rapport " + i);
            ce.setCompetenceLabel("2");
            ce.setResultValue((int) Math.round(Math.random() * 100));
            ce.setAvgResultValue((int) Math.round(Math.random() * 100));
            ce.setMaxResultValue(100);
            competenceResultsEntityList.add(ce);
        }

        view.setClassProgress(60.9);
        view.setStudentProgress(50);

        /*GraphWidgetPresenter graphPresenter = graphWidgetPresenterProvider.get();
        graphPresenter.setGraphType(GraphWidgetPresenter.ChartType.CumulativeGradeLineChart);
        graphPresenter.setChartData(competenceResultsEntityList, 2);
        graphPresenter.showChart();
        view.setCol1(graphPresenter);*/
        CumulativeGradeLineChart chart = new CumulativeGradeLineChart();
        chart.setData(competenceResultsEntityList);
        view.setCol1(chart);


        GraphWidgetPresenter graphPresenter2 = graphWidgetPresenterProvider.get();
        graphPresenter2.setGraphType(GraphWidgetPresenter.ChartType.Gauge);
        graphPresenter2.setChartData(e);
        graphPresenter2.showChart();
        view.setCol2(graphPresenter2);

        GraphWidgetPresenter graphPresenter3 = graphWidgetPresenterProvider.get();
        graphPresenter3.setGraphType(GraphWidgetPresenter.ChartType.Area);
        graphPresenter3.setChartData(e);
        graphPresenter3.showChart();
        view.setCol3(graphPresenter3);

        GraphWidgetPresenter graphPresenter4 = graphWidgetPresenterProvider.get();
        graphPresenter4.setGraphType(GraphWidgetPresenter.ChartType.StackedBar);
        graphPresenter4.setChartData(e);
        graphPresenter4.showChart();
        view.setCol4(graphPresenter4);

        GraphWidgetPresenter graphPresenter5 = graphWidgetPresenterProvider.get();
        graphPresenter5.setGraphType(GraphWidgetPresenter.ChartType.GroupBar);
        graphPresenter5.setChartData(apSummaryEntityList);
        graphPresenter5.showChart();
        view.setCol5(graphPresenter5);

        GraphWidgetPresenter graphPresenter6 = graphWidgetPresenterProvider.get();
        graphPresenter6.setGraphType(GraphWidgetPresenter.ChartType.Pie);
        graphPresenter6.setChartData(e);
        graphPresenter6.showChart();
        view.setCol6(graphPresenter6);

    }
}
