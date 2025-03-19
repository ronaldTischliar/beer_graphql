package eu.ronald.store.control;

import eu.ronald.beer.entity.Beer;
import eu.ronald.brewery.entity.Brewery;
import eu.ronald.store.entity.Store;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@ApplicationScoped
public class StoreService {
  public static final String AUGUSTIENER = "Augustiener";
  public static final String BRAUEZLOH = "Bräu z'Loh";
  public static final String RONALD = "Ronald";
  final Store store = new Store(new ConcurrentLinkedQueue<>(), new ConcurrentLinkedQueue<>());

  @PostConstruct
  public void init() {
    store.breweries().add(new Brewery(AUGUSTIENER, "", 1328, """
        In 1328, the Augustinian monks began brewing beer in their Augustinian monastery near Munich Cathedral. For almost 500 years, the friars brewed their Augustinian beer directly in the monastery and sold it in the monastery tavern. In 1803, the state took over the Augustinian monastery in the course of secularization and the brewery was privatized. Anton Wagner, a brewer from Freising, took over the business in 1829, and it has existed as a middle-class private brewery ever since. His son Josef Wagner had a modern new building constructed on what was then the outskirts of the city in Landsberger Strasse, to which the brewery moved in 1885.
        Here we brew our Augustiner beer to this day. Our brewery building was severely damaged during the Second World War and rebuilt according to the old model. Today, the facade of the elongated red brick building is a listed building. During its almost 700-year history, the Augustiner Brewery has experienced many expansions and innovations - while always maintaining its corporate philosophy: We are concerned with the outstanding quality of our beer, with tradition and with our Munich roots.
        """.replaceAll("\\n", ""), LocalDateTime.now()));
    store.breweries().add(new Brewery(BRAUEZLOH, "", 1928, """
        Rings um Äcker und Wiesen und Wälder, eine Lichtung im Wald, bei den Kelten ein heiliger Hain. Hier in dieser Einöd brauen wir unser Bier, zwischen Dorfen und Mühldorf, zwischen Wasserburg und Landshut. Und nur über einen steilen, schmalen Hohlweg kommt man zu uns in die Brauerei und ins Bräustüberl.
        In Loh bei Dorfen wird seit 1928 Bier gebraut. Nach einer Anzeige wegen Schwarzbrauens gründete der Urgroßvater der heutigen Bräuin aus Protest die Brauerei. Hier wuchs auch der Schriftsteller Georg Lohmeier auf – Autor des „Königlich Bayerischen Amtsgerichts“.
        Heute leitet die Babsi die Familienbrauerei. Sie ist Bräuin in 4. Generation und braut das gute Loher Bier nach dem Bayerischen Reinheitsgebot von 1516. Die Kunden schätzen die hohe Braukunst der Bräuin und die feinen Bierspezialitäten aus Loh.
        """.replaceAll("\\n", ""), LocalDateTime.now()));
    store.beers().add(new Beer(AUGUSTIENER, "Augustiener Lagerbier Hell", "", 5.2));
    store.beers().add(new Beer(AUGUSTIENER, "Augustiener Dunkel", "", 5.6));

    store.beers().add(new Beer(BRAUEZLOH, "Hell Export", "", 5.2));
    store.beers().add(new Beer(BRAUEZLOH, "Dunkel", "", 5.4));
    store.beers().add(new Beer(RONALD, "Ronald Hell", "", 5.3));
    store.beers().add(new Beer(RONALD, "Ronald Dunkel", "", 5.8));

  }


  public List<Brewery> allBreweries() {
    return store.breweries().stream().sorted(Comparator.comparing(Brewery::name)).toList();
  }

  public Optional<Brewery> brewery(String name) {
    return store.breweries().stream().filter(t -> t!=null && t.name().equals(name)).
        findAny();
  }


  public Optional<Beer> beer(String breweryName, String name) {
    return beers(breweryName).stream().filter(t -> t.name().equals(name)).
        findAny();
  }

  public List<Beer> beers(String breweryName) {
    return store.beers().stream().filter(t -> t.brewery().equals(breweryName)).toList();
  }

  public List<Beer> beers(Brewery brewery) {
    return store.beers().stream().filter(t -> t.brewery().equals(brewery.name())).toList();
  }


  public Brewery addBrewery(Brewery brewery) {
    if(brewery(brewery.name()).isPresent()) {
     updateBrewery(brewery);
    } else {
      store.breweries().add(brewery);
    }
    return brewery;
  }

  private void updateBrewery(Brewery brewery) {
    store.breweries().removeIf(t -> t.name().equalsIgnoreCase(brewery.name()));
    store.breweries().add(brewery);
  }

  public Brewery deleteBrewery(String name) {
    store.beers().removeIf(t -> t == null || t.brewery().equalsIgnoreCase(name));
    var result = this.brewery(name);
    store.breweries().removeIf(t -> t.name().equalsIgnoreCase(name));
    return result.orElse(Brewery.DEFAULT);
  }

  public Beer addBeer(Beer beer) {
    store.beers().add(beer);
    return beer;
  }

  public Beer deleteBeer(String breweryName, String name) {
    var result = this.beer(breweryName, name);
    store.beers().removeIf(t -> t.name().equalsIgnoreCase(name));
    return result.orElse(Beer.DEFAULT);
  }


}
