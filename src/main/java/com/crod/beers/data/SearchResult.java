package com.crod.beers.data;

import java.math.BigDecimal;
import java.util.List;

public record SearchResult(List<SearchNode> itineraryNodes, BigDecimal totalKms,
                           List<String> beersCollected) {

}
