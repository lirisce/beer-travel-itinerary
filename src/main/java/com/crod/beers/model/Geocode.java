package com.crod.beers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "geocode")
public class Geocode {

  @Id
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "brewery_id", referencedColumnName = "id")
  private Brewery brewery;

  @Column(name = "latitude", precision = 17, scale = 15, nullable = false)
  private BigDecimal latitude;

  @Column(name = "longitude", precision = 18, scale = 15, nullable = false)
  private BigDecimal longitude;

  @Column(name = "accuracy")
  private String accuracy;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Brewery getBrewery() {
    return brewery;
  }

  public void setBrewery(Brewery brewery) {
    this.brewery = brewery;
  }

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

  public String getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(String accuracy) {
    this.accuracy = accuracy;
  }
}
