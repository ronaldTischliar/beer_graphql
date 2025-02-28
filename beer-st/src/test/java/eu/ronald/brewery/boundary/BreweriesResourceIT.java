package eu.ronald.brewery.boundary;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@QuarkusTest
public class BreweriesResourceIT {
  @Inject
  BreweryDelegate breweryDelegate;

  String RONALD_BAD_BREWERY = """
      hhhhh
      """;
  public static String RONALD_BREWERY = """
      {
      
           "history": "new Beer Brewery, founded in 2025",
           "imageLink": "",
           "name": "Ronald",
           "yearOfFounding": 2025
         }
      """;

  @Test
  @DisplayName("read all breweries")
  void readAllBreweries() {
    var breweries = breweryDelegate.allBreweries();
    System.out.println(breweries.toString());
    assertThat(breweries.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("bad create brewery")
  void createBadBrewery() {
    breweryDelegate.create(RONALD_BAD_BREWERY);
    assertThat(breweryDelegate.lastResponseServerException()).isTrue();
  }


  @Test
  @DisplayName("create read delete brewery")
  void crdBrewery() {
    breweryDelegate.create(RONALD_BREWERY);
    assertThat(breweryDelegate.lastResponseCreated()).isTrue();
    var brewery = breweryDelegate.brewery("Ronald");
    assertThat(brewery.getString("name")).isEqualTo("Ronald");
    assertThat(brewery.getInt("yearOfFounding")).isEqualTo(2025);
    breweryDelegate.delete("Ronald");
    assertThat(breweryDelegate.lastResponseSuccessful()).isTrue();
  }
}
