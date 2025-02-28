package eu.ronald.brewery.boundary;

import eu.ronald.Boundary;
import eu.ronald.beer.boundary.BeersResource;
import eu.ronald.brewery.entity.Brewery;
import eu.ronald.store.control.StoreService;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


@Boundary
@Path("breweries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreweriesResource {

  @Inject
  StoreService store;

  @Path("{name}")
  public BreweryResource find(@PathParam("name") String name) {
    return new BreweryResource(name, store);
  }

  @GET
  public Response all() {
    return Response.ok(this.store.allBreweries()).build();
  }


  @POST
  public Response insert(JsonObject input, @Context UriInfo info) {
    var brewery = this.store.addBrewery(Brewery.of(input));
    var uri = info.getAbsolutePathBuilder().path("/" + brewery.name()).build();
    return Response.created(uri).build();
  }

  @Path("{name}/beers")
  public BeersResource findBeers(@PathParam("name") String name) {
    return new BeersResource(name, store);
  }


}
