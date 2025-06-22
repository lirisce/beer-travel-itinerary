package com.crod.beers.service.impl;

import com.crod.beers.data.Coordinate;
import com.crod.beers.service.DistanceCalculatorService;
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
}
