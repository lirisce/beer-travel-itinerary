package com.crod.beers.service;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchNode;
import java.util.List;

public interface DistanceCalculatorService {

  /**
   * Calculates the distance between two coordinates. The algorithm used depends on the
   * implementation.
   *
   * @param c1 first coordinate
   * @param c2 second coordinate
   * @return the distance value, in km
   */
  double distanceBetweenCoordinates(Coordinate c1, Coordinate c2);

  /**
   * Builds a matrix of distances between all the nodes provided.
   *
   * @param nodes the list of nodes
   * @return the matrix of distances
   */
  double[][] distanceMatrix(List<SearchNode> nodes);
}
