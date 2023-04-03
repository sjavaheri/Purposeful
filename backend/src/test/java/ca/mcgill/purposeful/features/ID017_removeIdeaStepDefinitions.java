package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Step definitions for the ID017_removeIdea.feature file
 *
 * @author Athmane Benarous
 */
public class ID017_removeIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired AppUserRepository appUserRepository;

  private HttpHeaders authHeader;
  private ResponseEntity<?> response;
  private Map<String, String> idMap = new HashMap<String, String>();

  @Given("the database contains the following user accounts before removing an idea:")
  public void theDatabaseContainsTheFollowingUserAccountsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domains before removing an idea:")
  public void theDatabaseContainsTheFollowingDomainsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following topics before removing an idea:")
  public void theDatabaseContainsTheFollowingTopicsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following techs before removing an idea:")
  public void theDatabaseContainsTheFollowingTechsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following URLs before removing an idea:")
  public void theDatabaseContainsTheFollowingURLsBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @And("the database contains the following ideas before removing an idea:")
  public void theDatabaseContainsTheFollowingIdeasBeforeRemovingAnIdea(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
  }

  @And(
      "the user is logged in with the email {string} and the password {string} before removing an idea")
  public void theUserIsLoggedInWithTheEmailAndThePasswordBeforeRemovingAnIdea(
      String email, String password) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      // Login as the user
      HttpEntity<String> requestEntity =
          new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));
      this.response = client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);

      // check that the login was successful
      assertEquals(200, this.response.getStatusCode().value());
      authHeader = cucumberUtil.bearerAuthHeader(response.getBody().toString());
    }
  }

  @When("the user requests to remove the idea with id {int}")
  public void theUserRequestsToRemoveTheIdeaWithId(Integer id) {
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response =
        client.exchange("/api/idea/" + correctedId, HttpMethod.DELETE, request, String.class);
  }

  @Then("the idea entry with id {int} will no longer exist in the idea database")
  public void theIdeaEntryWithIdWillNoLongerExistInTheIdeaDatabase(Integer id) {
    assertEquals(HttpStatus.OK, response.getStatusCode());
    String correctedId = idMap.get(id.toString());
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange("/api/idea/" + correctedId, HttpMethod.GET, request, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @When("I request to remove the idea with the invalid UUID {string}")
  public void iRequestToRemoveTheIdeaWithTheInvalidUUID(String uuid) {
    HttpEntity<String> request = new HttpEntity<>(authHeader);
    response = client.exchange("/api/idea/" + uuid, HttpMethod.DELETE, request, String.class);
  }

  @Then("the error message {string} will be thrown with status code {int}")
  public void theErrorMessageWillBeThrownWithStatusCode(String msg, Integer code) {
    assertEquals(HttpStatus.valueOf(code), response.getStatusCode());
  }
}
