package eu.ronald.brewery.boundary;

import eu.ronald.brewery.entity.Brewery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@QuarkusTest
public class BreweriesGraphQLIT {

  @Inject
  BreweriesGraphQLClient rut;

  @Test
  @DisplayName("read all breweries")
  void testAllBreweries() {
    var breweries = rut.breweries();
    System.out.println(breweries);
    assertThat(breweries).size().isGreaterThan(0);
  }

  @DisplayName("create read delete brewery")
  @Test
  void crdBrewery() {
    var newBrewery = new Brewery("TestBrewery2", "image", 2023, "History", LocalDateTime.now());
    rut.createBrewery(newBrewery);
    var retrievedBrewery = rut.brewery("TestBrewery2");
    assertThat(retrievedBrewery.name()).isEqualTo("TestBrewery2");

    var deletedBrewery = rut.deleteBrewery("TestBrewery2");
    assertThat(deletedBrewery.name()).isEqualTo("TestBrewery2");
    var notFoundBrewery = rut.brewery("TestBrewery2");
    assertThat(notFoundBrewery).isEqualTo(Brewery.DEFAULT);

  }

  @DisplayName("subscription brewery create")
  @Test
  void subBreweryCreate() {
    var subscription = rut.breweryCreated().subscribe();
    subscription.asStream().forEach(t -> System.out.println("-------------- " + t));

  }


}
