package ca.mcgill.purposeful.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class ID003_loginStepDefinitions {

  @Autowired
  private TestRestTemplate client;

  @Given("the database contains the following appUser accounts:")
  public void theDatabaseContainsTheFollowingAppUserAccounts() {
  }

  @When("I request to login with the email {string} and password {string}")
  public void iRequestToLoginWithTheEmailAndPassword(String arg0, String arg1) {
  }

  @Then("I should receive a valid JWT token and a HTTP status code {string}")
  public void iShouldReceiveAValidJWTTokenAndAHTTPStatusCode(String arg0) {
  }

  @When("I request to login erroneously with the email {string} and password {string}")
  public void iRequestToLoginErroneouslyWithTheEmailAndPassword(String arg0, String arg1) {
  }

  @Then("I should receive a HTTP status code {string}")
  public void iShouldReceiveAHTTPStatusCode(String arg0) {
  }
}
