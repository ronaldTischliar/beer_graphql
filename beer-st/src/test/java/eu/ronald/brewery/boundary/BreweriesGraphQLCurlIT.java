package eu.ronald.brewery.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@QuarkusTest
public class BreweriesGraphQLCurlIT {

  @Inject
  @ConfigProperty(name = "quarkus.smallrye-graphql-client.beer_graphql.url")
  String GRAPHQL_URL;

  Response runGraphQL(String query) {
    var response = given().contentType(ContentType.JSON).body(query).when().post(GRAPHQL_URL).then().assertThat().statusCode(200).and().extract().response();
    response.getBody().prettyPrint();
    return response;
  }

  @DisplayName("read all breweries")
  @Test
  void allBreweries() {
    var response = runGraphQL("""
         {"query":   "query all {
                        breweries {
                          name
                          createTime
                          beers {
                            name
                            alcByVol
                          }
                        }}"}
        """);
    var breweries = response.jsonPath().getList("data.breweries");
    breweries.forEach(System.out::println);
  }

  @DisplayName("create Bad Brewery")
  @Test
  void createBadBrewery() {
    var response = runGraphQL("""
          {"query":"mutation createBrewery($brewery: BreweryInput) {
           createBrewery(brewery: $brewery) {
                      name
                      yearOfFounding
                      history
                      BAD_REQUEST
                    }
                    }
        ",
        "variables": {
                "brewery": {
                      "name": "Ronald",
                      "imageLink": "image",
                      "yearOfFounding": 2023,
                      "history": "History",
                      "createTime": "2025-02-28T09:49:16.363656"
                    }
        }}
        
        """);
    var errors = response.jsonPath().getList("errors");
    assertThat(errors.size()).isEqualTo(1);
    assertThat(errors.getFirst().toString()).contains("BAD_REQUEST");

  }

  @DisplayName("create  Brewery")
  @Test
  void createBrewery() {
    var response = runGraphQL("""
          {"query":"mutation createBrewery($brewery: BreweryInput) {
           createBrewery(brewery: $brewery) {
                      name
                      yearOfFounding
                      history
                    }
                    }
        ",
        "variables": {
                "brewery": {
                      "name": "Ronald",
                      "imageLink": "image",
                      "yearOfFounding": 2023,
                      "history": "History",
                      "createTime": "2025-02-28T09:49:16.363656"
                    }
        }}
        
        """);
    var pathEvaluator = response.jsonPath();
    assertThat(pathEvaluator.getString("data.createBrewery.name")).isEqualTo("Ronald");


  }

  @DisplayName("delete Brewery")
  @Test
  void deleteBrewery() {
    var response = runGraphQL("""
         {"query":"mutation deleteBrewery($name: String) {
          deleteBrewery(name: $name) {
            name
            yearOfFounding
            createTime} }","variables":{"name":"Ronald"}
            }
        """);
    var pathEvaluator = response.jsonPath();
    assertThat(pathEvaluator.getString("data.deleteBrewery.name")).isEqualTo("Ronald");
  }


}
