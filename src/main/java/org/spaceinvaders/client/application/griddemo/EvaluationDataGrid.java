package org.spaceinvaders.client.application.griddemo;

import com.google.gwt.core.client.GWT;

import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.SemesterInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/26/2015
 *
 * @author antoine
 */
public class EvaluationDataGrid implements GridData<SemesterInfo, CompetenceEvalResult> {

    private SemesterInfo semesterInfo;
    private List<String> apLabels;
    private List<String> competenceLabels;

    private Evaluation evaluation;
    private List<CompetenceEvalResult> competenceEvalResult;

    public EvaluationDataGrid() {

    }

    public EvaluationDataGrid(SemesterInfo semesterInfo) {
        this.semesterInfo = semesterInfo;
    }

    public List<String> getApLabels() {
        return apLabels;
    }

    public void setApLabels(List<String> apLabel) {
        this.apLabels = apLabels;
    }

    public List<String> getCompetenceLabels() {
        return competenceLabels;
    }

    public void setCompetenceLabels(List<String> competenceLabels) {
        this.competenceLabels = competenceLabels;
    }

    public SemesterInfo getSemesterInfo() {
        return semesterInfo;
    }

    public void setSemesterInfo(SemesterInfo semesterInfo) {
        this.semesterInfo = semesterInfo;
    }

    // --------------- Competence Result --------------- //

    public List<CompetenceEvalResult> getCompetenceEvalResult() {
        return competenceEvalResult;
    }

    public void setCompetenceEvalResult(List<CompetenceEvalResult> competenceEvalResult) {
        GWT.log("setCompetenceEvalResult " + competenceEvalResult.get(0).getCompetenceLabel());
        this.competenceEvalResult = competenceEvalResult;
    }

    public Evaluation getEvaluationLabel() {
        return evaluation;
    }


    public void setEvaluationLabel(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public List<Competence> getAllCompetences() {
        return semesterInfo.getCompetences();
    }

    @Override
    public List<CompetenceEvalResult> getAllRow() {
        return competenceEvalResult;
    }
}
