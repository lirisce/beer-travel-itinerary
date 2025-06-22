package com.crod.beers.service.impl;

import static com.crod.beers.constants.Constants.MAX_TOTAL_DISTANCE;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchNode;
import com.crod.beers.data.SearchResult;
import com.crod.beers.repository.BreweryRepository;
import com.crod.beers.repository.GeocodeRepository;
import com.crod.beers.service.DistanceCalculatorService;
import com.crod.beers.service.SearchService;
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
public class BasicSearchService extends AbstractSearchService implements SearchService {

  @Autowired
  public BasicSearchService(GeocodeRepository geocodeRepository,
      BreweryRepository breweryRepository, DistanceCalculatorService distanceCalculatorService) {
    super(geocodeRepository, breweryRepository, distanceCalculatorService);
  }

  @Override
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
      double distanceFromPreviousNode = distanceCalculatorService.distanceBetweenCoordinates(
          lastVisitedNode.getCoordinate(), candidate.getCoordinate());
      if (distanceFromPreviousNode + candidate.getDistanceFromOrigin() < remainingDistance) {
        candidate.setDistanceFromPreviousNode(distanceFromPreviousNode);
        visitedNodes.add(candidate);
        remainingDistance -= distanceFromPreviousNode;
        lastVisitedNode = candidate;
      }
    }

    // Search complete. We just need to manually add the origin as a final step.
    if (visitedNodes.size() > 1) {
      SearchNode backToOriginNode = new SearchNode(null, null, origin,
          visitedNodes.getLast().getDistanceFromOrigin());
      visitedNodes.add(backToOriginNode);
    }

    return buildSearchResult(visitedNodes);
  }

  private List<SearchNode> getSearchNodesCandidates(Coordinate origin) {
    return findAllGeocodes().stream()
        .map(g -> mapToSearchNodeIfWithinDistance(g, origin))
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(SearchNode::getDistanceFromOrigin))
        .toList();
  }
}
