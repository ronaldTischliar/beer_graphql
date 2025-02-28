package eu.ronald.brewery.boundary;

import eu.ronald.store.control.StoreService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Produces(MediaType.APPLICATION_JSON)
public class BreweryResource {
  String name;
  StoreService store;

  public BreweryResource(String name, StoreService store) {
    this.name = name;
    this.store = store;
  }

  @GET
  public Response getBrewery() {
    var brewery = this.store.brewery(name);
    return brewery.isPresent() ?
        Response.ok(brewery.get()).build() :
        Response.status(Response.Status.NOT_FOUND).build();
  }

  @DELETE
  public Response delete() {
    if (this.store.brewery(this.name).isPresent()) {
      this.store.deleteBrewery(this.name);
      return Response.ok().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

}
