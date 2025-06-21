package com.crod.beers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "brewery")
public class Brewery {

  @Id
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "address1")
  private String address1;

  @Column(name = "address2")
  private String address2;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "code")
  private String code;

  @Column(name = "country")
  private String country;

  @Column(name = "phone")
  private String phone;

  @Column(name = "website")
  private String website;

  @Column(name = "filepath")
  private String filepath;

  @Column(name = "descript", columnDefinition = "text")
  private String description;

  @Column(name = "add_user")
  private Integer addUser;

  @Column(name = "last_mod")
  private LocalDateTime lastMod;

  @OneToMany(mappedBy = "brewery")
  private Set<Beer> beers;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
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

  public Set<Beer> getBeers() {
    return beers;
  }

  public void setBeers(Set<Beer> beers) {
    this.beers = beers;
  }
}