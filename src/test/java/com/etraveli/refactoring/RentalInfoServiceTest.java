package com.etraveli.refactoring;

import static org.assertj.core.api.Assertions.assertThat;

import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.MovieRental;
import com.etraveli.refactoring.service.RentalInfoService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RentalInfoServiceTest {
  @Mock protected RentalInfoService rentalInfoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGet() {
    String expected =
        "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";
    List<MovieRental> movieRentals =
        List.of(new MovieRental("F001", 3), new MovieRental("F002", 1));
    Customer customer = new Customer("C. U. Stomer", movieRentals);

    String result = new RentalInfoService().getStatementOfCustomer(customer);

    assertThat(result).isEqualTo(expected);
  }
}
