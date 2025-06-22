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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Search algorithm that, on each step, gets the closest node from the current one
 */
@Service
public class SearchByRelativeProximityService extends AbstractSearchService implements
    SearchService {

  @Autowired
  public SearchByRelativeProximityService(GeocodeRepository geocodeRepository,
      BreweryRepository breweryRepository, DistanceCalculatorService distanceCalculatorService) {
    super(geocodeRepository, breweryRepository, distanceCalculatorService);
  }

  @Override
  public SearchResult findBestItinerary(Coordinate origin) {
    // Get an initial list of search nodes candidates based on the allowed max distance
    // First node is always the origin
    List<SearchNode> searchNodesCandidates = getSearchNodesCandidates(origin);

    // Initialise the list that will store the final itinerary as an ordered list of nodes
    List<SearchNode> visitedNodes = new ArrayList<>();
    visitedNodes.add(searchNodesCandidates.getFirst().clone());

    if (searchNodesCandidates.size() > 1) {
      // Build a matrix of distances between all the search nodes
      double[][] distanceMatrix = distanceCalculatorService.distanceMatrix(searchNodesCandidates);

      // Hashset of matrix indexes already selected as part of the itinerary, for quick reference
      Set<Integer> visitedIndexes = new HashSet<>();

      // Recursively find the next closest node
      visitClosestNode(0, MAX_TOTAL_DISTANCE, searchNodesCandidates, distanceMatrix, visitedNodes,
          visitedIndexes);
    }

    return buildSearchResult(visitedNodes);
  }

  private void visitClosestNode(int currentOriginIndex, double remainingDistance,
      List<SearchNode> searchNodesCandidates, double[][] distanceMatrix,
      List<SearchNode> visitedNodes, Set<Integer> visitedIndexes) {

    int closestUnvisitedNodeIndex = getClosestUnvisitedNodeIndex(currentOriginIndex,
        remainingDistance, distanceMatrix, visitedIndexes);

    // The closest node can either be a brewery (index > 0) or the origin (index = 0)
    // Either way, we add it to the itinerary
    SearchNode closestNode = searchNodesCandidates.get(closestUnvisitedNodeIndex);
    closestNode.setDistanceFromPreviousNode(
        distanceMatrix[currentOriginIndex][closestUnvisitedNodeIndex]);
    visitedNodes.add(closestNode);

    // If the node that was found is a brewery (index > 0), we keep going. Otherwise, we end the recursion.
    if (closestUnvisitedNodeIndex != 0) {
      visitedIndexes.add(closestUnvisitedNodeIndex);
      remainingDistance -= closestNode.getDistanceFromPreviousNode();
      visitClosestNode(closestUnvisitedNodeIndex, remainingDistance, searchNodesCandidates,
          distanceMatrix, visitedNodes, visitedIndexes);
    }
  }

  private int getClosestUnvisitedNodeIndex(int fromIndex, double remainingDistance,
      double[][] distanceMatrix, Set<Integer> visitedIndexes) {
    int closestNodeIndex = 0;
    double closestDistance = MAX_TOTAL_DISTANCE;

    // Loop through all the nodes to find the closest valid one
    for (int toIndex = 1; toIndex < distanceMatrix.length; toIndex++) {
      // Node can't have been visited yet
      if (fromIndex != toIndex && !visitedIndexes.contains(toIndex)) {
        // Distance to reach node + distance to go back to the start must be within the remaining allowance
        double distanceToNode = distanceMatrix[fromIndex][toIndex];
        if (distanceToNode + distanceMatrix[toIndex][0] < remainingDistance) {
          // Check if it's the closest so far
          if (distanceToNode < closestDistance) {
            closestDistance = distanceToNode;
            closestNodeIndex = toIndex;
          }
        }
      }
    }

    return closestNodeIndex;
  }

  private List<SearchNode> getSearchNodesCandidates(Coordinate origin) {
    List<SearchNode> searchNodes = new ArrayList<>();

    SearchNode originNode = new SearchNode(null, null, origin, 0);
    searchNodes.addFirst(originNode);

    findAllGeocodes().stream()
        .map(g -> mapToSearchNodeIfWithinDistance(g, origin))
        .filter(Objects::nonNull)
        .forEach(searchNodes::add);

    return searchNodes;
  }
}
