package eu.ronald.beer.boundary;

import eu.ronald.brewery.boundary.BreweryDelegate;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.ronald.brewery.boundary.BreweriesResourceIT.RONALD_BREWERY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@QuarkusTest
public class BeerResourceIT {
  @Inject
  BreweryDelegate breweryDelegate;

  String BEER_RONALD_HELL = """
      {"alcByVol":5.8,
        "brewery":"Ronald","imageLink":"","name":"Ronald Hell 2025"}
      """;

  @Test
  @DisplayName("read all beers from a brewery")
  public void readBeersFromBrewery() {
    var beers = breweryDelegate.beersFromBrewery("Augustiener");
    assertThat(breweryDelegate.lastResponseSuccessful()).isTrue();
    System.out.println(beers.toString());
    assertThat(beers.size()).isGreaterThan(0);
  }

  @DisplayName("create read delete beer from a brewery")
  @Test
  public void createReadDeleteBeer() {
    breweryDelegate.create(RONALD_BREWERY);
    assertThat(breweryDelegate.lastResponseCreated()).isTrue();

    var brewery = breweryDelegate.brewery("Ronald");
    breweryDelegate.createBeer(brewery.getString("name"),BEER_RONALD_HELL);
    assertThat(breweryDelegate.lastResponseCreated()).isTrue();

    var beer = breweryDelegate.beer(brewery.getString("name"),"Ronald Hell 2025");
    assertThat(beer.getString("name")).isEqualTo("Ronald Hell 2025");

    breweryDelegate.deleteBeer(brewery.getString("name"),"Ronald Hell 2025");
    assertThat(breweryDelegate.lastResponseSuccessful()).isTrue();
    breweryDelegate.delete(brewery.getString("name"));
    assertThat(breweryDelegate.lastResponseSuccessful()).isTrue();

  }

}
