package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.dto.CollaborationResponseDTO;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Status;
import ca.mcgill.purposeful.util.CucumberUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ID025_respondToCollaborationRequests {

  @Autowired private TestRestTemplate client;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired private CollaborationRequestRepository collaborationRequestRepository;

  @Autowired private CollaborationResponseRepository collaborationResponseRepository;

  // token to store once user is logged in
  private String jwtToken;

  // Fields for catching the response of the http request
  private CollaborationResponseDTO returnedCollaborationResponse;
  private ResponseEntity<String> erroneousResponse;

  // Declare a map to store the ids of the users created in the database
  private Map<String, String> idMap;

  @Given("the id map is initialized \\(ID025)")
  public void theIdMapIsInitialized() {
    idMap = new HashMap<>();
  }

  @And("the database contains the following RegularUser accounts \\(ID025):")
  public void theDatabaseContainsTheFollowingRegularUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domains \\(ID025):")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following topics \\(ID025):")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following techs \\(ID025):")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following urls \\(ID025):")
  public void theDatabaseContainsTheFollowingUrls(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @And("the database contains the following ideas \\(ID025):")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
  }

  @And("the database contains the following collaboration requests \\(ID025):")
  public void theDatabaseContainsTheFollowingCollaborationRequests(DataTable dataTable) {
    cucumberUtil.createAndSaveCollaborationRequestsFromTable(dataTable, idMap);
  }

  @And("the database contains the following collaboration responses \\(ID025):")
  public void theDatabaseContainsTheFollowingCollaborationResponses(DataTable dataTable) {
    cucumberUtil.createAndSaveCollaborationResponsesFromTable(dataTable, idMap);
  }

  @Given("I am logged in as the user with email {string} and password {string} \\(ID025)")
  public void iAmLoggedInAsTheUserWithEmailAndPassword(String email, String password) {
    HttpEntity<String> requestEntity =
        new HttpEntity<>(cucumberUtil.basicAuthHeader(email, password));

    // We don't save this response in the field because we don't need it later
    // In this case we are testing whether the browse ideas response is correct so
    // we only
    // need the token
    ResponseEntity<?> response =
        client.exchange("/api/login", HttpMethod.POST, requestEntity, String.class);
    Assertions.assertEquals(
        HttpStatus.OK, response.getStatusCode()); // Making sure the login was successful
    jwtToken = response.getBody().toString(); // Extract the token for future requests
    assertNotNull(jwtToken); // Ensure the token is not null
  }

  @When(
      "the user approves the collaboration request with id {string} using message {string} and additional contact {string}")
  public void theUserApprovesTheCollaborationRequestWithIdUsingMessageAndAdditionalContact(
      String requestId, String message, String additionalContact) {

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(header);

    // Create the path variable
    String url =
        "/api/collaborationResponse/approve/"
            + idMap.get(requestId)
            + "/"
            + message
            + "/"
            + additionalContact;

    // Save the response in the current class (This is the response we are trying to test here)
    var response =
        client.exchange(url, HttpMethod.POST, requestEntity, CollaborationResponseDTO.class);

    // Extract returned collaboration response
    ObjectMapper mapper = new ObjectMapper();
    returnedCollaborationResponse =
        mapper.convertValue(response.getBody(), new TypeReference<CollaborationResponseDTO>() {});
  }

  @Then(
      "the collaboration request with id {string} has an associated collaboration response with status {string}, message {string} and additional contact {string}")
  public void
      theCollaborationRequestWithIdHasAnAssociatedCollaborationResponseWithStatusMessageAndAdditionalContact(
          String requestId, String status, String message, String additionalContact) {

    // Retrieve the request
    CollaborationRequest collaborationRequest =
        collaborationRequestRepository.findCollaborationRequestById(idMap.get(requestId));

    // Ensure it has a response
    CollaborationResponse collaborationResponse = collaborationRequest.getCollaborationResponse();
    assertNotNull(collaborationResponse);

    // Ensure the response has the correct status
    Status expectedStatus = null;
    if (status.equals("Approved")) {
      expectedStatus = Status.Approved;
    } else if (status.equals("Declined")) {
      expectedStatus = Status.Declined;
    } else {
      fail(); // Testing mistake again if get here
    }
    assertEquals(expectedStatus, collaborationResponse.getStatus());

    // Ensure the response has the correct message
    assertEquals(message, collaborationResponse.getMessage());

    // Ensure the response has the correct additional contact
    assertEquals(additionalContact, collaborationResponse.getAdditionalContact());
  }

  @And("the number of collaboration responses in the database is {string}")
  public void theNumberOfCollaborationResponsesInTheDatabaseIs(String number) {
    assertEquals(Integer.parseInt(number), collaborationResponseRepository.count());
  }

  @When("the user declines the collaboration request with id {string} using message {string}")
  public void theUserDeclinesTheCollaborationRequestWithIdUsingMessage(
      String requestId, String message) {

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(header);

    // Create the path variable
    String url = "/api/collaborationResponse/decline/" + idMap.get(requestId) + "/" + message + "/";

    // Save the response in the current class (This is the response we are trying to test here)
    var response =
        client.exchange(url, HttpMethod.POST, requestEntity, CollaborationResponseDTO.class);

    // Extract returned collaboration response
    ObjectMapper mapper = new ObjectMapper();
    returnedCollaborationResponse =
        mapper.convertValue(response.getBody(), new TypeReference<CollaborationResponseDTO>() {});
  }

  @Then(
      "the collaboration request with id {string} has an associated collaboration response with status {string}, message {string} and no additional contact")
  public void
      theCollaborationRequestWithIdHasAnAssociatedCollaborationResponseWithStatusMessageAndNoAdditionalContact(
          String requestId, String status, String message) {
    // Retrieve the request
    CollaborationRequest collaborationRequest =
        collaborationRequestRepository.findCollaborationRequestById(idMap.get(requestId));

    // Ensure it has a response
    CollaborationResponse collaborationResponse = collaborationRequest.getCollaborationResponse();
    assertNotNull(collaborationResponse);

    // Ensure the response has the correct status
    Status expectedStatus = null;
    if (status.equals("Approved")) {
      expectedStatus = Status.Approved;
    } else if (status.equals("Declined")) {
      expectedStatus = Status.Declined;
    } else {
      fail(); // Testing mistake again if get here
    }
    assertEquals(expectedStatus, collaborationResponse.getStatus());

    // Ensure the response has the correct message
    assertEquals(message, collaborationResponse.getMessage());

    // Ensure the response has the correct additional contact
    assertNull(collaborationResponse.getAdditionalContact());
  }

  @When(
      "the user erroneously approves the collaboration request with id {string} and message {string} and additional contact {string}")
  public void theUserErroneouslyApprovesTheCollaborationRequestWithIdAndMessageAndAdditionalContact(
      String requestId, String message, String additionalContact) {

    // Convert the null values to empty strings
    if (message == null) {
      message = "";
    }
    if (additionalContact == null) {
      additionalContact = "";
    }

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(header);

    // Create the path variable
    String url =
        "/api/collaborationResponse/approve/"
            + idMap.get(requestId)
            + "/"
            + message
            + "/"
            + additionalContact;

    // Save the response in the current class (This is the response we are trying to test here)
    erroneousResponse = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
  }

  @When(
      "the user erroneously declines the collaboration request with id {string} and message {string}")
  public void theUserErroneouslyDeclinesTheCollaborationRequestWithIdAndMessage(
      String requestId, String message) {

    // Convert the null values to empty strings
    if (message == null) {
      message = "";
    }

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(header);

    // Create the path variable
    String url = "/api/collaborationResponse/decline/" + idMap.get(requestId) + "/" + message;

    // Save the response in the current class (This is the response we are trying to test here)
    erroneousResponse = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
  }

  @Then("the user shall receive the error message {string} with status {string} \\(ID025)")
  public void theUserShallReceiveTheErrorMessageWithStatus(
      String expectedMessage, String expectedStatus) {
    assertEquals(expectedMessage, erroneousResponse.getBody());
    assertEquals(Integer.parseInt(expectedStatus), erroneousResponse.getStatusCode().value());
  }
}
