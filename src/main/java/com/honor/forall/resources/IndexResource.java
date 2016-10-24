package com.honor.forall.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.honor.forall.views.Index;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class IndexResource {

    @GET
    public Index index() {
        return new Index();
    }
}
