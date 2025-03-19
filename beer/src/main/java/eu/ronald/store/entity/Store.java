package eu.ronald.store.entity;

import eu.ronald.beer.entity.Beer;
import eu.ronald.brewery.entity.Brewery;

import java.util.Queue;

public record Store(Queue<Brewery> breweries, Queue<Beer> beers) {
}
