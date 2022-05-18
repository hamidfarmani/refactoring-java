package com.etraveli.refactoring.model;

public class Movie {

  private String title;
  private String code;

  public Movie(String title, String code) {

    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public String getCode() {
    return code;
  }
}
