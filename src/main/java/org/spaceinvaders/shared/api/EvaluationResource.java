package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static org.spaceinvaders.shared.api.ApiParameters.SEMESTER_ID;
import static org.spaceinvaders.shared.api.ApiParameters.AP_ID;
import static org.spaceinvaders.shared.api.ApiPaths.COMPETENCE_RESULTS;
import static org.spaceinvaders.shared.api.ApiPaths.EVALUATIONS;
import static org.spaceinvaders.shared.api.ApiPaths.SEMESTERGRADES;
import static org.spaceinvaders.shared.api.ApiPaths.AP_RESULTS;

@Path(EVALUATIONS)
@Produces(MediaType.APPLICATION_JSON)
public interface EvaluationResource {
    @GET
    TreeMap<String, Evaluation> getAllEvaluations(@QueryParam(SEMESTER_ID) int semesterID);
}
