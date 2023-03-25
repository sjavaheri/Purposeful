package ca.mcgill.purposeful.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.CollaborationRequestDTO;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.util.CucumberUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.security.crypto.password.PasswordEncoder;

public class ID022_collaborationRequestWithMessage {

  @Autowired private TestRestTemplate client;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired AppUserRepository appUserRepository;

  @Autowired ReactionRepository reactionRepository;

  @Autowired RegularUserRepository regularUserRepository;

  @Autowired IdeaRepository ideaRepository;

  @Autowired CollaborationRequestRepository collaborationRequestRepository;

  // token to store once user is logged in
  private String jwtToken;

  private CollaborationRequestDTO returnedCollaborationRequest;
  private String returnedErrorMessage;
  private Map<String, String> idMap = new HashMap<String, String>();

  @And("the database contains the following RegularUser accounts \\(ID022):")
  public void theDatabaseContainsTheFollowingRegularUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domains \\(ID022):")
  public void theDatabaseContainsTheFollowingDomains(DataTable dataTable) {
    cucumberUtil.createAndSaveDomainsFromTable(dataTable, idMap);
  }

  @And("the database contains the following topics \\(ID022):")
  public void theDatabaseContainsTheFollowingTopics(DataTable dataTable) {
    cucumberUtil.createAndSaveTopicsFromTable(dataTable, idMap);
  }

  @And("the database contains the following techs \\(ID022):")
  public void theDatabaseContainsTheFollowingTechs(DataTable dataTable) {
    cucumberUtil.createAndSaveTechsFromTable(dataTable, idMap);
  }

  @And("the database contains the following urls \\(ID022):")
  public void theDatabaseContainsTheFollowingUrls(DataTable dataTable) {
    cucumberUtil.createAndSaveURLsFromTable(dataTable, idMap);
  }

  @And("the database contains the following ideas \\(ID022):")
  public void theDatabaseContainsTheFollowingIdeas(DataTable dataTable) {
    cucumberUtil.createAndSaveIdeasFromTable(dataTable, idMap);
  }

  @And("the database contains the following collaboration requests \\(ID022):")
  public void theDatabaseContainsTheFollowingCollaborationRequests(DataTable dataTable) {
    cucumberUtil.createAndSaveCollaborationRequestsFromTable(dataTable, idMap);
  }

  @When(
      "I successfully request to send a collaboration request to the creator of the idea with id {string} with message {string} and additionalContact {string}")
  public void
      iRequestToSendACollaborationRequestToTheCreatorOfTheIdeaWithIdWithMessageAndAdditionalContact(
          String ideaId, String message, String additionalContact) {

    // Create the request body
    CollaborationRequestDTO collaborationRequestDTO = new CollaborationRequestDTO();
    collaborationRequestDTO.setMessage(message);
    collaborationRequestDTO.setAdditionalContact(additionalContact);
    collaborationRequestDTO.setIdea(new IdeaDTO(ideaRepository.findIdeaById(idMap.get(ideaId))));

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(collaborationRequestDTO, header);

    // Send the request
    var response =
        client.exchange(
            "/api/collaborationRequest", HttpMethod.POST, requestEntity, ArrayList.class);

    // Extract returned DTO
    ObjectMapper mapper = new ObjectMapper();
    this.returnedCollaborationRequest =
        mapper.convertValue(response.getBody(), new TypeReference<CollaborationRequestDTO>() {});
  }

  @Then("the number of collaboration requests in the database should be {string}")
  public void theNumberOfCollaborationRequestsInTheDatabaseShouldBe(String number) {
    assert (collaborationRequestRepository.count() == Integer.parseInt(number));
  }

  @And("the following error message shall be returned {string}")
  public void theFollowingErrorMessageShallBeReturned(String errorMessage) {
    assertEquals(errorMessage, returnedErrorMessage);
  }

  @Given("I am logged in as the user with email {string} and password {string} \\(ID022)")
  public void iAmLoggedInAsTheUserWithEmailAndPasswordID(String email, String password) {
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

  @And(
      "the collaboration request with idea_id {string} and user_id {string} shall exist in the system")
  public void theFollowingCollaborationRequestWithIdea_idAndUser_idShallExistInTheSystem(
      String ideaId, String userId) {
    List<CollaborationRequest> list =
        collaborationRequestRepository.findCollaborationRequestsByRequesterAndIdea(
            regularUserRepository.findRegularUserById(idMap.get(userId)),
            ideaRepository.findIdeaById(idMap.get(ideaId)));
    assert (list.size() == 1);
  }

  @When(
      "I unsuccessfully request to send a collaboration request to the creator of the idea with id {string} with message {string} and additionalContact {string}")
  public void
      iUnsuccessfullyRequestToSendACollaborationRequestToTheCreatorOfTheIdeaWithIdWithMessageAndAdditionalContact(
          String ideaId, String message, String additionalContact) {
    // Create the request body
    CollaborationRequestDTO collaborationRequestDTO = new CollaborationRequestDTO();
    collaborationRequestDTO.setMessage(message);
    collaborationRequestDTO.setAdditionalContact(additionalContact);
    collaborationRequestDTO.setIdea(new IdeaDTO(ideaRepository.findIdeaById(idMap.get(ideaId))));

    // Create the request header
    HttpHeaders header = cucumberUtil.bearerAuthHeader(jwtToken);
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccessControlAllowOrigin("*");

    // Create the request entity
    HttpEntity<?> requestEntity = new HttpEntity<>(collaborationRequestDTO, header);

    // Send the request
    var response =
        client.exchange("/api/collaborationRequest", HttpMethod.POST, requestEntity, String.class);

    // Extract returned DTO
    ObjectMapper mapper = new ObjectMapper();
    this.returnedErrorMessage =
        mapper.convertValue(response.getBody(), new TypeReference<String>() {});
  }
}
