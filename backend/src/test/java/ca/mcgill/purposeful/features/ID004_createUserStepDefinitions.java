package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Step definitions for the ID004_CreateUser.feature file
 *
 * @author Shidan Javaheri
 */
public class ID004_createUserStepDefinitions {

  @Autowired AppUserService appUserService;

  @Autowired AppUserRepository appUserRepository;

  // client that will send requests
  @Autowired private TestRestTemplate client;

  // fields for catching the response of the http request
  private ResponseEntity<?> response;

  @Given("the database contains the following regular user account:")
  public void theDatabaseContainsTheFollowingRegularUserAccount(DataTable dataTable) {

    // get access to the app users with the helper method
    ArrayList<AppUser> appUsers = CucumberUtil.unpackTableIntoUsers(dataTable);

    // create each app user
    for (AppUser appUser : appUsers) {
      appUserService.registerRegularUser(
          appUser.getEmail(), appUser.getPassword(), appUser.getFirstname(), appUser.getLastname());
    }
  }

  // it was critical to have a separate step definition for a successful account creation and an
  // erroneous one
  @When(
      "a new regular user account is created with first name {string}, last name {string}, email {string} and password {string}")
  public void aNewRegularUserAccountIsCreatedWithFirstNameLastNameEmailAndPassword(
      String firstname, String lastname, String email, String password) {

    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

    // make a post request to create the user and store the response
    this.response = client.postForEntity("/api/appuser/regular", appUserDto, AppUserDto.class);
  }

  @When(
      "a new regular user account is created erroneously with first name {string}, last name {string}, email {string} and password {string}")
  public void aNewRegularUserAccountIsCreatedErroneouslyWithFirstNameLastNameEmailAndPassword(
      String firstname, String lastname, String email, String password) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

    // make a post request to create the user
    this.response = client.postForEntity("/api/appuser/regular", appUserDto, String.class);
  }

  @Then(
      "a new regular user account exists in the database with first name {string}, last name {string}, email {string} and authorities {string}")
  public void aNewRegularUserAccountExistsInTheDatabaseWithFirstNameLastNameEmailAndAuthorities(
      String firstname, String lastname, String email, String authorities) {

    // assert the response was not null
    assertNotNull(response, "The response was null");

    // get the app user from the database
    AppUser retrievedUser = appUserRepository.findAppUserByEmail(email);

    // check that the app user has the correct attributes
    assertEquals(firstname, retrievedUser.getFirstname());
    assertEquals(lastname, retrievedUser.getLastname());
    assertEquals(email, retrievedUser.getEmail());

    // check that the app user has the correct authorities
    Set<Authority> setOfAuthorities = new HashSet<>();
    setOfAuthorities.add(Authority.valueOf(authorities));
    assertEquals(setOfAuthorities, retrievedUser.getAuthorities());
  }

  @Then("the number of regular user accounts in the database is {string}")
  public void theNumberOfRegularUserAccountsInTheDatabaseIs(String count) {
    // Assert that the number of user accounts in the database is equal to the count
    assertEquals(Integer.parseInt(count), appUserRepository.count());
  }

  @Then("the following error {string} shall be raised with http status {string}")
  public void theFollowingErrorShallBeRaised(String message, String status) {
    // assert that the response was not null
    assertNotNull(response, "The response was null");

    // assert that the error message is correct
    assertEquals(message, this.response.getBody());

    // assert that the http status is correct
    int statusNumber = Integer.parseInt(status);
    assertEquals(HttpStatus.valueOf(statusNumber), this.response.getStatusCode());
  }
}
