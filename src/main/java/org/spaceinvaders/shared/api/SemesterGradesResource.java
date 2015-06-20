package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dto.CompetenceEvalResult;
import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.spaceinvaders.shared.api.ApiParameters.SEMESTER_ID;
import static org.spaceinvaders.shared.api.ApiPaths.EVALUATIONS;
import static org.spaceinvaders.shared.api.ApiPaths.SEMESTERGRADES;
import static org.spaceinvaders.shared.api.ApiPaths.COMPETENCE_RESULT;

@Path(SEMESTERGRADES)
@Produces(MediaType.APPLICATION_JSON)
public interface SemesterGradesResource {
    @GET
    @Path(EVALUATIONS)
    TreeMap<String, Evaluation> getAllEvaluations(@QueryParam(SEMESTER_ID) int semesterID);

    @GET
    @Path(COMPETENCE_RESULT)
    List<CompetenceEvalResult> getAllCompetenceEvalResults(@QueryParam(SEMESTER_ID) int semesterID);
}
