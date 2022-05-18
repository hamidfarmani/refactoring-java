package com.etraveli.refactoring.service;

import static com.etraveli.refactoring.utils.enums.MovieCode.REGULAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.etraveli.refactoring.exception.MovieNotFoundException;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.utils.enums.MovieCode;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class MovieServiceTest {
  protected MovieService underTest = new MovieService();

  @Test
  void saveMovieShouldCreateCorrectObject() {
    Movie expected = new Movie("Beautiful mind", REGULAR);
    Movie actual = underTest.saveMovie("Beautiful mind", REGULAR);

    assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
    assertThat(actual.getCode()).isEqualTo(expected.getCode());
  }

  @Test
  void moviesListShouldBeInitialized() {
    underTest.setMovies();
    HashMap<String, Movie> actual = underTest.getMovies();
    Integer expected = 4;

    assertThat(actual.size()).isEqualTo(expected);
  }

  @Test
  void getMovieShouldReturnCorrectMovie() {
    underTest.setMovies();
    Movie actual = underTest.getMovie("F001");
    String expectedTitle = "You've Got Mail";
    MovieCode expectedCode = REGULAR;

    assertThat(actual.getTitle()).isEqualTo(expectedTitle);
    assertThat(actual.getCode()).isEqualTo(expectedCode);
  }

  @Test
  void getMovieShouldThrowExceptionIfCouldNotFindID() {
    underTest.setMovies();
    MovieNotFoundException notFoundException =
        assertThrows(
            MovieNotFoundException.class,
            () -> {
              Movie actual = underTest.getMovie("INVALID ID");
            });

    String expectedMessage = "Could not find movie with rental id of INVALID ID";
    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
