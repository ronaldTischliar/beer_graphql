package eu.ronald.brewery.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class BreweryDelegate {
  @Inject
  @RestClient
  BreweriesResourceClient rut;


  Response lastResponse;
  public JsonArray allBreweries() {
    this.lastResponse = this.rut.allBreweries();
    return this.lastResponse.readEntity(JsonArray.class);
  }
  public void delete(String name) {
    this.lastResponse = this.rut.delete(name);
  }
  public void create(String input) {
    this.lastResponse = this.rut.create(input);
  }

  public JsonObject brewery(String name) {
    this.lastResponse = this.rut.brewery(name);
    return this.lastResponse.readEntity(JsonObject.class);
  }

  public void createBeer(String breweryName, String input) {

    this.lastResponse = this.rut.createBeer(breweryName,input);
    System.out.println(input);
  }



  public boolean isSuccessful(Response response) {
    return response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL;
  }

  public boolean lastResponseWas(int status) {
    return this.lastResponse.getStatus() == status;
  }

  public boolean lastResponseSuccessful() {
    return isSuccessful(this.lastResponse);
  }

  public boolean lastResponseConflict() {
    return lastResponseWas(409);
  }

  public boolean lastResponseInvalid() {
    return lastResponseWas(400);
  }
  public boolean lastResponseNotFound() {
    return lastResponseWas(404);
  }
  public boolean lastResponseCreated() {
    return lastResponseWas(Response.Status.CREATED.getStatusCode());
  }
  public boolean lastResponseServerException() {
    return lastResponseWas(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  public JsonArray beersFromBrewery(String nameBrewery) {
    this.lastResponse = this.rut.allBeers(nameBrewery);
    return this.lastResponse.readEntity(JsonArray.class);
  }


  public JsonObject beer(String breweryName, String name) {
    this.lastResponse = this.rut.beer(breweryName,name);
    return this.lastResponse.readEntity(JsonObject.class);
  }

  public void deleteBeer(String breweryName, String name) {
    this.lastResponse = this.rut.deleteBeer(breweryName,name);
  }
}
