package com.etraveli.refactoring.service;

import static com.etraveli.refactoring.utils.ConstantValues.CHILDREN_MOVIES_INITIAL;
import static com.etraveli.refactoring.utils.ConstantValues.CHILDREN_MOVIES_MINIMUM_DAYS;
import static com.etraveli.refactoring.utils.ConstantValues.CHILDREN_MOVIES_MULTIPLIER;
import static com.etraveli.refactoring.utils.ConstantValues.NEW_MOVIES_MULTIPLIER;
import static com.etraveli.refactoring.utils.ConstantValues.NUMBER_OF_DAYS_FOR_NEW_MOVIES_BONUS;
import static com.etraveli.refactoring.utils.ConstantValues.REGULAR_MOVIES_INITIAL;
import static com.etraveli.refactoring.utils.ConstantValues.REGULAR_MOVIES_MINIMUM_DAYS;
import static com.etraveli.refactoring.utils.ConstantValues.REGULAR_MOVIES_MULTIPLIER;
import static com.etraveli.refactoring.utils.enums.MovieCode.NEW;

import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.model.MovieRental;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RentalInfoService {
  private final Logger LOGGER = LogManager.getLogger(RentalInfoService.class);

  private final MovieService movieService = new MovieService();

  /**
   * * This function will gather the statement of rental for the customer.
   *
   * @param customer
   * @return the statement for the customer
   */
  public String getStatementOfCustomer(Customer customer) {
    BigDecimal totalAmount = BigDecimal.ZERO;
    Integer frequentEnterPoints = 0;
    String statementResult = String.format("Rental Record for %s\n", customer.getName());

    for (MovieRental movieRental : customer.getRentals()) {
      Movie movie = movieService.getMovie(movieRental.getMovieId());
      BigDecimal rentalAmount = getRentalAmountOf(movieRental, movie);

      frequentEnterPoints = checkAndIncrementFrequentBonus(movieRental, movie, frequentEnterPoints);

      statementResult += String.format("\t%s\t%s\n", movie.getTitle(), rentalAmount);

      totalAmount = totalAmount.add(rentalAmount).setScale(1);
    }
    statementResult += String.format("Amount owed is %s\n", totalAmount);
    statementResult += String.format("You earned %s frequent points\n", frequentEnterPoints);

    LOGGER.info("Statement for customer {}: \n{}", customer.getName(), statementResult);
    return statementResult;
  }

  /**
   * This function will calculate the amount for each rental based on days and type of the rental.
   *
   * @param movieRental
   * @param movie
   * @return BigDecimal value of amount
   */
  public BigDecimal getRentalAmountOf(MovieRental movieRental, Movie movie) {
    BigDecimal rentalAmount = BigDecimal.ZERO;
    switch (movie.getCode()) {
      case REGULAR:
        rentalAmount = new BigDecimal(REGULAR_MOVIES_INITIAL);
        if (movieRental.getDays() > REGULAR_MOVIES_MINIMUM_DAYS) {
          BigDecimal discountedAmount =
              new BigDecimal(
                  (movieRental.getDays() - REGULAR_MOVIES_MINIMUM_DAYS)
                      * REGULAR_MOVIES_MULTIPLIER);
          rentalAmount = rentalAmount.add(discountedAmount);
        }
        break;

      case NEW:
        rentalAmount = new BigDecimal(movieRental.getDays() * NEW_MOVIES_MULTIPLIER);
        break;

      case CHILDREN:
        rentalAmount = new BigDecimal(CHILDREN_MOVIES_INITIAL);
        if (movieRental.getDays() > CHILDREN_MOVIES_MINIMUM_DAYS) {
          BigDecimal discountedAmount =
              new BigDecimal(
                  (movieRental.getDays() - CHILDREN_MOVIES_MINIMUM_DAYS)
                      * CHILDREN_MOVIES_MULTIPLIER);
          rentalAmount = rentalAmount.add(discountedAmount);
        }
        break;
    }
    LOGGER.info(
        "Rental amount for {} and movie {} was {}",
        movieRental.getMovieId(),
        movie.getTitle(),
        rentalAmount);
    return rentalAmount.setScale(1);
  }

  /**
   * * This function will check whether the movie type of rental is NEW or not. If it was, it will
   * add extra point, otherwise, it just adds one point.
   *
   * @param movieRental
   * @param movie
   * @param frequentEnterPoints initial points
   * @return
   */
  public Integer checkAndIncrementFrequentBonus(
      MovieRental movieRental, Movie movie, Integer frequentEnterPoints) {
    frequentEnterPoints++;
    LOGGER.info(
        "Bonus added for rental {} and movie {}", movieRental.getMovieId(), movie.getTitle());

    if (movie.getCode() == NEW && movieRental.getDays() > NUMBER_OF_DAYS_FOR_NEW_MOVIES_BONUS) {
      frequentEnterPoints++;
      LOGGER.info(
          "New-movie-bonus added for rental {} and movie {}",
          movieRental.getMovieId(),
          movie.getTitle());
    }

    return frequentEnterPoints;
  }
}
