package com.crod.beers.configuration;

import com.crod.beers.repository.BreweryRepository;
import com.crod.beers.repository.GeocodeRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockJpaTestConfig {

  @Bean
  public GeocodeRepository geocodeRepository() {
    return Mockito.mock(GeocodeRepository.class);
  }

  @Bean
  public BreweryRepository breweryRepository() {
    return Mockito.mock(BreweryRepository.class);
  }
}
