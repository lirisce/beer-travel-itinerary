package com.crod.beers.controller;

import com.crod.beers.repository.GeocodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  @Autowired
  GeocodeRepository geocodeRepository;

  @GetMapping(value = "/")
  @ResponseBody
  public String get() {
    return geocodeRepository.count() + " geocode records";
  }
}
