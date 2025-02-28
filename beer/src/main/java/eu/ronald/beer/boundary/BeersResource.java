package eu.ronald.beer.boundary;

import eu.ronald.beer.entity.Beer;
import eu.ronald.store.control.StoreService;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


public class BeersResource {

  private final String breweryName;

  private final StoreService store;

  public BeersResource(String breweryName, StoreService store) {
    this.breweryName = breweryName;
    this.store = store;
  }

  @GET
  public Response getBeers() {
    var beers = this.store.beers(breweryName);
    return Response.ok(beers).build();
  }

  @POST
  public Response insert(JsonObject input, @Context UriInfo info) {
    var brewery = this.store.addBeer(Beer.of(input));
    var uri = info.getAbsolutePathBuilder().path("/" + brewery.name()).build();
    return Response.created(uri).build();
  }

  @Path("{name}")
  public BeerResource find(@PathParam("name") String name) {
    return new BeerResource(breweryName, name, store);
  }


}



