package com.crod.beers.form;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SearchForm {

  @DecimalMin(value = "-90.0")
  @DecimalMax(value = "90.0")
  @NotNull
  private BigDecimal latitude;

  @DecimalMin(value = "-180.0")
  @DecimalMax(value = "180.0")
  @NotNull
  private BigDecimal longitude;

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }
}
