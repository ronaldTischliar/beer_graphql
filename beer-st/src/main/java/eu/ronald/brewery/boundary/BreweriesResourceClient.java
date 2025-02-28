package eu.ronald.brewery.boundary;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("breweries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "beer_uri")
public interface BreweriesResourceClient {

  @POST
  Response create(String json);

  @GET
  Response allBreweries();

  @Path("{name}")
  @DELETE
  Response delete(@PathParam("name") String name);


  @GET
  @Path("{name}")
  Response brewery(@PathParam("name") String name);


  @POST
  @Path("{name}/beers")
  Response createBeer(@PathParam("name") String name,String json);

  @GET
  @Path("{name}/beers")
  Response allBeers(@PathParam("name") String name);


  @Path("{name}/beers/{beerName}")
  @DELETE
  Response deleteBeer(@PathParam("name") String name, @PathParam("beerName") String beerName);

  @Path("{name}/beers/{beerName}")
  @GET
  Response beer(@PathParam("name") String name, @PathParam("beerName") String beerName);


}

