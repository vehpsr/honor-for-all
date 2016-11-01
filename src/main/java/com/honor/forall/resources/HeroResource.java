package com.honor.forall.resources;

import static com.honor.forall.HonorForAllConstants.HERO_RESOURCE_URI;
import static com.honor.forall.HonorForAllConstants.ROOT_URI;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.codahale.metrics.annotation.Timed;
import com.honor.forall.enums.HeroField;
import com.honor.forall.exception.response.ErrorResponse;
import com.honor.forall.model.base.Hero.Class;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.HeroesVm;
import com.honor.forall.resources.params.HeroClassParams;
import com.honor.forall.resources.params.HeroFieldParams;
import com.honor.forall.resources.params.IntParams;
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
    @Timed
    public Response getHeroes(
            @QueryParam("heroIds") @DefaultValue("") IntParams heroIdParams,
            @QueryParam("classes") @DefaultValue("") HeroClassParams heroClassParams,
            @QueryParam("include") @DefaultValue("hero") HeroFieldParams heroFieldParams
            ) {
        Set<Integer> heroIds = heroIdParams.get();
        Set<Class> heroClasses = heroClassParams.get();
        Set<HeroField> heroFields = heroFieldParams.get();
        HeroesVm heroes = heroService.getHeroes(heroIds, heroClasses, heroFields);
        return Response.ok(heroes).build();
    }

    @GET
    @Path("/{heroId}")
    @Timed
    public Response getHero(
            @PathParam("heroId") IntParams heroIdParams
            ) {
        int heroId = heroIdParams.single().get();
        HeroVm hero = heroService.getHero(heroId);
        if (hero == null) {
            return ErrorResponse.build(HttpStatus.NOT_FOUND_404, "Fail to find Hero with id: " + heroId);
        }
        return Response.ok(hero).build();
    }

}
