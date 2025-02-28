package eu.ronald.beer.boundary;


import eu.ronald.store.control.StoreService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;

public class BeerResource {
  private final String breweryName;
  private final String name;
  private final StoreService store;

  public BeerResource(String breweryName, String name, StoreService store) {
    this.breweryName = breweryName;
    this.name = name;
    this.store = store;
  }

  @GET
  public Response getBeer() {
    var beer = this.store.beer(this.breweryName, this.name);
    return beer.isPresent() ?
        Response.ok(beer.get()).build() :
        Response.status(Response.Status.NOT_FOUND).build();
  }

  @DELETE
  public Response delete() {
    if (this.store.beer(this.breweryName, this.name).isPresent()) {
      this.store.deleteBeer(this.breweryName, this.name);
      return Response.ok().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }


}
