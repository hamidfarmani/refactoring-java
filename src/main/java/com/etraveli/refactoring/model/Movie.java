package com.etraveli.refactoring.model;

import com.etraveli.refactoring.utils.enums.MovieCode;

public class Movie {

  private String title;
  private MovieCode code;

  public Movie(String title, MovieCode code) {

    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public MovieCode getCode() {
    return code;
  }
}
