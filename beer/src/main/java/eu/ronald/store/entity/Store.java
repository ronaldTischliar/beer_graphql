package eu.ronald.store.entity;

import eu.ronald.beer.entity.Beer;
import eu.ronald.brewery.entity.Brewery;

import java.util.List;

public record Store(List<Brewery> breweries, List<Beer> beers) {
}
