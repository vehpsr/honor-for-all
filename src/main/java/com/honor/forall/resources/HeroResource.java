package com.honor.forall.resources;

import static com.honor.forall.HonorForAllConstants.HERO_RESOURCE_URI;
import static com.honor.forall.HonorForAllConstants.ROOT_URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.honor.forall.model.vm.HeroesVm;
import com.honor.forall.service.HeroService;

@Path(ROOT_URI + HERO_RESOURCE_URI)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HeroResource {

    private final HeroService heroService;

    public HeroResource(HeroService heroService) {
        this.heroService = heroService;
    }

    @GET
    public Response getHeroes() {
        HeroesVm heroes = heroService.getHeroes();
        return Response.ok(heroes).build();
    }
}
