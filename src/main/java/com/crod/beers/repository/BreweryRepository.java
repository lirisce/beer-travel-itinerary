package com.crod.beers.repository;

import com.crod.beers.model.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreweryRepository extends JpaRepository<Brewery, Integer> {

}
