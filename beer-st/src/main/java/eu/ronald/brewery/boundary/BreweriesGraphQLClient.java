package eu.ronald.brewery.boundary;

import eu.ronald.brewery.entity.Beer;
import eu.ronald.brewery.entity.Brewery;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLClientApi(configKey = "beer_graphql")
public interface BreweriesGraphQLClient {
  @Query("breweries")
  List<Brewery> breweries();

  @Query("brewery")
  Brewery brewery(String name);

  @Query
  List<Beer> beers(Brewery brewery);

  @Mutation
  Brewery createBrewery(Brewery brewery);

  @Mutation
  Brewery deleteBrewery(String name);

  @Subscription("breweryCreated")
  Multi<Brewery> breweryCreated();
}

