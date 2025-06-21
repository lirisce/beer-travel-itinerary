package com.crod.beers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "style")
public class Style {

  @Id
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "cat_id", referencedColumnName = "id")
  private Category category;

  @Column(name = "style_name")
  private String name;

  @Column(name = "last_mod")
  private LocalDateTime lastMod;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getLastMod() {
    return lastMod;
  }

  public void setLastMod(LocalDateTime lastMod) {
    this.lastMod = lastMod;
  }
}
