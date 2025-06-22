package com.crod.beers.controller;

import com.crod.beers.constants.Constants.Views;
import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchResult;
import com.crod.beers.service.impl.BasicSearchService;
import com.crod.beers.service.impl.SearchByRelativeProximityService;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {

  private final SearchByRelativeProximityService searchByRelativeProximityService;
  private final BasicSearchService basicSearchService;
  private final Clock clock;

  @Autowired
  public HomepageController(SearchByRelativeProximityService searchByRelativeProximityService,
      BasicSearchService basicSearchService, Clock clock) {
    this.searchByRelativeProximityService = searchByRelativeProximityService;
    this.basicSearchService = basicSearchService;
    this.clock = clock;
  }

  @GetMapping
  public String get() {
    return Views.INDEX;
  }

  @PostMapping(value = "/searchBasic")
  public String basicSearch(@RequestParam(name = "lat") String latitude,
      @RequestParam(name = "long") String longitude,
      Model model) {
    Instant startInstant = clock.instant();
    SearchResult searchResult = basicSearchService.findBestItinerary(
        new Coordinate(new BigDecimal(latitude), new BigDecimal(longitude)));

    model.addAttribute("searchResult", searchResult);
    model.addAttribute("elapsedTimeMs", Duration.between(startInstant, clock.instant()).toMillis());
    model.addAttribute("latValue", latitude);
    model.addAttribute("longValue", longitude);

    return Views.INDEX;
  }

  @PostMapping(value = "/searchByRP")
  public String searchByRelativeProximity(@RequestParam(name = "lat") String latitude,
      @RequestParam(name = "long") String longitude,
      Model model) {
    Instant startInstant = clock.instant();
    SearchResult searchResult = searchByRelativeProximityService.findBestItinerary(
        new Coordinate(new BigDecimal(latitude), new BigDecimal(longitude)));

    model.addAttribute("searchResult", searchResult);
    model.addAttribute("elapsedTimeMs", Duration.between(startInstant, clock.instant()).toMillis());
    model.addAttribute("latValue", latitude);
    model.addAttribute("longValue", longitude);

    return Views.INDEX;
  }
}
