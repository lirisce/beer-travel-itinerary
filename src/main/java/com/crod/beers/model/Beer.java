package com.crod.beers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "beer")
public class Beer {

  @Id
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "brewery_id", referencedColumnName = "id")
  private Brewery brewery;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "style_id", referencedColumnName = "id")
  private Style style;

  @ManyToOne
  @JoinColumn(name = "cat_id", referencedColumnName = "id")
  private Category category;

  @Column(name = "abv")
  private String abv;

  @Column(name = "ibu")
  private String ibu;

  @Column(name = "srm")
  private String srm;

  @Column(name = "upc")
  private String upc;

  @Column(name = "filepath")
  private String filepath;

  @Column(name = "descript", columnDefinition = "text")
  private String description;

  @Column(name = "add_user")
  private Integer addUser;

  @Column(name = "last_mod")
  private LocalDateTime lastMod;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Style getStyle() {
    return style;
  }

  public void setStyle(Style style) {
    this.style = style;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getAbv() {
    return abv;
  }

  public void setAbv(String abv) {
    this.abv = abv;
  }

  public String getIbu() {
    return ibu;
  }

  public void setIbu(String ibu) {
    this.ibu = ibu;
  }

  public String getSrm() {
    return srm;
  }

  public void setSrm(String srm) {
    this.srm = srm;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getAddUser() {
    return addUser;
  }

  public void setAddUser(Integer addUser) {
    this.addUser = addUser;
  }

  public LocalDateTime getLastMod() {
    return lastMod;
  }

  public void setLastMod(LocalDateTime lastMod) {
    this.lastMod = lastMod;
  }
}
