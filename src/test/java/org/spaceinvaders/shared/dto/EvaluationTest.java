package org.spaceinvaders.shared.dto;

import com.google.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaceinvaders.server.api.SemesterGradesResourceImpl;
import org.spaceinvaders.server.dao.CompetenceEvalResultDao;
import org.spaceinvaders.server.dao.mock.CompetenceEvalResultDaoMock;
import org.spaceinvaders.shared.api.SemesterGradesResource;

import java.util.List;
import java.util.SortedMap;

@RunWith(JukitoRunner.class)
public class EvaluationTest {
    @Inject
    CompetenceEvalResultDao competenceEvalResultDao;

    public SortedMap<String, Evaluation> getEvals() {
        List<CompetenceEvalResult> results = competenceEvalResultDao.getSemesterResults("", 1);
        return Evaluation.getEvaluations(results);
    }

    @Test
    public void testGetEvaluations_EvaluationsAreSet() {
        SortedMap<String, Evaluation> evals = getEvals();

        Assert.assertEquals(evals.size(), 2);
        Assert.assertNotNull(evals.get("Sommatif APP2"));
        Assert.assertNotNull(evals.get("Sommatif APP3"));

        Assert.assertEquals((double) evals.get("Sommatif APP2").getCompetenceEvalResult("GEN402-1").getResultValue(), 74, 0);
    }

    @Test
    public void testGetEvaluations_CompetencesAreSet() {
        SortedMap<String, Evaluation> evals = getEvals();

        Assert.assertNotNull(evals.get("Sommatif APP2").getCompetenceEvalResult("GEN402-1"));
        Assert.assertNotNull(evals.get("Sommatif APP2").getCompetenceEvalResult("GEN501-1"));
        Assert.assertNotNull(evals.get("Sommatif APP2").getCompetenceEvalResult("GEN501-2"));

        Assert.assertNotNull(evals.get("Sommatif APP3").getCompetenceEvalResult("GEN666-1"));
        Assert.assertNotNull(evals.get("Sommatif APP3").getCompetenceEvalResult("GEN666-2"));
    }

    @Test
    public void testGetEvaluations_CompetenceObjectsAreInstantiated() {
        SortedMap<String, Evaluation> evals = getEvals();

        Assert.assertEquals((double) evals.get("Sommatif APP2").getCompetenceEvalResult("GEN402-1").getResultValue(), 74, 0);
    }

    @Test
    public void testGetEvaluations_ExampleIterateInSortedMap() {
        SortedMap<String, Evaluation> evals = getEvals();

        // For each evaluation
        for (String evalLabel : evals.keySet()) {
            Evaluation eval = evals.get(evalLabel);

            // For each competence result
//            TreeMap<String, CompetenceEvalResult> evalResults = eval.getCompetenceEvalResults();
//            for (String competenceLabel : evalResults.keySet()) {
//                Assert.assertNotNull(evalResults.get(competenceLabel));
//            }
        }
    }

    @Test
    public void getAllEvaluations_returnsValidObject() {
        // TODO : test that call to rest : SemesterGradeResource.getAllEvaluations returns valid object
        // Deserialization could be wrong without error message if there are invalid getters/setters name for example
    }

    private void givenDelegate() {
    }

    public static class A extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(CompetenceEvalResultDao.class).to(CompetenceEvalResultDaoMock.class);
            bind(SemesterGradesResource.class).to(SemesterGradesResourceImpl.class);
        }
    }
}