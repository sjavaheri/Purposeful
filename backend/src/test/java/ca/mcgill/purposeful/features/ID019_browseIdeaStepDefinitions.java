package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.controller.IdeaController;
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

public class ID019_browseIdeaStepDefinitions {

  @Autowired private TestRestTemplate client;

  @Autowired private IdeaController ideaController;

  @Autowired private DatabaseUtil databaseUtil;

  @Autowired private CucumberUtil cucumberUtil;

  // token to store once user is logged in
  private String jwtToken;

  // field for catching the response of the http request
  private ResponseEntity<?> response;

  // declare a map to store the ids of the users created in the database
  private Map<String, String> idMap;

  @Given("the id map is initialized")
  public void theIdMapIsInitialized() {
    idMap = new HashMap<>();
  }

  @And("the database contains the following RegularUser accounts:")
  public void theDatabaseContainsTheFollowingRegularUserAccounts(DataTable dataTable) {
    cucumberUtil.createAndSaveRegularUsersFromTable(dataTable, idMap);
  }

  @And("the database contains the following domains:")
  public void theDatabaseContainsTheFollowingDomains() {

  }

  @And("the database contains the following topics:")
  public void theDatabaseContainsTheFollowingTopics() {}

  @And("the database contains the following techs:")
  public void theDatabaseContainsTheFollowingTechs() {}

  @And("the database contains the following ideas:")
  public void theDatabaseContainsTheFollowingIdeas() {}

  @And("I am logged in as the user with email {string} and password {string}")
  public void iAmLoggedInAsTheUserWithEmailAndPassword(String arg0, String arg1) {}

  @When(
      "the user requests to browse ideas by domains {string}, topics {string}, and techs {string}")
  public void theUserRequestsToBrowseIdeasByDomainsTopicsAndTechs(
      String arg0, String arg1, String arg2) {}

  @Then("the user shall have access to the ideas with ids {string}")
  public void theUserShallHaveAccessToTheIdeasWithIds(String arg0) {}

  @When(
      "the user erroneously requests to browse ideas by domains {string}, topics {string}, and techs {string}")
  public void theUserErroneouslyRequestsToBrowseIdeasByDomainsTopicsAndTechs(
      String arg0, String arg1, String arg2) {}

  @Then("the user shall receive the error message {string} with status {string}")
  public void theUserShallReceiveTheErrorMessageWithStatus(String arg0, String arg1) {}
}
