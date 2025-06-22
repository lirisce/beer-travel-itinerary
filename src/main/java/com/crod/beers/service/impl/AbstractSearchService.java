package com.crod.beers.service.impl;

import static com.crod.beers.constants.Constants.MAX_DISTANCE_FROM_ORIGIN;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchNode;
import com.crod.beers.data.SearchResult;
import com.crod.beers.model.Beer;
import com.crod.beers.model.Brewery;
import com.crod.beers.model.Geocode;
import com.crod.beers.repository.BreweryRepository;
import com.crod.beers.repository.GeocodeRepository;
import com.crod.beers.service.DistanceCalculatorService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSearchService {

  protected final GeocodeRepository geocodeRepository;
  protected final BreweryRepository breweryRepository;
  protected final DistanceCalculatorService distanceCalculatorService;

  protected AbstractSearchService(GeocodeRepository geocodeRepository,
      BreweryRepository breweryRepository, DistanceCalculatorService distanceCalculatorService) {
    this.geocodeRepository = geocodeRepository;
    this.breweryRepository = breweryRepository;
    this.distanceCalculatorService = distanceCalculatorService;
  }

  protected List<Geocode> findAllGeocodes() {
    return geocodeRepository.findAll();
  }

  protected SearchNode mapToSearchNodeIfWithinDistance(Geocode breweryGeocode, Coordinate origin) {
    Coordinate breweryCoordinate = new Coordinate(breweryGeocode.getLatitude(),
        breweryGeocode.getLongitude());
    double distanceFromOrigin = distanceCalculatorService.distanceBetweenCoordinates(origin,
        breweryCoordinate);

    if (Double.compare(distanceFromOrigin, MAX_DISTANCE_FROM_ORIGIN) < 0) {
      return new SearchNode(breweryGeocode.getBrewery().getId(),
          breweryGeocode.getBrewery().getName(), breweryCoordinate, distanceFromOrigin);
    }
    return null;
  }

  protected SearchResult buildSearchResult(List<SearchNode> visitedNodes) {
    // Worst case scenario, the origin is the only node in the result
    if (visitedNodes.size() == 1) {
      return new SearchResult(visitedNodes, BigDecimal.ZERO, Collections.emptyList());
    }

    // Calculate total distance travelled
    double totalDistance = visitedNodes.stream().map(SearchNode::getDistanceFromPreviousNode)
        .mapToDouble(Double::doubleValue).sum();
    BigDecimal totalDistanceRoundedUp = new BigDecimal(totalDistance).setScale(2, RoundingMode.UP);

    // Retrieve info about beers collected
    List<Brewery> visitedBreweries = breweryRepository.findAllById(
        visitedNodes.stream().map(SearchNode::getBreweryId).toList());
    List<String> beerNames = visitedBreweries.stream().flatMap(g -> g.getBeers().stream())
        .map(Beer::getName).toList();

    return new SearchResult(visitedNodes, totalDistanceRoundedUp, beerNames);
  }
}
