package com.crod.beers.controller;

import static com.crod.beers.constants.Constants.DEFAULT_START_LAT;
import static com.crod.beers.constants.Constants.DEFAULT_START_LONG;

import com.crod.beers.constants.Constants.Views;
import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchResult;
import com.crod.beers.form.SearchForm;
import com.crod.beers.service.impl.BasicSearchService;
import com.crod.beers.service.impl.SearchByRelativeProximityService;
import jakarta.validation.Valid;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String get(Model model) {
    model.addAttribute("searchForm", createDefaultSearchForm());
    return Views.INDEX;
  }

  @PostMapping(value = "/searchBasic")
  public String searchBasic(@Valid SearchForm searchForm, BindingResult bindingResult,
      Model model) {

    if (!bindingResult.hasErrors()) {
      Instant startInstant = clock.instant();

      SearchResult searchResult = basicSearchService.findBestItinerary(
          new Coordinate(searchForm.getLatitude(), searchForm.getLongitude()));

      model.addAttribute("searchResult", searchResult);
      model.addAttribute("elapsedTimeMs",
          Duration.between(startInstant, clock.instant()).toMillis());
    }

    return Views.INDEX;
  }

  @PostMapping(value = "/searchByRP")
  public String searchByRelativeProximity(@Valid SearchForm searchForm, BindingResult bindingResult,
      Model model) {

    if (!bindingResult.hasErrors()) {
      Instant startInstant = clock.instant();

      SearchResult searchResult = searchByRelativeProximityService.findBestItinerary(
          new Coordinate(searchForm.getLatitude(), searchForm.getLongitude()));

      model.addAttribute("searchResult", searchResult);
      model.addAttribute("elapsedTimeMs",
          Duration.between(startInstant, clock.instant()).toMillis());
    }

    return Views.INDEX;
  }

  private SearchForm createDefaultSearchForm() {
    SearchForm searchForm = new SearchForm();
    searchForm.setLatitude(DEFAULT_START_LAT);
    searchForm.setLongitude(DEFAULT_START_LONG);
    return searchForm;
  }
}
