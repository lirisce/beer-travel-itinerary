package com.crod.beers.data;

public class SearchNode implements Cloneable {

  private Integer breweryId;
  private String breweryName;
  private Coordinate coordinate;
  private double distanceFromPreviousNode;
  private double distanceFromOrigin;

  public SearchNode(Integer breweryId, String breweryName, Coordinate coordinate,
      double distanceFromOrigin) {
    this.breweryId = breweryId;
    this.breweryName = breweryName;
    this.coordinate = coordinate;
    this.distanceFromOrigin = distanceFromOrigin;
  }

  public Integer getBreweryId() {
    return breweryId;
  }

  public void setBreweryId(Integer breweryId) {
    this.breweryId = breweryId;
  }

  public String getBreweryName() {
    return breweryName;
  }

  public void setBreweryName(String breweryName) {
    this.breweryName = breweryName;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public double getDistanceFromPreviousNode() {
    return distanceFromPreviousNode;
  }

  public void setDistanceFromPreviousNode(double distanceFromPreviousNode) {
    this.distanceFromPreviousNode = distanceFromPreviousNode;
  }

  public double getDistanceFromOrigin() {
    return distanceFromOrigin;
  }

  public void setDistanceFromOrigin(double distanceFromOrigin) {
    this.distanceFromOrigin = distanceFromOrigin;
  }

  @Override
  public String toString() {
    return (breweryId != null ? "[" + breweryId + "] " + breweryName : "HOME") + ": "
        + (coordinate != null ? "{" + coordinate.latitude() + "," + coordinate.longitude() + "}"
        : "")
        + ", distance: " + String.format("%.2f", distanceFromPreviousNode) + " km";
  }

  @Override
  public SearchNode clone() {
    try {
      return (SearchNode) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
