package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.controller.IdeaController;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dto.CollaborationResponseDTO;
import ca.mcgill.purposeful.service.IdeaService;
import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class ID025_respondToCollaborationRequests {

  @Autowired private TestRestTemplate client;

  @Autowired private IdeaController ideaController;

  @Autowired private DatabaseUtil databaseUtil;

  @Autowired private CucumberUtil cucumberUtil;

  @Autowired private DomainRepository domainRepository;

  @Autowired private TopicRepository topicRepository;

  @Autowired private TechnologyRepository technologyRepository;

  @Autowired private IdeaService ideaService;

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
  public void theDatabaseContainsTheFollowingCollaborationResponses(DataTable dataTable) {}

  @Given("I am logged in as the user with email {string} and password {string} \\(ID025)")
  public void iAmLoggedInAsTheUserWithEmailAndPassword(String arg0, String arg1) {}

  @When(
      "the user approves the collaboration request with id {string} using message {string} and additional contact {string}")
  public void theUserApprovesTheCollaborationRequestWithIdUsingMessageAndAdditionalContact(
      String arg0, String arg1, String arg2) {}

  @Then(
      "the collaboration request with id {string} has an associated collaboration response with status {string}, message {string} and additional contact {string}")
  public void
      theCollaborationRequestWithIdHasAnAssociatedCollaborationResponseWithStatusMessageAndAdditionalContact(
          String arg0, String arg1, String arg2, String arg3) {}

  @And("the number of collaboration responses in the database is {string}")
  public void theNumberOfCollaborationResponsesInTheDatabaseIs(String arg0) {}

  @When("the user declines the collaboration request with id {string} using message {string}")
  public void theUserDeclinesTheCollaborationRequestWithIdUsingMessage(String arg0, String arg1) {}

  @Then(
      "the collaboration request with id {string} has an associated collaboration response with status {string}, message {string} and no additional contact")
  public void
      theCollaborationRequestWithIdHasAnAssociatedCollaborationResponseWithStatusMessageAndNoAdditionalContact(
          String arg0, String arg1, String arg2) {}

  @When(
      "the user erroneously approves the collaboration request with id {string} and message {string} and additional contact {string}")
  public void theUserErroneouslyApprovesTheCollaborationRequestWithIdAndMessageAndAdditionalContact(
      String arg0, String arg1, String arg2) {}

  @When(
      "the user erroneously declines the collaboration request with id {string} and message {string}")
  public void theUserErroneouslyDeclinesTheCollaborationRequestWithIdAndMessage(
      String arg0, String arg1) {}
}
