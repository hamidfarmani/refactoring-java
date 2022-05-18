package com.etraveli.refactoring.model;

import java.util.List;

public class Customer {

  private String name;
  private List<MovieRental> rentals;

  public Customer(String name, List<MovieRental> rentals) {
    this.name = name;
    this.rentals = rentals;
  }

  public String getName() {
    return name;
  }

  public List<MovieRental> getRentals() {
    return rentals;
  }
}
