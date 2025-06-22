package com.crod.beers.service;

import com.crod.beers.data.Coordinate;
import com.crod.beers.data.SearchResult;

public interface SearchService {

  /**
   * Finds an itinerary that maximises the number of breweries visited, starting and ending at a
   * given coordinate. The search algorithm used depends on the implementation.
   *
   * @param origin a coordinate representing the start/end point
   * @return a search result
   */
  SearchResult findBestItinerary(Coordinate origin);
}
