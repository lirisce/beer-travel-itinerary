package com.crod.beers.controller;

import com.crod.beers.constants.Constants.Views;
import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchResult;
import com.crod.beers.service.SearchService;
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

  private final SearchService searchService;
  private final Clock clock;

  @Autowired
  public HomepageController(SearchService searchService, Clock clock) {
    this.searchService = searchService;
    this.clock = clock;
  }

  @GetMapping(value = "/")
  public String get() {
    return Views.INDEX;
  }

  @PostMapping(value = "/search")
  public String search(@RequestParam(name = "lat") String latitude,
      @RequestParam(name = "long") String longitude,
      Model model) {
    Instant startInstant = clock.instant();
    SearchResult searchResult = searchService.findBestItinerary(
        new Coordinate(new BigDecimal(latitude), new BigDecimal(longitude)));

    model.addAttribute("searchResult", searchResult);
    model.addAttribute("elapsedTimeMs", Duration.between(startInstant, clock.instant()).toMillis());
    model.addAttribute("latValue", latitude);
    model.addAttribute("longValue", longitude);

    return Views.INDEX;
  }
}
