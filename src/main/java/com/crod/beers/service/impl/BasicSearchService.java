package com.crod.beers.service.impl;

import static com.crod.beers.constants.Constants.MAX_DISTANCE_FROM_ORIGIN;
import static com.crod.beers.constants.Constants.MAX_TOTAL_DISTANCE;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchNode;
import com.crod.beers.data.SearchResult;
import com.crod.beers.model.Beer;
import com.crod.beers.model.Brewery;
import com.crod.beers.model.Geocode;
import com.crod.beers.repository.BreweryRepository;
import com.crod.beers.repository.GeocodeRepository;
import com.crod.beers.service.DistanceCalculatorService;
import com.crod.beers.service.SearchService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Basic search algorithm that always gets the next closest node from the origin
 */
@Service
public class BasicSearchService implements SearchService {

  private final GeocodeRepository geocodeRepository;
  private final BreweryRepository breweryRepository;
  private final DistanceCalculatorService distanceCalculatorService;

  @Autowired
  public BasicSearchService(GeocodeRepository geocodeRepository,
      BreweryRepository breweryRepository, DistanceCalculatorService distanceCalculatorService) {
    this.geocodeRepository = geocodeRepository;
    this.breweryRepository = breweryRepository;
    this.distanceCalculatorService = distanceCalculatorService;
  }

  public SearchResult findBestItinerary(Coordinate origin) {
    // Get an initial list of search nodes candidates based on the allowed max distance
    // Nodes are ordered by proximity to the origin
    List<SearchNode> searchNodesCandidates = getSearchNodesCandidates(origin);

    // Initialise the list that will store the final itinerary as an ordered list of nodes
    List<SearchNode> visitedNodes = new ArrayList<>();
    SearchNode originNode = new SearchNode(null, null, origin, 0);
    visitedNodes.add(originNode);

    double remainingDistance = MAX_TOTAL_DISTANCE;
    SearchNode lastVisitedNode = originNode;

    // Add the candidates one by one until the max distance is reached
    for (SearchNode candidate : searchNodesCandidates) {
      if (Double.compare(candidate.getDistanceFromOrigin(), remainingDistance) < 0) {
        double distanceFromPreviousNode = distanceCalculatorService.distanceBetweenCoordinates(
            lastVisitedNode.getCoordinate(), candidate.getCoordinate());
        candidate.setDistanceFromPreviousNode(distanceFromPreviousNode);
        visitedNodes.add(candidate);
        remainingDistance -= distanceFromPreviousNode;
        lastVisitedNode = candidate;
      }
    }

    // Search complete. Manually add the origin as a final step.
    if (visitedNodes.size() > 1) {
      SearchNode backToOriginNode = new SearchNode(null, null, origin,
          visitedNodes.getLast().getDistanceFromOrigin());
      visitedNodes.add(backToOriginNode);
    }

    return buildSearchResult(visitedNodes);
  }

  private List<SearchNode> getSearchNodesCandidates(Coordinate origin) {
    return geocodeRepository.findAll().stream()
        .map(g -> mapToSearchNodeIfWithinDistance(g, origin))
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(SearchNode::getDistanceFromOrigin))
        .toList();
  }

  private SearchNode mapToSearchNodeIfWithinDistance(Geocode breweryGeocode, Coordinate origin) {
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

  private SearchResult buildSearchResult(List<SearchNode> visitedNodes) {
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
