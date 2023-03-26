package ca.mcgill.purposeful.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Step definitions for viewing a collaboration response.
 *
 * @author Thibaut Baguette
 */
public class ID026_viewCollaborationResponseStepDefinitions {
  @Autowired private TestRestTemplate client;

  @Autowired private CucumberUtil cucumberUtil;

  private ResponseEntity<?> response;
  private HttpHeaders authHeader;
  private String jwtToken;
  private Map<String, String> idMap = new HashMap<>();

  @Given("the database contains the following RegularUser accounts:")
  public void theDatabaseContainsTheFollowingRegularUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following Domains:")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following Topics:")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following Techs:")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following Urls:")
  public void theDatabaseContainsTheFollowingUrls(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @And("the database contains the following Ideas:")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
  }

  @And("the database contains the following CollaborationResponses:")
  public void theDatabaseContainsTheFollowingCollaborationResponses(DataTable dataTable) {
    cucumberUtil.createAndSaveCollaborationResponsesFromTable(dataTable, idMap);
  }

  @And("the database contains the following CollaborationRequests:")
  public void theDatabaseContainsTheFollowingCollaborationRequests(DataTable dataTable) {
    cucumberUtil.createAndSaveCollaborationRequestsFromTable(dataTable, idMap);
  }

  @And("I am logged in as the user with the email {string} and password {string}")
  public void iAmLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));

    ResponseEntity<String> response =
        client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    jwtToken = response.getBody().toString();
    assertNotNull(jwtToken);
  }

  @When("the user requests to access the collaboration response for the idea with id {string}")
  public void theUserRequestsToAccessTheCollaborationResponseForTheIdeaWithId(String ideaId) {
    authHeader = new HttpHeaders();
    authHeader.setBearerAuth(jwtToken);
    HttpEntity<String> requestEntity = new HttpEntity<>(authHeader);

    response =
        client.exchange(
            "/api/collaborationResponse/" + idMap.get(ideaId),
            HttpMethod.GET,
            requestEntity,
            // CollaborationResponseDTO.class);
            String.class);
  }

  @Then("the user shall have access to the collaboration response with id {string}")
  public void theUserShallHaveAccessToTheCollaborationResponseWithId(
      String collaborationResponseId) {
    assertEquals(HttpStatus.OK, response.getStatusCode());

    JsonObject json = Json.parse(response.getBody().toString()).asObject();
    String id = json.get("id").asString();

    assertEquals(idMap.get(collaborationResponseId), id);
  }

  @Then("the user shall receive the error message {string} with status code {int}")
  public void theUserShallReceiveTheErrorMessageWithStatus(String errorMessage, Integer status) {
    assertEquals(HttpStatus.valueOf(status), response.getStatusCode());
    assertEquals(errorMessage, response.getBody());
  }
}
