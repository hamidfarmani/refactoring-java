package com.etraveli.refactoring.model;

public class MovieRental {

  private String movieId;
  private int days;

  public MovieRental(String movieId, int days) {
    this.movieId = movieId;
    this.days = days;
  }

  public String getMovieId() {
    return movieId;
  }

  public int getDays() {
    return days;
  }
}
