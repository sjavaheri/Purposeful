package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.controller.LoginController;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Step definitions for the ID003_Login.feature file
 *
 * @author Sasha
 */
public class ID003_loginStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired LoginController loginController;

  @Autowired private CucumberUtil cucumberUtil;

  // token to store once user is logged in
  private String jwtToken;
  // field for catching the response of the http request
  private ResponseEntity<?> response;

  @Given("the database contains the following appUser accounts:")
  public void theDatabaseContainsTheFollowingAppUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveAppUsersFromTable(dataTable, null);
  }

  @When("I request to login with the email {string} and password {string}")
  public void iRequestToLoginWithTheEmailAndPassword(String email, String password) {
    // Add the Basic Auth header to the request
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);
  }

  @Then("I should receive a valid JWT token and a HTTP status code {string}")
  public void iShouldReceiveAValidJWTTokenAndAHTTPStatusCode(String status) {
    // Check the status code
    int statusNumber = Integer.parseInt(status);
    assertEquals(HttpStatus.valueOf(statusNumber), response.getStatusCode());
    // Check the JWT token
    jwtToken = response.getBody().toString();
    assertNotNull(jwtToken);
  }

  @When("I request to login erroneously with the email {string} and password {string}")
  public void iRequestToLoginErroneouslyWithTheEmailAndPassword(String email, String password) {
    // Add the Basic Auth header to the request
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
    this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);
  }

  @Then("I should receive a HTTP status code {string}")
  public void iShouldReceiveAHTTPStatusCode(String status) {
    // Check the status code
    int statusNumber = Integer.parseInt(status);
    assertEquals(HttpStatus.valueOf(statusNumber), response.getStatusCode());
  }
}
