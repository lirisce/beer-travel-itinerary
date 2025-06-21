package com.crod.beers.repository;

import com.crod.beers.model.Geocode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeocodeRepository extends JpaRepository<Geocode, Integer> {

}
