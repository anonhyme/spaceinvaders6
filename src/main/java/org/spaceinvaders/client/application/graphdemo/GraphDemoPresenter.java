


package org.spaceinvaders.client.application.graphdemo;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.CumulativeLineChart;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.EvaluationResultsChart;
import org.spaceinvaders.client.application.ui.graph.GwtCharts.SemesterResultsChart;
import org.spaceinvaders.client.application.ui.graph.gwtchartwidget.GwtChartWidgetPresenter;
import org.spaceinvaders.client.place.NameTokens;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

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
    Provider<GwtChartWidgetPresenter> gwtChartWidgetPresenterProvider;

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

        List<CompetenceEvalResult> competenceResultsEntityList= new ArrayList<CompetenceEvalResult>();

       /* for (int i =0; i<6; i++) {
            ApSummaryEntity e = new ApSummaryEntity();
            e.setApName("GIF50" + i);
            e.setApResult((int) Math.round(Math.random() * 100));
            e.setApAverage((int) Math.round(Math.random() * 100));
            e.setApMax(100);
            apSummaryEntityList.add(e);
        }
        ApSummaryEntity e = new ApSummaryEntity();
        e.setApName("Gel501");*/

        for (int i =0; i<6; i++) {
            CompetenceEvalResult ce = new CompetenceEvalResult();
            ce.setEvalLabel("Rapport " + i);
            ce.setCourseLabel("GEN500");
            ce.setCompetenceLabel("1");
            ce.setResultValue((int) Math.round(Math.random() * 100));
            ce.setAvgResultValue((int) Math.round(Math.random() * 100));
            ce.setMaxResultValue(100);
            competenceResultsEntityList.add(ce);
        }

        for (int i =0; i<6; i++) {
            CompetenceEvalResult ce = new CompetenceEvalResult();
            ce.setEvalLabel("Rapport " + i);
            ce.setCourseLabel("GEN500");
            ce.setCompetenceLabel("2");
            ce.setResultValue((int) Math.round(Math.random() * 100));
            ce.setAvgResultValue((int) Math.round(Math.random() * 100));
            ce.setMaxResultValue(100);
            competenceResultsEntityList.add(ce);
        }

        for (int i =0; i<6; i++) {
            CompetenceEvalResult ce = new CompetenceEvalResult();
            ce.setEvalLabel("Rapport " + i);
            ce.setCourseLabel("GEN501");
            ce.setCompetenceLabel("2");
            ce.setResultValue((int) Math.round(Math.random() * 100));
            ce.setAvgResultValue((int) Math.round(Math.random() * 100));
            ce.setMaxResultValue(100);
            competenceResultsEntityList.add(ce);
        }

        view.setClassProgress(60.9);
        view.setStudentProgress(50);
        String [] colors = {"#FF0000", "#00FF00", "#0000FF"};
        final GwtChartWidgetPresenter p4 = gwtChartWidgetPresenterProvider.get();
        p4.setChart(new CumulativeLineChart());
        p4.setChartData(competenceResultsEntityList);
        p4.setChartColors(colors );
        view.setCol4(p4);

        final GwtChartWidgetPresenter p5 = gwtChartWidgetPresenterProvider.get();
        p5.setChart(new EvaluationResultsChart());
        p5.setChartData(competenceResultsEntityList);
        view.setCol5(p5);

        final GwtChartWidgetPresenter p6 = gwtChartWidgetPresenterProvider.get();
        p6.setChart(new SemesterResultsChart());
        p6.setChartData(competenceResultsEntityList);
        view.setCol6(p6);


        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                p4.loadChart();
                p5.loadChart();
                p6.loadChart();
            }
        });

    }
}
