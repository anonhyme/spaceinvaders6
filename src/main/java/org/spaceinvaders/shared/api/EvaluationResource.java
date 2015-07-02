package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dto.Ap;
import org.spaceinvaders.shared.dto.Evaluation;
import org.spaceinvaders.shared.dto.Result;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.TreeMap;

import static org.spaceinvaders.shared.api.ApiParameters.SEMESTER_ID;
import static org.spaceinvaders.shared.api.ApiParameters.AP_ID;
import static org.spaceinvaders.shared.api.ApiPaths.EVALUATIONS;
import static org.spaceinvaders.shared.api.ApiPaths.ALL;
import static org.spaceinvaders.shared.api.ApiPaths.AP;

@Path(EVALUATIONS)
@Produces(MediaType.APPLICATION_JSON)
public interface EvaluationResource {
    @GET
    @Path(ALL)
    TreeMap<String, Evaluation> getAllEvaluations(@QueryParam(SEMESTER_ID) int semesterID);

    @GET
    @Path(AP)
    TreeMap<String, Evaluation> getApEvaluations(@QueryParam(SEMESTER_ID) int semesterID, @QueryParam(AP_ID) int apID);
}
