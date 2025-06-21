package com.crod.beers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  @GetMapping(value = "/")
  @ResponseBody
  public String get() {
    return "hi";
  }
}
