package org.spaceinvaders.shared.api;

import org.spaceinvaders.shared.dispatch.UserInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.spaceinvaders.shared.api.ApiPaths.*;

@Path(USERINFO)
@Produces(MediaType.APPLICATION_JSON)
public interface UserInfoResource {
    @GET
    UserInfo get();
}
