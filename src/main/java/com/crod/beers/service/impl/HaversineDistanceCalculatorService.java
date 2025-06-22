package com.crod.beers.service.impl;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchNode;
import com.crod.beers.service.DistanceCalculatorService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Uses the Haversine Formula to calculate the distance between two coordinates.
 */
@Service
public class HaversineDistanceCalculatorService implements DistanceCalculatorService {

  final int EARTH_RADIUS_KM = 6371;

  @Override
  public double distanceBetweenCoordinates(Coordinate c1, Coordinate c2) {
    // Converting from degrees to radians before applying sin/cos
    double lat1Radians = Math.toRadians(c1.latitude().doubleValue());
    double lat2Radians = Math.toRadians(c2.latitude().doubleValue());
    double deltaLatRadians = Math.toRadians(
        c2.latitude().doubleValue() - c1.latitude().doubleValue());
    double deltaLongRadians = Math.toRadians(
        c2.longitude().doubleValue() - c1.longitude().doubleValue());

    double a = haversine(deltaLatRadians) +
        Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(deltaLongRadians);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c;
  }

  private double haversine(double value) {
    return Math.pow(Math.sin(value / 2), 2);
  }

  @Override
  public double[][] distanceMatrix(List<SearchNode> nodes) {
    double[][] matrix = new double[nodes.size()][nodes.size()];

    for (int row = 0; row < nodes.size(); row++) {
      for (int column = 0; column < nodes.size(); column++) {
        if (row != column) {
          Coordinate from = nodes.get(row).getCoordinate();
          Coordinate to = nodes.get(column).getCoordinate();
          double distance = distanceBetweenCoordinates(from, to);
          matrix[row][column] = distance;
          matrix[column][row] = distance;
        }
      }
    }

    return matrix;
  }
}
