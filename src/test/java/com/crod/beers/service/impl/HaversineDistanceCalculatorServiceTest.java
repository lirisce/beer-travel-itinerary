package com.crod.beers.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.crod.beers.data.Coordinate;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class HaversineDistanceCalculatorServiceTest {

  HaversineDistanceCalculatorService service = new HaversineDistanceCalculatorService();

  @Test
  void distanceBetweenCoordinates() {
    Coordinate whiteHouseUS = new Coordinate(new BigDecimal("38.898"), new BigDecimal("77.037"));
    Coordinate eiffelTowerFR = new Coordinate(new BigDecimal("48.858"), new BigDecimal("2.294"));
    double expectedDistance = 6161.6;

    double result = service.distanceBetweenCoordinates(whiteHouseUS, eiffelTowerFR);

    assertTrue(Double.compare(result, expectedDistance) < 0.001);
  }
}
