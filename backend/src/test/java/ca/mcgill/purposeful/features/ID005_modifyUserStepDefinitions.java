package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.OwnerRepository;
import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Owner;
import ca.mcgill.purposeful.service.AppUserService;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step Definition for the ID005_modifyUser.feature file
 *
 * @author Enzo Benoit-Jeannin
 */
public class ID005_modifyUserStepDefinitions {

  @Autowired AppUserService appUserService;

  @Autowired AppUserRepository appUserRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired OwnerRepository ownerRepository;

  // Util class
  @Autowired private CucumberUtil cucumberUtil;

  // fields for catching the response of the http request
  private ResponseEntity<?> response;

  // client that will send requests
  @Autowired private TestRestTemplate client;

  // util class for clearing the database
  @Autowired private DatabaseUtil databaseUtil;

  private String jwtToken;

  @Given("The user is not logged in")
  public void iAmNotLoggedIn() {
    // save the jwt token
    this.jwtToken = null;
  }

  @Given("the database contains the following accounts:")
  public void theDatabaseContainsTheFollowingAccounts(DataTable dataTable) {
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
      } else if (appUser.getAuthorities().contains(Authority.User)) {
        appUserService.registerRegularUser(
            appUser.getEmail(),
            appUser.getPassword(),
            appUser.getFirstname(),
            appUser.getLastname());
      }
    }
  }

  @Given("that the user is logged as user with email {string} and password {string}")
  public void iAmLoggedInAsUserWithEmailAndPassword(String email, String password) {
    // Login as the owner
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);

    // check that the login was successful
    assertEquals(200, this.response.getStatusCode().value());

    // save the jwt token
    this.jwtToken = this.response.getBody().toString();
  }

  @Given("that the user is logged as owner with email {string} and password {string}")
  public void iAmLoggedInAsOwnerWithEmailAndPassword(String email, String password) {
    // Login as the owner
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);

    // check that the login was successful
    assertEquals(200, this.response.getStatusCode().value());

    // save the jwt token
    this.jwtToken = this.response.getBody().toString();
  }

  @Given("that the user is logged as moderator with email {string} and password {string}")
  public void iAmLoggedInAsTheModeratorWithEmailAndPassword(String email, String password) {
    // Login as the owner
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);

    // check that the login was successful
    assertEquals(200, this.response.getStatusCode().value());

    // save the jwt token
    this.jwtToken = this.response.getBody().toString();
  }

  @When(
      "the user requests to modify the account with email {string} with {string} as the new lastname and {string} as the new first name")
  public void userAccountIsUpdatedWithNewLastNameFirstName(
      String email, String new_lastname, String new_firstname) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, "", new_firstname, new_lastname);

    // make a post request to modify the user and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange("/api/appuser/regular", HttpMethod.PUT, requestEntity, AppUserDto.class);
  }

  @When("the user requests to modify the account with email {string} with {string} as the password")
  public void userAccountIsUpdatedWithNewPassword(String email, String new_password) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, new_password, "", "");

    // make a post request to modify the user's password and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange(
            "/api/appuser/regular/password", HttpMethod.PUT, requestEntity, AppUserDto.class);
  }

  @When(
      "the user erroneously requests to modify the account with email {string} with {string} as the new lastname and {string} as the new first name")
  public void userAccountIsErroneouslyUpdatedWithNewLastNameFirstName(
      String email, String new_lastname, String new_firstname) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, "", new_firstname, new_lastname);

    // make a post request to modify the user and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange("/api/appuser/regular", HttpMethod.PUT, requestEntity, String.class);
  }

  @When(
      "the user erroneously request to modify the account with email {string} with new password {string}")
  public void userAccountIsErroneouslyUpdatedWithNewPassword(String email, String new_password) {
    // create a DTO to send the request to the service
    AppUserDto appUserDto = new AppUserDto(email, new_password, "", "");

    // make a post request to modify the user and store the response
    HttpEntity<AppUserDto> requestEntity =
        new HttpEntity<>(appUserDto, cucumberUtil.bearerAuthHeader(this.jwtToken));
    this.response =
        client.exchange(
            "/api/appuser/regular/password", HttpMethod.PUT, requestEntity, String.class);
  }

  @Then("account with email {string} have {string} as lastname and {string} as firstname")
  public void accountWithEmailHaveAsLastnameFirstname(
      String email, String lastname, String firstname) {
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
  }

  @Then("account with email {string} have {string} as password")
  public void accountWithEmailHaveAsPassword(String email, String password) {
    // assert the response was not null
    assertNotNull(this.response, "The response was null");

    // check that the request was successful
    assertEquals(200, this.response.getStatusCode().value());

    // get the app user from the database
    AppUser retrievedUser = appUserRepository.findAppUserByEmail(email);

    // check that the app user has the correct attributes
    assertTrue(passwordEncoder.matches(password, retrievedUser.getPassword()));
    assertEquals(email, retrievedUser.getEmail());
  }

  @Then("the number of user accounts in the database shall be {string}")
  public void theNumberOfUsersInTheDatabaseIs(String count) {
    // Assert that the number of user accounts in the database is equal to the count
    assertEquals(Integer.parseInt(count), appUserRepository.count());
  }

  @Then(
      "the user should be denied permission to the requested resource with an HTTP status code of {string}")
  public void permissionDeniedWithHTTPStatusCoseOf(String status) {
    // Assert that the response was not null
    assertNotNull(this.response, "The response was null");

    // Assert that the response status is equal to the status
    assertEquals(Integer.parseInt(status), this.response.getStatusCode().value());
  }

  @Then("this error {string} shall be raised with http status code {string}")
  public void thisErrorShallBeRaised(String message, String status) {
    // assert that the response was not null
    assertNotNull(this.response, "The response was null");

    // assert that the error message is correct
    assertEquals(message + " ", this.response.getBody());

    // assert that the http status is correct
    int statusNumber = Integer.parseInt(status);
    assertEquals(HttpStatus.valueOf(statusNumber), this.response.getStatusCode());
  }
}
