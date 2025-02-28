package eu.ronald.brewery.boundary;

import eu.ronald.beer.entity.Beer;
import eu.ronald.brewery.entity.Brewery;
import eu.ronald.store.control.StoreService;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

import static eu.ronald.store.control.StoreService.BRAUEZLOH;

@ApplicationScoped
@GraphQLApi
public class BreweriesGraphQL {

  BroadcastProcessor<Brewery> processor = BroadcastProcessor.create();

  @Inject
  StoreService storeService;

  @Query("breweries")
  public List<Brewery> allBreweries() {
    return storeService.allBreweries();
  }

  @Query("brewery")
  public Brewery getBrewery(@DefaultValue(BRAUEZLOH) String name) {
    return storeService.brewery(name).orElse(Brewery.DEFAULT);
  }

  public List<Beer> beers(@Source Brewery brewery) {
    return storeService.beers(brewery);
  }

  @Mutation
  public Brewery createBrewery(Brewery brewery) {
    storeService.addBrewery(brewery);
    processor.onNext(brewery);
    return brewery;
  }

  @Mutation
  public Brewery deleteBrewery(String name) {
    return storeService.deleteBrewery(name);
  }

  @Subscription
  public Multi<Brewery> breweryCreated() {
    return processor;
  }


}
