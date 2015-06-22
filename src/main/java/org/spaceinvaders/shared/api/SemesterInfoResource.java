package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dto.SemesterInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import java.util.List;

import static org.spaceinvaders.shared.api.ApiParameters.SEMESTER_ID;
import static org.spaceinvaders.shared.api.ApiPaths.SEMESTERINFO;

@Path(SEMESTERINFO)
@Produces(MediaType.APPLICATION_JSON)
public interface SemesterInfoResource {
    @GET
    SemesterInfo get(@QueryParam(SEMESTER_ID) int semesterID);

    @GET
    List<SemesterInfo> getAllSemestersInfo();
}
