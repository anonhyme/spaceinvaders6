package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static org.spaceinvaders.shared.api.ApiParameters.AP_ID;
import static org.spaceinvaders.shared.api.ApiParameters.SEMESTER_ID;
import static org.spaceinvaders.shared.api.ApiPaths.ALL;
import static org.spaceinvaders.shared.api.ApiPaths.AP;
import static org.spaceinvaders.shared.api.ApiPaths.EVALUATIONS;

@Path(EVALUATIONS)
@Produces(MediaType.APPLICATION_JSON)
public interface EvaluationResource {
    @GET
    @Path(EVALUATIONS + ALL)
    TreeMap<String, Evaluation> getAllEvaluations(@QueryParam(SEMESTER_ID) int semesterID);

    @GET
    @Path(EVALUATIONS + AP)
    TreeMap<String, Evaluation> getApEvaluations(@QueryParam(SEMESTER_ID) int semesterID, @QueryParam(AP_ID) int apID);
}
