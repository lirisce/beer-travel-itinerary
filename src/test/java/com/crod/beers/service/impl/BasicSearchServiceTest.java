package com.crod.beers.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.crod.beers.configuration.MockJpaTestConfig;
import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchResult;
import com.crod.beers.model.Beer;
import com.crod.beers.model.Brewery;
import com.crod.beers.model.Geocode;
import com.crod.beers.repository.GeocodeRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MockJpaTestConfig.class)
public class BasicSearchServiceTest {

  private static final Coordinate ORIGIN = new Coordinate(new BigDecimal("49.735"),
      new BigDecimal("10.137"));

  @Autowired
  BasicSearchService searchService;
  @Autowired
  GeocodeRepository geocodeRepository;

  @Test
  void search_whenNoBreweries() {
    when(geocodeRepository.findAll()).thenReturn(Collections.emptyList());

    SearchResult result = searchService.findBestItinerary(ORIGIN);

    assertEquals(1, result.itineraryNodes().size());
    assertNull(result.itineraryNodes().getFirst().getBreweryId());
    assertTrue(Math.abs(result.totalKms().doubleValue()) < 0.001);
  }

  @Test
  void search_whenNoBreweriesWithinRange() {
    Geocode outsideRange = createGeocode(1, "-10", "-10");
    when(geocodeRepository.findAll()).thenReturn(List.of(outsideRange));

    SearchResult result = searchService.findBestItinerary(ORIGIN);

    assertEquals(1, result.itineraryNodes().size());
    assertNull(result.itineraryNodes().getFirst().getBreweryId());
    assertTrue(Math.abs(result.totalKms().doubleValue()) < 0.001);
  }

  @Test
  void search_whenBreweriesWithinRange() {
    // Setting up test data that looks like:
    // geo1 --401km-- ORIGIN --400km-- geo2 --10km-- geo3 --10km-- geo4
    // Algorithm always gets the closest value from the origin, so expecting:
    // ORIGIN -> geo2 -> geo1 -> ORIGIN

    Geocode geo1 = createGeocode(1, "49.735", "4.55522");
    Geocode geo2 = createGeocode(2, "49.735", "15.70234");
    Geocode geo3 = createGeocode(3, "49.735", "15.84141");
    Geocode geo4 = createGeocode(4, "49.735", "15.98047");
    when(geocodeRepository.findAll()).thenReturn(List.of(geo1, geo2, geo3, geo4));

    SearchResult result = searchService.findBestItinerary(ORIGIN);

    assertEquals(4, result.itineraryNodes().size());
    assertNull(result.itineraryNodes().get(0).getBreweryId());
    assertEquals(2, result.itineraryNodes().get(1).getBreweryId());
    assertEquals(1, result.itineraryNodes().get(2).getBreweryId());
    assertNull(result.itineraryNodes().get(3).getBreweryId());
    // 400 + 801 + 401
    assertTrue(Math.abs(1602 - result.totalKms().doubleValue()) < 1);
  }

  private Geocode createGeocode(int id, String latitude, String longitude) {
    Brewery brewery = new Brewery();
    brewery.setId(id);
    brewery.setName("Brewery-" + id);

    Beer beer = new Beer();
    beer.setName("Beer-" + id);
    brewery.setBeers(Set.of(beer));

    Geocode geocode = new Geocode();
    geocode.setLatitude(new BigDecimal(latitude));
    geocode.setLongitude(new BigDecimal(longitude));
    geocode.setBrewery(brewery);

    return geocode;
  }
}
