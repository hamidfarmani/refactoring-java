package com.etraveli.refactoring;

import com.etraveli.refactoring.model.Customer;
import com.etraveli.refactoring.model.MovieRental;
import com.etraveli.refactoring.service.RentalInfoService;
import java.util.List;

public class RentalApplication {

  public static void main(String[] args) {
    String expected =
        "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

    String result =
        new RentalInfoService()
            .getStatementOfCustomer(
                new Customer(
                    "C. U. Stomer",
                    List.of(new MovieRental("F001", 3), new MovieRental("F002", 1))));

    if (!result.equals(expected)) {
      throw new AssertionError(String.format("Expected: \n%s\n\nGot: \n%s", expected, result));
    }

    System.out.println("Success");
  }
}
