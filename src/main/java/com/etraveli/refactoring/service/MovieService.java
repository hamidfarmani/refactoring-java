package com.etraveli.refactoring.service;

import static com.etraveli.refactoring.utils.enums.MovieCode.*;

import com.etraveli.refactoring.exception.MovieNotFoundException;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.utils.enums.MovieCode;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovieService {
  private final Logger LOGGER = LogManager.getLogger(MovieService.class);
  private HashMap<String, Movie> movies = new HashMap();

  public MovieService() {
    setMovies();
  }

  public Movie saveMovie(String title, MovieCode code) {
    LOGGER.info("Saving movie '{}' with the code of '{}'", title, code);
    Movie movie = new Movie(title, code);
    return movie;
  }

  public void setMovies() {
    movies.put("F001", saveMovie("You've Got Mail", REGULAR));
    movies.put("F002", saveMovie("Matrix", REGULAR));
    movies.put("F003", saveMovie("Cars", CHILDREN));
    movies.put("F004", saveMovie("Fast & Furious X", NEW));
  }

  public HashMap<String, Movie> getMovies() {
    return movies;
  }

  public Movie getMovie(String movieRentalId) {
    Movie movie = movies.get(movieRentalId);
    if (movie == null) {
      throw new MovieNotFoundException(
          String.format("Could not find movie with rental id of %s", movieRentalId));
    }
    return movie;
  }
}
