package com.honor.forall.resources;

import static com.honor.forall.HonorForAllConstants.LOGIN_RESOURCE_URI;
import static com.honor.forall.HonorForAllConstants.ROOT_URI;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import com.codahale.metrics.annotation.Timed;
import com.honor.forall.exception.response.ErrorResponse;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.service.LoginService;

@Path(ROOT_URI + LOGIN_RESOURCE_URI)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Timed
    public Response login(
            @Context UriInfo uriInfo,
            @QueryParam("login") String login,
            @QueryParam("password") String password) {

        if (StringUtils.isNotEmpty(login) || StringUtils.isNotEmpty(password)) {
            return ErrorResponse.build(HttpStatus.BAD_REQUEST_400, "Login is not yet supported. Guest users only.");
        }

        AuthToken token = loginService.guestAuthToken();
        URI uri = uriInfo.getAbsolutePathBuilder() // TODO add verify endpoint
                .path("verify/" + token.getType().toString() + "/" + token.getToken().toString()).build();
        return Response.created(uri).entity(token).build();
    }

}
