package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.OwnerRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Owner;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Step definitions for the ID001_createModerator.feature file
 *
 * @author Siger Ma
 */
public class ID001_createModeratorStepDefinitions {

  @Autowired AppUserService appUserService;

  @Autowired AppUserRepository appUserRepository;

  @Autowired OwnerRepository ownerRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private TestRestTemplate client;

  @Autowired private CucumberUtil cucumberUtil;

  private String jwtToken;

  private ResponseEntity<?> response;

  @Given("the database contains the following moderator and owner accounts:")
  public void theDatabaseContainsTheFollowingModeratorAndOwnerAccounts(DataTable dataTable) {
    // get access to the app users with the helper method
    ArrayList<AppUser> appUsers = CucumberUtil.unpackTableIntoUsers(dataTable);

    // create each app user
    for (AppUser appUser : appUsers) {
      if (appUser.getAuthorities().contains(Authority.Moderator)) {
        appUserService.registerModerator(
            appUser.getEmail(),
            appUser.getPassword(),
            appUser.getFirstname(),
            appUser.getLastname());
      } else if (appUser.getAuthorities().contains(Authority.Owner)) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);

        Owner owner = new Owner();
        owner.setAppUser(appUser);
        ownerRepository.save(owner);
      }
    }
  }

  @Given("I am logged in as the owner with email {string} and password {string}")
  public void iAmLoggedInAsTheOwnerWithEmailAndPassword(String email, String password) {
    // Login as the owner
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/login", HttpMethod.POST, requestEntity, String.class);

    // check that the login was successful
    assertEquals(200, this.response.getStatusCode().value());

    // save the jwt token
    this.jwtToken = this.response.getBody().toString();
  }

  @Given("I am logged in as the moderator with email {string} and password {string}")
  public void iAmLoggedInAsTheModeratorWithEmailAndPassword(String email, String password) {
    // Login as the owner
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/login", HttpMethod.POST, requestEntity, String.class);

    // check that the login was successful
    assertEquals(200, this.response.getStatusCode().value());

    // save the jwt token
    this.jwtToken = this.response.getBody().toString();
  }

  @Given("I am not logged in")
  public void iAmNotLoggedIn() {
    // save the jwt token
    this.jwtToken = null;
  }

  @When(
      "a new moderator account is created with first name {string}, last name {string}, email {string} and password {string}")
  public void aNewModeratorAccountIsCreatedWithFirstNameLastNameEmailAndPassword(
      String firstname, String lastname, String email, String password) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

    // make a post request to create the user and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange("/api/appuser/moderator", HttpMethod.POST, requestEntity, AppUserDto.class);
  }

  @When(
      "a new moderator account is created erroneously with first name {string}, last name {string}, email {string} and password {string}")
  public void aNewModeratorAccountIsCreatedErroneouslyWithFirstNameLastNameEmailAndPassword(
      String firstname, String lastname, String email, String password) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, password, firstname, lastname);

    // make a post request to create the user and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange("/api/appuser/moderator", HttpMethod.POST, requestEntity, String.class);
  }

  @Then(
      "a new moderator account exists in the database with first name {string}, last name {string}, email {string}, password {string} and authorities {string}")
  public void
      aNewModeratorAccountExistsInTheDatabaseWithFirstNameLastNameEmailPasswordAndAuthorities(
          String firstname, String lastname, String email, String password, String authorities) {
    // assert the response was not null
    assertNotNull(this.response, "The response was null");

    // check that the request was successful
    assertEquals(200, this.response.getStatusCode().value());

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

  @Then("the number of moderator accounts in the database is {string}")
  public void theNumberOfModeratorAccountsInTheDatabaseIs(String count) {
    // Assert that the number of user accounts in the database is equal to the count
    assertEquals(Integer.parseInt(count), appUserRepository.count());
  }

  @Then("the following http status {string} shall be raised")
  public void theFollowingHttpStatusShallBeRaised(String status) {
    // Assert that the response was not null
    assertNotNull(this.response, "The response was null");

    // Assert that the response status is equal to the status
    assertEquals(Integer.parseInt(status), this.response.getStatusCode().value());
  }

  @Then("the following error {string} shall be raised with http status code {string}")
  public void theFollowingErrorShallBeRaised(String message, String status) {
    // assert that the response was not null
    assertNotNull(this.response, "The response was null");

    // assert that the error message is correct
    assertEquals(message, this.response.getBody());

    // assert that the http status is correct
    int statusNumber = Integer.parseInt(status);
    assertEquals(HttpStatus.valueOf(statusNumber), this.response.getStatusCode());
  }
}
