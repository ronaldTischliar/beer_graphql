package eu.ronald.brewery.boundary;

import eu.ronald.beer.entity.Beer;
import eu.ronald.brewery.entity.Brewery;
import eu.ronald.store.control.StoreService;
import graphql.GraphQL;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import io.quarkus.logging.Log;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

import static eu.ronald.store.control.StoreService.BRAUEZLOH;

@ApplicationScoped
@GraphQLApi
public class BreweriesGraphQL {

  @Inject
  Context context;


  BroadcastProcessor<Brewery> processor = BroadcastProcessor.create();

  @Inject
  StoreService storeService;

  public GraphQLSchema.Builder leakyAbstraction(@Observes GraphQLSchema.Builder builder) {
    Log.info(">>>>>>> Here we leak while building the schema");
    return builder;
  }

  public GraphQL.Builder leakyAbstraction(@Observes GraphQL.Builder builder) {
    Log.info(">>>>>>> Here we leak while building graphQL");
    return builder;
  }

  private void interceptQuery() {
    DataFetchingEnvironment dfe = context.unwrap(DataFetchingEnvironment.class);
    var document = dfe.getDocument();
    var definitions = document.getDefinitionsOfType(OperationDefinition.class);
    if (!definitions.isEmpty()) {
      var operation = definitions.getFirst();
      var selections = operation.getSelectionSet().getSelections();
      var metadata = new StringBuilder();
      for (Selection selection : selections) {
        if (selection instanceof Field) {
          Field field = (Field) selection;
          String fieldName = field.getName();
          metadata.append("Field: ").append(fieldName).append("\n");
          Log.info(field.getSelectionSet());
          // You can add more logic to extract arguments, aliases, etc.
        }
      }
    }
  }

  @Query("breweries")
  public List<Brewery> allBreweries() {
    interceptQuery();
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
