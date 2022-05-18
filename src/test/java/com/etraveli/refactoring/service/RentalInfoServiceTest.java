package com.etraveli.refactoring.service;

import static com.etraveli.refactoring.utils.enums.MovieCode.CHILDREN;
import static com.etraveli.refactoring.utils.enums.MovieCode.NEW;
import static com.etraveli.refactoring.utils.enums.MovieCode.REGULAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.model.MovieRental;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RentalInfoServiceTest {
  @Mock protected MovieService movieService;

  protected RentalInfoService underTest = new RentalInfoService();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getStatementOfCustomerForOneRental() {
    Movie movie = new Movie("You've Got Mail", REGULAR);
    List<MovieRental> movieRentals = List.of(new MovieRental("F001", 1));
    Customer customer = new Customer("C. U. Stomer", movieRentals);

    when(movieService.getMovie(any())).thenReturn(movie);

    String expected =
        String.format(
            "Rental Record for C. U. Stomer\n\t%s\t2.0\nAmount owed is 2.0\nYou earned 1 frequent points\n",
            movie.getTitle());

    String actual = underTest.getStatementOfCustomer(customer);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getStatementOfCustomerForMultipleRentals() {
    Movie movie1 = new Movie("You've Got Mail", REGULAR);
    Movie movie2 = new Movie("Cars", CHILDREN);
    List<MovieRental> movieRentals =
        List.of(new MovieRental("F001", 1), new MovieRental("F003", 10));
    Customer customer = new Customer("C. U. Stomer", movieRentals);

    when(movieService.getMovie("F001")).thenReturn(movie1);
    when(movieService.getMovie("F003")).thenReturn(movie2);

    String expected =
        String.format(
            "Rental Record for C. U. Stomer\n\t%s\t2.0\n\t%s\t12.0\nAmount owed is 14.0\nYou earned 2 frequent points\n",
            movie1.getTitle(), movie2.getTitle());

    String actual = underTest.getStatementOfCustomer(customer);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getStatementOfCustomerForZeroRental() {
    Movie movie = new Movie("You've Got Mail", REGULAR);
    List<MovieRental> movieRentals = new ArrayList<>();
    Customer customer = new Customer("C. U. Stomer", movieRentals);
    when(movieService.getMovie(any())).thenReturn(movie);
    String expected =
        String.format(
            "Rental Record for C. U. Stomer\nAmount owed is 0\nYou earned 0 frequent points\n");

    String actual = underTest.getStatementOfCustomer(customer);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getRentalAmountOfOneDayRegularShouldReturnTwo() {
    MovieRental movieRental = new MovieRental("F001", 1);
    Movie movie = new Movie("Regular Movie", REGULAR);
    BigDecimal expected = new BigDecimal(2).setScale(1);
    BigDecimal actual = underTest.getRentalAmountOf(movieRental, movie);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getRentalAmountOfTenDaysRegularShouldReturnFourteen() {
    MovieRental movieRental = new MovieRental("F001", 10);
    Movie movie = new Movie("Regular Movie", REGULAR);
    BigDecimal expected = new BigDecimal(14).setScale(1);
    BigDecimal actual = underTest.getRentalAmountOf(movieRental, movie);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getRentalAmountOfFiveDaysNewShouldReturnFifteen() {
    MovieRental movieRental = new MovieRental("F001", 5);
    Movie movie = new Movie("New Movie", NEW);
    BigDecimal expected = new BigDecimal(15).setScale(1);
    BigDecimal actual = underTest.getRentalAmountOf(movieRental, movie);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getRentalAmountOfThreeDaysChildrenShouldReturnOneHalf() {
    MovieRental movieRental = new MovieRental("F001", 3);
    Movie movie = new Movie("Children Movie", CHILDREN);
    BigDecimal expected = new BigDecimal(1.5).setScale(1);
    BigDecimal actual = underTest.getRentalAmountOf(movieRental, movie);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getRentalAmountOfFourDaysChildrenShouldReturnOneHalf() {
    MovieRental movieRental = new MovieRental("F001", 4);
    Movie movie = new Movie("Children Movie", CHILDREN);
    BigDecimal expected = new BigDecimal(3).setScale(1);
    BigDecimal actual = underTest.getRentalAmountOf(movieRental, movie);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void checkAndIncrementFrequentBonusShouldIncrementOnceForRegular() {
    MovieRental movieRental = new MovieRental("F001", 1);
    Movie movie = new Movie("Regular Movie", REGULAR);
    Integer frequentBonus = 0;
    Integer expected = 1;
    Integer actual = underTest.checkAndIncrementFrequentBonus(movieRental, movie, frequentBonus);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void checkAndIncrementFrequentBonusShouldIncrementOnceForChildren() {
    MovieRental movieRental = new MovieRental("F001", 10);
    Movie movie = new Movie("Children Movie", CHILDREN);
    Integer frequentBonus = 5;
    Integer expected = 6;
    Integer actual = underTest.checkAndIncrementFrequentBonus(movieRental, movie, frequentBonus);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void checkAndIncrementFrequentBonusShouldIncrementOnceForNewMovies() {
    MovieRental movieRental = new MovieRental("F001", 1);
    Movie movie = new Movie("New Movie", NEW);
    Integer frequentBonus = 0;
    Integer expected = 1;
    Integer actual = underTest.checkAndIncrementFrequentBonus(movieRental, movie, frequentBonus);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void checkAndIncrementFrequentBonusShouldIncrementTwice() {
    MovieRental movieRental = new MovieRental("F001", 3);
    Movie movie = new Movie("New Movie", NEW);
    Integer frequentBonus = 0;
    Integer expected = 2;
    Integer actual = underTest.checkAndIncrementFrequentBonus(movieRental, movie, frequentBonus);

    assertThat(actual).isEqualTo(expected);
  }
}
